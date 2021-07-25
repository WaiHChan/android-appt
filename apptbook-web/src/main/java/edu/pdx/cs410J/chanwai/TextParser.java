package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements AppointmentBookParser {
    private final Reader reader;

    /**
     * Create a Reader
     * @param reader Create a bufferedReader for reading file
     */
    public TextParser(Reader reader){
        this.reader = reader;
    }

    /**
     * Read a file and return an appointment book
     * @return it returns an appointment book
     * @throws ParserException if it can't read the data, raise exception
     */
    @Override
    public AppointmentBook parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.reader);
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
            AppointmentBook book = new AppointmentBook();
            while ((oneTextLine = br.readLine()) != null) {
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
                    beginDate = m.group(2);
                }
                if (m.find()){
                    beginTime = m.group(2);
                }
                if (m.find()){
                    beginAmPm = m.group(2);
                }
                if (m.find()){
                    endDate = m.group(2);
                }
                if (m.find()){
                    endTime = m.group(2);
                }
                if (m.find()){
                    endAmPm = m.group(2);
                }
                try {
                    begin_date = df.parse(beginDate + " " + beginTime + " " + beginAmPm);
                    end_date = df.parse(endDate + " " + endTime + " " + endAmPm);
                }catch (ParseException e){
                    throw new ParserException("While reading text", e);
                }
                book.addAppointment(new Appointment(owner, description, begin_date, end_date));
            }
            return book;
        } catch (IOException e) {
            throw new ParserException("While reading text", e);
        }
    }
}
