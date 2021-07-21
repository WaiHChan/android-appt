package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements AppointmentBookParser {
    static final String BEGIN_DATE_AFTER_END_DATE = "Begin date occurs after End date";
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
        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String beginAmPm = null;
        String endDate = null;
        String endTime = null;
        String endAmPm = null;
        Date begin_date = null;
        Date end_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        try {
            String oneTextLine;
            String regex = "\"([^\"]*)\"|(\\S+)";

            AppointmentBook newBook = new AppointmentBook();
            while ((oneTextLine = reader.readLine()) != null) {
                Matcher m = Pattern.compile(regex).matcher(oneTextLine);

                if (m.find()) {
                    if (m.group(1) != null) {
                        owner = "\"" + m.group(1) + "\"";
                    } else {
                        owner = m.group(2);
                    }
                }
                if (m.find()){
                    if (m.group(1) != null) {
                        description = "\"" + m.group(1) + "\"";
                    } else {
                        description = m.group(2);
                    }
                }
                if (m.find()){
                    beginDate = m.group(2);//isDateCorrect(m.group(2));
                }
                if (m.find()){
                    beginTime = m.group(2);//isTimeCorrect(m.group(2));
                }
                if (m.find()){
                    beginAmPm = m.group(2);
                }
                if (m.find()){
                    endDate = m.group(2);//isDateCorrect(m.group(2));
                }
                if (m.find()){
                    endTime = m.group(2);//isTimeCorrect(m.group(2));
                }
                if (m.find()){
                    endAmPm = m.group(2);
                }

                try {
                    begin_date = df.parse(beginDate + " " + beginTime + " " + beginAmPm);
                    end_date = df.parse(endDate + " " + endTime + " " + endAmPm);
                    if (begin_date.compareTo(end_date) > 0){
                        throw new ParserException(BEGIN_DATE_AFTER_END_DATE);
                    }
                }catch (ParseException e){
                    throw new ParserException("Can not parse the data from the file.");
                }

                Appointment appt = new Appointment(owner, description, begin_date, end_date);
                newBook.addAppointment(appt);
            }
            reader.close();
            return newBook;
        } catch (IOException e) {
            return new AppointmentBook();
        }
    }
}
