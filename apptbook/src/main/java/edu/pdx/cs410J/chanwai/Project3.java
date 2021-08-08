package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.Date;

/**
 * The main class for the CS410J appointment book Project
 */

public class Project3 {

    static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] owner description begin_date begin_time end_date end_time";
    static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
    static final String TOO_MANY_COMMAND_LINE_ARGUMENTS = "Too many command line arguments";
    static final String MISSING_DESCRIPTION = "Missing Description";
    static final String MISSING_BEGIN_DATE = "Missing Begin Date";
    static final String MISSING_BEGIN_TIME = "Missing Begin Time";
    static final String MISSING_END_DATE = "Missing End Date";
    static final String MISSING_END_TIME = "Missing End Time";
    static final String YEAR_OUT_OF_BOUNDS = "Year out of bounds";
    static final String MONTH_OUT_OF_BOUNDS = "Month out of bounds" ;
    static final String DAY_OUT_OF_BOUNDS = "Day out of bounds" ;
    static final String HOUR_OUT_OF_BOUNDS = "Hour out of bounds";
    static final String MINS_OUT_OF_BOUNDS = "Minutes out of bounds";
    static final String OWNER_NAME_NOT_EQUAL = "The owner's name on command line is different than the owner's name in the text file";
    static final String INVALID_DATE = "Invalid Date: ";
    static final String INVALID_TIME = "Invalid Time: ";
    static final String MISSING_AMPM = "Missing AM or PM";
    static final String BEGIN_DATE_AFTER_END_DATE = "Begin date occurs after End date";
    static final String PRETT_FILE_CANNOT_BE_THE_SAME_AS_TEXT_FILE = "Pretty print file can not be the same as textFile file";

    public static void main(String[] args) {
        String textfile = null;
        String print = null;
        String pPrint = null;
        String fileName = null;
        String pFileName = null;
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String beginAmPm = null;
        String endDate = null;
        String endTime = null;
        String endAmPm = null;
        String trash = null;
        Date begin_date = null;
        Date end_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        for (String  arg : args) {
            if ("-pretty".equals(arg)) {
                pPrint = arg;
            } else if ("-textFile".equals(arg)) {
                textfile = arg;
            } else if ("-print".equals(arg)) {
                print = arg;
            } else if ("-README".equals(arg)) {
                System.out.println("This is a README file!");
                printReadme();
            }else if (pPrint != null && pFileName == null){
                pFileName = arg;
            } else if (textfile != null && fileName == null){
                fileName = arg;
            } else if (owner == null) {
                owner = arg;
            } else if (description == null){
                if (arg.contains(" ")){
                    description = "\"" + arg + "\"";
                }else{
                    description = arg;
                }
            } else if (beginDate == null){
                beginDate = isDateCorrect(arg);
            } else if (beginTime == null){
                beginTime = isTimeCorrect(arg);
            } else if (beginAmPm == null){
                beginAmPm = arg;
            } else if (endDate == null){
                endDate = isDateCorrect(arg);
            } else if (endTime == null){
                endTime = isTimeCorrect(arg);
            } else if (endAmPm == null) {
                endAmPm = arg;
            }else{
                trash = arg;
            }
        }

        if (trash != null){
            printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
            return;
        }
        if (owner == null) {
            printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
            return;
        } else if (description == null) {
            printErrorMessageAndExit(MISSING_DESCRIPTION);
            return;
        } else if (beginDate == null) {
            printErrorMessageAndExit(MISSING_BEGIN_DATE);
            return;
        } else if (beginTime == null) {
            printErrorMessageAndExit(MISSING_BEGIN_TIME);
            return;
        } else if (beginAmPm == null) {
            printErrorMessageAndExit(MISSING_AMPM);
            return;
        }else if (endDate == null) {
            printErrorMessageAndExit(MISSING_END_DATE);
            return;
        } else if (endTime == null) {
            printErrorMessageAndExit(MISSING_END_TIME);
            return;
        } else if (endAmPm == null) {
            printErrorMessageAndExit(MISSING_AMPM);
            return;
        }

        try {
            begin_date = df.parse(beginDate + " " + beginTime + " " + beginAmPm);
            end_date = df.parse(endDate + " " + endTime + " " + endAmPm);
            if (begin_date.compareTo(end_date) > 0){
                printErrorMessageAndExit(BEGIN_DATE_AFTER_END_DATE);
            }
        }catch (ParseException e){
            printErrorMessageAndExit("Can not parse the date.");
        }

        Appointment appointmentFromArgs = new Appointment(owner, description, begin_date, end_date);
        AppointmentBook appointmentBookFromFile;

        if(fileName != null){
            appointmentBookFromFile = readFile(fileName);
            if (appointmentBookFromFile.getOwnerName() == null){
                appointmentBookFromFile.addAppointment(appointmentFromArgs);
                writeFile(fileName, appointmentBookFromFile);
            }else if (!appointmentBookFromFile.getOwnerName().equals(owner)){
                printErrorMessageAndExit(OWNER_NAME_NOT_EQUAL);
            }else {
                appointmentBookFromFile.addAppointment(appointmentFromArgs);
                writeFile(fileName, appointmentBookFromFile);
            }
        }else {
            appointmentBookFromFile = new AppointmentBook(owner);
            appointmentBookFromFile.addAppointment(appointmentFromArgs);
        }

        if(pFileName != null){
            if (pFileName.equals(fileName)){
                printErrorMessageAndExit(PRETT_FILE_CANNOT_BE_THE_SAME_AS_TEXT_FILE);
                return;
            }
            writePretty(pFileName, appointmentBookFromFile);
        }

        if (print != null) {
            System.out.println(appointmentFromArgs);
        }
        System.exit(0);
    }

    /**
     * Pass the file name to TextParser class to read the file
     * @param fileName the name of the file that the user wants to read
     */
    private static AppointmentBook readFile(String fileName){

        File file = new File(fileName);
        if (!file.exists()) {
            return new AppointmentBook();
        } else {
            try {
                TextParser txt = new TextParser(new FileReader(file));
                return txt.parse();
            } catch (FileNotFoundException | ParserException e) {
                printErrorMessageAndExit("Cannot parse \"" + fileName + "\": " + e.getMessage());
                return null;
            }
        }
    }

    /**
     * Pass the file name and the appointment book to TextDumper to write to the file
     * @param fileName the name of the file that the user wants to write
     * @param appointmentBookFromFile the appointment book that need to write to the file
     */
    private static void writeFile(String fileName, AppointmentBook appointmentBookFromFile) {
        try {
            TextDumper dumper = new TextDumper(new FileWriter(fileName));
            dumper.dump(appointmentBookFromFile);
        } catch (IOException e) {
            printErrorMessageAndExit("Cannot write appointments to " + fileName);
        }
    }

    /**
     * Calling this in main, and invoke the dump() from pretty print class to dump the appointments to the pFileName
     * @param pFileName a file that will contain the pretty print of the appointment book
     * @param appointmentBook the information of appointments that will be dumped to pFileName
     */
    private static void writePretty(String pFileName, AppointmentBook appointmentBook){
        try {
            PrettyPrinter print = new PrettyPrinter((new FileWriter(pFileName)));
            print.dump(appointmentBook);
        } catch (IOException e) {
            printErrorMessageAndExit("Cannot pretty print to " + pFileName);
        }
    }
    /**
     * Open the README.txt file
     */
    private static void printReadme() {
        try (
                InputStream readme = Project3.class.getResourceAsStream("README.txt")
        ) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String txt;
            while ((txt = reader.readLine()) != null) {
                System.out.println(txt);
            }
            reader.close();
            System.exit(0);
        } catch (IOException e) {
            printErrorMessageAndExit("File doesn't exist.");
        }
    }

    /**
     * Returns true or false if the date is correct
     * @param date a date needed to be check if it is within the range
     * @return Returns true or false if the date is valid or invalid
     */
    private static String isDateCorrect(String date) {
        try {
            StringTokenizer stHour = new StringTokenizer(date, "/");
            int month = -1;
            int day = -1;
            int year = -1;
            int trash = 0;

            while (stHour.hasMoreTokens()) {
                if (month == -1){
                    month = Integer.parseInt(stHour.nextToken());
                }else if (day == -1){
                    day = Integer.parseInt(stHour.nextToken());
                }else if (year == -1){
                    year = Integer.parseInt(stHour.nextToken());
                }else{
                    trash = Integer.parseInt(stHour.nextToken());
                }
            }

            if (trash != 0){
                printErrorMessageAndExit(INVALID_DATE + date);
            }else if (month == -1){
                printErrorMessageAndExit(INVALID_DATE + date);
            }else if (day == -1){
                printErrorMessageAndExit(INVALID_DATE + date);
            }else if (year == -1){
                printErrorMessageAndExit(INVALID_DATE + date);
            }

            if (year <= 0 || year >= 10000) {
                printErrorMessageAndExit(YEAR_OUT_OF_BOUNDS + " " + date);
            }
            if (month <= 0 || month >= 13) {
                printErrorMessageAndExit(MONTH_OUT_OF_BOUNDS + " " + date);
            }
            if (day <= 0 || day >= 32) {
                printErrorMessageAndExit(DAY_OUT_OF_BOUNDS + " " + date);
            }

            return date;
        } catch (NumberFormatException ex){
            printErrorMessageAndExit("Invalid Date: " + date);
            return null;
        }
    }

    /**
     * Returns true or false if the time is correct
     * @param time the appointment time needed to be checked
     * @return True or False if the time is valid or invalid
     */
    private static String isTimeCorrect(String time){
        try {
            StringTokenizer st = new StringTokenizer(time, ":");
            int hour = -1;
            int min = -1;
            int trash = 0;

            while (st.hasMoreTokens()) {
                if (hour == -1){
                    hour = Integer.parseInt(st.nextToken());
                }else if (min == -1){
                    min = Integer.parseInt(st.nextToken());
                }else{
                    trash = Integer.parseInt(st.nextToken());
                }
            }

            if (trash != 0){
                printErrorMessageAndExit(INVALID_TIME + time);
            }else if (hour == -1){
                printErrorMessageAndExit(INVALID_TIME + time);
            }else if (min == -1){
                printErrorMessageAndExit(INVALID_TIME + time);
            }

            if (hour < 0 || hour >= 13) {
                printErrorMessageAndExit(HOUR_OUT_OF_BOUNDS + " " + time);
            }
            if (min < 0 || min >= 60) {
                printErrorMessageAndExit(MINS_OUT_OF_BOUNDS + " " + time);
            }
            return time;
        }catch (NumberFormatException ex){
            printErrorMessageAndExit(INVALID_TIME + time);
            return null;
        }
    }

    /**
     * Print error message and exit the program
     * @param message an error message that needed to be printed
     */
    private static void printErrorMessageAndExit(String message){
        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
    }

}