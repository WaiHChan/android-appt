package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project2 {

    static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
    static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
    static final String TOO_MANY_COMMAND_LINE_ARGUMENTS = "Too many command line arguments";
    static final String MISSING_DESCRIPTION = "Missing Description";
    static final String MISSING_BEGINE_DATE = "Missing Begin Date";
    static final String MISSING_BEGINE_TIME = "Missing Begin Time";
    static final String MISSING_END_DATE = "Missing End Date";
    static final String MISSING_END_TIME = "Missing End Time";
    static final String YEAR_OUT_OF_BOUNDS = "Year out of bounds";
    static final String MONTH_OUT_OF_BOUNDS = "Month out of bounds" ;
    static final String DAY_OUT_OF_BOUNDS = "Day out of bounds" ;
    static final String HOUR_OUT_OF_BOUNDS = "Hour out of bounds";
    static final String MINS_OUT_OF_BOUNDS = "Minutes out of bounds";

    public static void main(String[] args) {
        String print = null;
        String file = null;
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String endDate = null;
        String endTime = null;


        for (String  arg : args) {
            if ("-textFile".equals(arg)){
                String temp = arg;
            }else if ("-print".equals(arg)){
                print = arg;
            }else if ("-README".equals(arg)) {
                System.out.println("This is a README file!");
                printReadme();
            }else if (file == null){
                file = arg;
                readFile(file);
            } else if (owner == null) {
                owner = arg;
            } else if (description == null){
                description = arg;
            } else if (beginDate == null){
                beginDate = isDateCorrect(arg);
            } else if (beginTime == null){
                beginTime = isTimeCorrect(arg);
            } else if (endDate == null){
                endDate = isDateCorrect(arg);
            } else if (endTime == null){
                endTime = isTimeCorrect(arg);
            }
        }

        if (owner == null) {
            printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
            return;
        } else if (description == null) {
            printErrorMessageAndExit(MISSING_DESCRIPTION);
            return;
        } else if (beginDate == null) {
            printErrorMessageAndExit(MISSING_BEGINE_DATE);
            return;
        } else if (beginTime == null) {
            printErrorMessageAndExit(MISSING_BEGINE_TIME);
            return;
        } else if (endDate == null) {
            printErrorMessageAndExit(MISSING_END_DATE);
            return;
        } else if (endTime == null) {
            printErrorMessageAndExit(MISSING_END_TIME);
            return;
        }

        Appointment appointment = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
        AppointmentBook newBook = new AppointmentBook(owner);
        newBook.addAppointment(appointment);
        if (print != null) {
            System.out.println(appointment);
            System.exit(1);
        }
        System.exit(0);
    }

    private static void readFile(String arg){
        try {
            TextParser txt = new TextParser(arg);
            AppointmentBook appointmentBookFromFile = txt.parse();
            writeFile(arg, appointmentBookFromFile);
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String fileName, AppointmentBook appointmentBookFromFile) {
        try {
            TextDumper dumper = new TextDumper(fileName);
            dumper.dump(appointmentBookFromFile);
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    /**
     * Open the README.txt file
     */
    private static void printReadme() {
        try (
                InputStream readme = Project2.class.getResourceAsStream("README.txt")
        ) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String txt;
            while ((txt = reader.readLine()) != null) {
                System.out.println(txt);
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("File doesn't exist.");
            e.printStackTrace();
        }
        System.exit(1);
    }

    /**
     * Returns true or false if the date is correct
     * @param date a date needed to be check if it is within the range
     * @return Returns true or false if the date is valid or invalid
     */
    private static String isDateCorrect(String date) {
        StringTokenizer stHour = new StringTokenizer(date, "/");
        try {
            int month = Integer.parseInt(stHour.nextToken());
            int day = Integer.parseInt(stHour.nextToken());
            int year = Integer.parseInt(stHour.nextToken());

            if (year <= 0 || year >= 10000){
                printErrorMessageAndExit(YEAR_OUT_OF_BOUNDS);
            }
            if (month <= 0 || month >= 13){
                printErrorMessageAndExit(MONTH_OUT_OF_BOUNDS);
            }
            if (day <= 0 || day >= 32){
                printErrorMessageAndExit(DAY_OUT_OF_BOUNDS);
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
        StringTokenizer st = new StringTokenizer(time, ":");
        try {
            int hour = Integer.parseInt(st.nextToken());
            int min = Integer.parseInt(st.nextToken());

            if (hour < 0 || hour >= 24) {
                printErrorMessageAndExit(HOUR_OUT_OF_BOUNDS);
            }
            if (min < 0 || min >= 60) {
                printErrorMessageAndExit(MINS_OUT_OF_BOUNDS);
            }
            return time;
        }catch (NumberFormatException ex){
            printErrorMessageAndExit("Invalid Time: " + time);
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

    /**
     * Convert a string to two decimal
     * @param arg an string representing the minutes of the appointment
     * @return a converted two decimal string
     */
    private static String formatToTwoDecimal(String arg){
        StringTokenizer time = new StringTokenizer(arg, ":");
        String hour = time.nextToken();
        int temp_mins = Integer.parseInt(time.nextToken());

        DecimalFormat formatter = new DecimalFormat("00");
        String mins = formatter.format(temp_mins);

        return hour + ":" + mins;
    }
}