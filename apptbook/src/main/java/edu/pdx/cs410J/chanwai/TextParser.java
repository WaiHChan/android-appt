package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements AppointmentBookParser {
    private static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
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

    /**
     * Create a bufferedReader
     * @param reader Create a bufferedReader for reading file
     */
    public TextParser(Reader reader){
        this.reader = new BufferedReader(reader);
    }

    /**
     * Read a file and return an appointment book
     * @return it returns an appointment book
     * @throws ParserException if it can't read the data, raise exception
     */
    @Override
    public AppointmentBook parse() throws ParserException {
        String owner;
        String description;
        String beginDate;
        String beginTime;
        String endDate;
        String endTime;

        try {
            String oneTextLine;
            String regex = "\"([^\"]*)\"|(\\S+)";

            AppointmentBook newBook = new AppointmentBook();
            while ((oneTextLine = reader.readLine()) != null) {
                Matcher m = Pattern.compile(regex).matcher(oneTextLine);
                try{
                    if (m.find()) {
                        if (m.group(1) != null) {
                            owner = "\"" + m.group(1) + "\"";
                        } else {
                            owner = m.group(2);
                        }
                    }else {
                        throw new ParserException(MISSING_OWNER);
                    }
                }catch (ParserException e){
                    throw new ParserException(MISSING_OWNER);
                }

                try{
                    if (m.find()){
                        if (m.group(1) != null) {
                            description = "\"" + m.group(1) + "\"";
                        } else {
                            description = m.group(2);
                        }
                    }else {
                        throw new ParserException(MISSING_DESCRIPTION);
                    }
                }catch (ParserException e){
                    throw new ParserException(MISSING_DESCRIPTION);
                }

                try{
                    if (m.find()){
                        beginDate = isDateCorrect(m.group(2));
                    }else {
                        throw new ParserException(MISSING_BEGIN_DATE);
                    }
                }catch (ParserException e){
                    throw new ParserException(MISSING_BEGIN_DATE);
                }

                try{
                    if (m.find()){
                        beginTime = isTimeCorrect(m.group(2));
                    }else {
                        throw new ParserException(MISSING_BEGIN_TIME);
                    }
                }catch (ParserException e){
                    throw new ParserException(MISSING_BEGIN_TIME);
                }

                try{
                    if (m.find()){
                        endDate = isDateCorrect(m.group(2));
                    }else {
                        throw new ParserException(MISSING_END_DATE);
                    }
                }catch (ParserException e){
                    throw new ParserException(MISSING_END_DATE);
                }

                try{
                    if (m.find()){
                        endTime = isTimeCorrect(m.group(2));
                    }else {
                        throw new ParserException(MISSING_END_TIME);
                    }
                }catch (ParserException e){
                    throw new ParserException(MISSING_END_TIME);
                }

                Appointment appt = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
                newBook.addAppointment(appt);
            }
            return newBook;
        } catch (IOException e) {
            return new AppointmentBook();
        }
    }
    /**
     * Returns true or false if the date is correct
     * @param date a date needed to be check if it is within the range
     * @return Returns true or false if the date is valid or invalid
     */
    private String isDateCorrect(String date) throws ParserException {
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

            try {
                if (trash != 0) {
                    throw new ParserException(INVALID_DATE);
                }
            }catch (ParserException e){
                throw new ParserException(INVALID_DATE);
            }

            try {
                if (month == -1){
                    throw new ParserException(INVALID_DATE);
                }
            }catch (ParserException e){
                throw new ParserException(INVALID_DATE);
            }

            try {
                if (day == -1){
                    throw new ParserException(INVALID_DATE);
                }
            }catch (ParserException e){
                throw new ParserException(INVALID_DATE);
            }

            try {
                if (year == -1){
                    throw new ParserException(INVALID_DATE);
                }
            }catch (ParserException e){
                throw new ParserException(INVALID_DATE);
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
