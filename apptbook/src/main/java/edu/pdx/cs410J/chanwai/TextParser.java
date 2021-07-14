package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements AppointmentBookParser {
    private static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
    private static final String TOO_MANY_DATA = "File: Too many data";
    static final String MISSING_OWNER = "File: Missing Owner";
    static final String MISSING_DESCRIPTION = "File: Missing Description";
    static final String MISSING_BEGIN_DATE = "File: Missing Begin Date";
    static final String MISSING_BEGIN_TIME = "File: Missing Begin Time";
    static final String MISSING_END_DATE = "File: Missing End Date";
    static final String MISSING_END_TIME = "File: Missing End Time";
    static final String YEAR_OUT_OF_BOUNDS = "File: Year out of bounds: ";
    static final String MONTH_OUT_OF_BOUNDS = "File: Month out of bounds: " ;
    static final String DAY_OUT_OF_BOUNDS = "File: Day out of bounds: " ;
    static final String HOUR_OUT_OF_BOUNDS = "File: Hour out of bounds: ";
    static final String MINS_OUT_OF_BOUNDS = "File: Minutes out of bounds: ";
    static final String INVALID_DATE = "File: Invalid Date: ";
    static final String INVALID_TIME = "File: Invalid Time: ";

    private final BufferedReader reader;

    public TextParser(Reader reader){
        this.reader = new BufferedReader(reader);
    }

    @Override
    public AppointmentBook parse() throws ParserException {

        try {
            String oneTextLine;
            String regex = "\"([^\"]*)\"|(\\S+)";

            AppointmentBook newBook = new AppointmentBook();
            while ((oneTextLine = reader.readLine()) != null) {
                Matcher m = Pattern.compile(regex).matcher(oneTextLine);
                String owner = extractValue(m, MISSING_OWNER);

                String description = extractValue(m, MISSING_DESCRIPTION);

                String beginDate = extractDate(m, MISSING_BEGIN_DATE);

                String beginTime = extractTime(m, MISSING_BEGIN_TIME);

                String endDate = extractDate(m, MISSING_END_DATE);

                String endTime = extractTime(m, MISSING_END_TIME);

   /*             try{
                    if (m.find()){
                        throw new ParserException(TOO_MANY_DATA);
                    }
                }catch (ParserException e){
                    throw new ParserException(TOO_MANY_DATA);
                }*/

                Appointment appt = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
                newBook.addAppointment(appt);
            }
            return newBook;
        } catch (IOException e) {
            throw new ParserException("Could not parse appointment book", e);
        }
    }

    private String extractValue(Matcher m, String missingValueMessage) throws ParserException {
        String owner;
        try{
            if (m.find()) {
                if (m.group(1) != null) {
                    owner = "\"" + m.group(1) + "\"";
                } else {
                    owner = m.group(2);
                }
            }else {
                throw new ParserException(missingValueMessage);
            }
        }catch (ParserException e){
            throw new ParserException(missingValueMessage);
        }
        return owner;
    }

    private String extractDate(Matcher m, String missingValueMessage) throws ParserException {
        String endDate;
        try{
            if (m.find()){
                endDate = isDateCorrect(m.group(2));
            }else {
                throw new ParserException(missingValueMessage);
            }
        }catch (ParserException e){
            throw new ParserException(missingValueMessage);
        }
        return endDate;
    }

    private String extractTime(Matcher m, String missingValueMessage) throws ParserException {
        String endTime;
        try{
            if (m.find()){
                endTime = isTimeCorrect(m.group(2));
            }else {
                throw new ParserException(missingValueMessage);
            }
        }catch (ParserException e){
            throw new ParserException(missingValueMessage);
        }
        return endTime;
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
