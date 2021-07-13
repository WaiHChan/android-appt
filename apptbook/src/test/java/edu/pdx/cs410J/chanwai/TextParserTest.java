package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.io.TempDir;


public class TextParserTest {

 /*   @Test
    void appointmentBookOwnerCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {

        File textFile = new File(dir, "appointments.txt");

        String owner = "Owner";
        String description = "Eyes Check";
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
