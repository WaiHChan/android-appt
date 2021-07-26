package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

    /**
     * Tests that invoking the dump() and parse() method from TestDump class and TestParser class
     * Check if an appointment book is created and saved in StringWriter and
     * Check if an appointment book can be parsed after the dump
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     * @throws ParserException Throw an Parser exception if the appointment book cannot be read
     */
    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException, ParseException {
        String description = "Eyes";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
        Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
        var app = new Appointment("Jim", description, begin_date, end_date);

        AppointmentBook book = new AppointmentBook("Jim");
        book.addAppointment(app);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo("Jim"));
    }

    /**
     * Tests that invoking the dump() and parse() method from TestDump class and TestParser class
     * Check if an appointment book is created and saved in StringWriter and
     * Check if an appointment book can be parsed after the dump
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     * @throws ParserException Throw an Parser exception if the appointment book cannot be read
     */
    @Test
    void checkNameIsQuoted() throws IOException, ParserException, ParseException {
        String name = "\"Jim Chan\"";
        String description = "Eyes";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
        Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
        var app = new Appointment(name, description, begin_date, end_date);

        AppointmentBook book = new AppointmentBook(name);
        book.addAppointment(app);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(name));
    }

    /**
     * Tests that invoking the dump() and parse() method from TestDump class and TestParser class
     * Check if an appointment book is created and saved in StringWriter and
     * Check if an appointment book can be parsed after the dump
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     * @throws ParserException Throw an Parser exception if the appointment book cannot be read
     */
    @Test
    void appointmentBookParsed() throws IOException, ParserException, ParseException {
        String owner = "Jim";
        String description = "\"Eyes Check\"";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
        Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
        var app = new Appointment(owner, description, begin_date, end_date);
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(app);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }

}
