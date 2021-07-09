package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class TextParser implements AppointmentBookParser {

    private String fileName;

    TextParser(String name){
        this.fileName = name;
    }
    @Override
    public AppointmentBook parse() throws ParserException {
        String owner;
        String description;
        String begin_date;
        String begin_time;
        String end_date;
        String end_time;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String oneTextLine;
            AppointmentBook newBook = new AppointmentBook();
            while ((oneTextLine = br.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(oneTextLine);
                owner = token.nextToken();
                description = token.nextToken();
                begin_date = token.nextToken();
                begin_time = token.nextToken();
                end_date = token.nextToken();
                end_time = token.nextToken();

                Appointment new_appt = new Appointment(owner, description, begin_date, begin_time, end_date, end_time);
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
}
