package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.io.TempDir;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
   /* @Test
    void ifOwnerIsMissing() throws IOException, ParserException {

        //Appointment appt = new Appointment(null, null, null, null, null, null);
        AppointmentBook book = new AppointmentBook();
       // book.addAppointment(appt);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(ParserException, containsString(MISSING_OWNER));
    }*/

/*
    @Test
    void appointmentBookOwnerCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {

        File textFile = new File(dir, "appt.txt");

        String owner = "Owner";
        String description = "Eyes";
        String begin_Date = "1/2/1554";
        String begin_Time = "12:42";
        String end_Date = "1/2/2005";
        String end_Time = "12:42";

        Appointment appt = new Appointment(owner, description, begin_Date, begin_Time, end_Date, end_Time);
        AppointmentBook book = new AppointmentBook(owner);
        book.addAppointment(appt);

        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(book);

        TextParser parser = new TextParser(new FileReader(textFile));
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }*/
}
