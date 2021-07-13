package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements AppointmentBookParser {
    private static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
    private static final String MISSING_DATA = "Missing file data";
    private static final String TOO_MANY_DATA = "File: Too many data";
    private static final String MISSING_OWNER = "File: Missing Owner";
    private static final String MISSING_DESCRIPTION = "File: Missing Description";
    private static final String MISSING_BEGIN_DATE = "File: Missing Begin Date";
    private static final String MISSING_BEGIN_TIME = "File: Missing Begin Time";
    private static final String MISSING_END_DATE = "File: Missing End Date";
    private static final String MISSING_END_TIME = "File: Missing End Time";
    private static final String YEAR_OUT_OF_BOUNDS = "File: Year out of bounds: ";
    private static final String MONTH_OUT_OF_BOUNDS = "File: Month out of bounds: " ;
    private static final String DAY_OUT_OF_BOUNDS = "File: Day out of bounds: " ;
    private static final String HOUR_OUT_OF_BOUNDS = "File: Hour out of bounds: ";
    private static final String MINS_OUT_OF_BOUNDS = "File: Minutes out of bounds: ";
    private static final String INVALID_DATE = "File: Invalid Date: ";
    private static final String INVALID_TIME = "File: Invalid Time: ";

    private final BufferedReader reader;

    public TextParser(Reader reader){
        this.reader = new BufferedReader(reader);
    }

    @Override
    public AppointmentBook parse() throws ParserException {
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String endDate = null;
        String endTime = null;

        try {
            String oneTextLine;
            String regex = "\"([^\"]*)\"|(\\S+)";

            AppointmentBook newBook = new AppointmentBook();
            while ((oneTextLine = reader.readLine()) != null) {
                Matcher m = Pattern.compile(regex).matcher(oneTextLine);

                if (m.find()){
                    if (m.group(1) != null) {
                        owner = "\"" + m.group(1) + "\"";
                    } else {
                        owner = m.group(2);
                    }
                }else{
                    printErrorMessageAndExit(MISSING_OWNER);
                }

                if (m.find()){
                    if (m.group(1) != null) {
                        description = "\"" + m.group(1) + "\"";
                    } else {
                        description = m.group(2);
                    }
                }else{
                    printErrorMessageAndExit(MISSING_DESCRIPTION);
                }

                if (m.find()){
                    beginDate = isDateCorrect(m.group(2));
                }else{
                    printErrorMessageAndExit(MISSING_BEGIN_DATE);
                }

                if (m.find()){
                    beginTime = isTimeCorrect(m.group(2));
                }else{
                    printErrorMessageAndExit(MISSING_BEGIN_TIME);
                }

                if (m.find()){
                    endDate = isDateCorrect(m.group(2));
                }else{
                    printErrorMessageAndExit(MISSING_END_DATE);
                }

                if (m.find()){
                    endTime = isTimeCorrect(m.group(2));
                }else{
                    printErrorMessageAndExit(MISSING_END_TIME);
                }

                if (m.find()){
                    printErrorMessageAndExit(TOO_MANY_DATA);
                }
                Appointment appt = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
                newBook.addAppointment(appt);
            }
            return newBook;
        } catch (IOException e) {
            AppointmentBook newBook = new AppointmentBook();
            return newBook;
        }
    }
    /**
     * Returns true or false if the date is correct
     * @param date a date needed to be check if it is within the range
     * @return Returns true or false if the date is valid or invalid
     */
    private String isDateCorrect(String date) {
        try {
            StringTokenizer stHour = new StringTokenizer(date, "/");
            int month = -1;
            int day = -1;
            int year = -1;

            if (stHour.hasMoreTokens()){
                month = Integer.parseInt(stHour.nextToken());
            }else{
                printErrorMessageAndExit(INVALID_DATE + " " + date);
            }
            if (stHour.hasMoreTokens()){
                day = Integer.parseInt(stHour.nextToken());
            }else{
                printErrorMessageAndExit(INVALID_DATE+ date);
            }
            if (stHour.hasMoreTokens()){
                year = Integer.parseInt(stHour.nextToken());
            }else{
                printErrorMessageAndExit(INVALID_DATE+ date);
            }

            if(stHour.hasMoreTokens()){
                printErrorMessageAndExit(INVALID_DATE+ date);
            }

            if (year <= 0 || year >= 10000){
                printErrorMessageAndExit(YEAR_OUT_OF_BOUNDS+ date);
            }
            if (month <= 0 || month >= 13){
                printErrorMessageAndExit(MONTH_OUT_OF_BOUNDS + date);
            }
            if (day <= 0 || day >= 32){
                printErrorMessageAndExit(DAY_OUT_OF_BOUNDS + date);
            }
            return date;
        } catch (NumberFormatException ex){
            printErrorMessageAndExit(INVALID_DATE + date);
            return null;
        }
    }

    /**
     * Returns true or false if the time is correct
     * @param time the appointment time needed to be checked
     * @return True or False if the time is valid or invalid
     */
    private String isTimeCorrect(String time) {
        try {
            StringTokenizer st = new StringTokenizer(time, ":");
            int hour = -1;
            int min = -1;

            if (st.hasMoreTokens()) {
                hour = Integer.parseInt(st.nextToken());
            } else {
                printErrorMessageAndExit(INVALID_TIME + time);
            }
            if (st.hasMoreTokens()) {
                min = Integer.parseInt(st.nextToken());
            } else {
                printErrorMessageAndExit(INVALID_TIME + time);
            }

            if (st.hasMoreTokens()) {
                printErrorMessageAndExit(INVALID_TIME + time);
            }

            if (hour < 0 || hour >= 24) {
                printErrorMessageAndExit(HOUR_OUT_OF_BOUNDS + time);
            }
            if (min < 0 || min >= 60) {
                printErrorMessageAndExit(MINS_OUT_OF_BOUNDS + time);
            }
            return time;
        } catch (NumberFormatException ex) {
            printErrorMessageAndExit(INVALID_TIME + time);
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
