package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class TextParserTest {

    /**
     * Tests that invoking the dump() and parse() method from TestDump class and TestParser class
     * Check if an appointment book is created and saved in StringWriter and
     * Check if an appointment book can be parsed after the dump
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     * @throws ParserException Throw an Parser exception if the appointment book cannot be read
     */
    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
        String owner = "Owner";
        String description = "Eyes";
        String begin_Date = "1/2/1554";
        String begin_Time = "12:42";
        String end_Date = "1/2/2005";
        String end_Time = "12:42";

        Appointment appt = new Appointment(owner, description, begin_Date, begin_Time, end_Date, end_Time);
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }

    /**
     * Tests that invoking the dump() and parse() method from TestDump class and TestParser class
     * Check if an appointment book is created and saved in StringWriter and
     * Check if an appointment book can be parsed after the dump
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     * @throws ParserException Throw an Parser exception if the appointment book cannot be read
     */
    @Test
    void appointmentBookParsed() throws IOException, ParserException {
        String owner = "\"Owner One\"";
        String description = "Eyes";
        String begin_Date = "1/2/1554";
        String begin_Time = "12:42";
        String end_Date = "1/2/2005";
        String end_Time = "12:42";

        Appointment appt = new Appointment(owner, description, begin_Date, begin_Time, end_Date, end_Time);
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }

    /**
     * Tests that invoking the dump() and parse() method
     * If there is missing owner, throw exception
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void missingOwner() throws IOException{

        Appointment appt = new Appointment("", "", "", "", "", "");
        AppointmentBook book = new AppointmentBook();
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));

        Throwable exception = assertThrows(ParserException.class, parser::parse);
        assertEquals(TextParser.MISSING_OWNER, exception.getMessage());
    }

    /**
     * Tests that invoking the dump() and parse() method
     * If there is missing description, throw exception
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void missingDescription() throws IOException{

        Appointment appt = new Appointment("Jim", "", "", "", "", "");
        AppointmentBook book = new AppointmentBook();
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));

        Throwable exception = assertThrows(ParserException.class, parser::parse);
        assertEquals(TextParser.MISSING_DESCRIPTION, exception.getMessage());
    }

    /**
     * Tests that invoking the dump() and parse() method
     * If there is missing begin date, throw exception
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void missingBeginDate() throws IOException{

        Appointment appt = new Appointment("Jim", "Temp", "", "", "", "");
        AppointmentBook book = new AppointmentBook();
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));

        Throwable exception = assertThrows(ParserException.class, parser::parse);
        assertEquals(TextParser.MISSING_BEGIN_DATE, exception.getMessage());
    }

    /**
     * Tests that invoking the dump() and parse() method
     * If there is missing begin time, throw exception
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void missingBeginTime() throws IOException{

        Appointment appt = new Appointment("Jim", "Temp", "12/2/1493", "", "", "");
        AppointmentBook book = new AppointmentBook();
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));

        Throwable exception = assertThrows(ParserException.class, parser::parse);
        assertEquals(TextParser.MISSING_BEGIN_TIME, exception.getMessage());
    }

    /**
     * Tests that invoking the dump() and parse() method
     * If there is missing end date, throw exception
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void missingEndDate() throws IOException{

        Appointment appt = new Appointment("Jim", "Temp", "12/2/1493", "12:22", "", "");
        AppointmentBook book = new AppointmentBook();
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));

        Throwable exception = assertThrows(ParserException.class, parser::parse);
        assertEquals(TextParser.MISSING_END_DATE, exception.getMessage());
    }

    /**
     * Tests that invoking the dump() and parse() method
     * If there is missing end time, throw exception
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void missingEndTime() throws IOException{

        Appointment appt = new Appointment("Jim", "Temp", "12/2/1493", "12:22", "1/2/1995", "");
        AppointmentBook book = new AppointmentBook();
        book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));

        Throwable exception = assertThrows(ParserException.class, parser::parse);
        assertEquals(TextParser.MISSING_END_TIME, exception.getMessage());
    }
}
