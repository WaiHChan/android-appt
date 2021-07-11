package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class TextParser implements AppointmentBookParser {
    static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
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

    private String fileName;

    TextParser(String name){
        this.fileName = name;
    }
    @Override
    public AppointmentBook parse() throws ParserException {
        String owner;
        String description;
        String beginDate;
        String beginTime;
        String endDate;
        String endTime;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String oneTextLine;
            AppointmentBook newBook = new AppointmentBook();
            while ((oneTextLine = br.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(oneTextLine);
                owner = token.nextToken();
                description = token.nextToken();
                beginDate = isDateCorrect(token.nextToken());
                beginTime = isTimeCorrect(token.nextToken());
                endDate = isDateCorrect(token.nextToken());
                endTime = isTimeCorrect(token.nextToken());

                if (owner == null) {
                    printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
                } else if (description == null) {
                    printErrorMessageAndExit(MISSING_DESCRIPTION);
                } else if (beginDate == null) {
                    printErrorMessageAndExit(MISSING_BEGIN_DATE);
                } else if (beginTime == null) {
                    printErrorMessageAndExit(MISSING_BEGIN_TIME);
                } else if (endDate == null) {
                    printErrorMessageAndExit(MISSING_END_DATE);
                } else if (endTime == null) {
                    printErrorMessageAndExit(MISSING_END_TIME);
                }

                Appointment new_appt = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
                newBook.addAppointment(new_appt);
            }
            return newBook;
        } catch (FileNotFoundException exception) {
            System.err.println("File not found!" + fileName);
        } catch (IOException e) {
            System.err.println("Can't read the file" + fileName);
        }
        return null;
    }
    /**
     * Returns true or false if the date is correct
     * @param date a date needed to be check if it is within the range
     * @return Returns true or false if the date is valid or invalid
     */
    private String isDateCorrect(String date) {
        try {
            StringTokenizer stHour = new StringTokenizer(date, "/");
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
    private String isTimeCorrect(String time){
        try {
            StringTokenizer st = new StringTokenizer(time, ":");
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
    private void printErrorMessageAndExit(String message){
        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
    }
}
