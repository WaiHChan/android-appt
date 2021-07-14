package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {
    /**
     * Tests that invoking the dump() and parse() method
     * It shows that both methods run correctly
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException {
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

        sw.flush();

        String dumpedText = sw.toString();
        assertThat(dumpedText, containsString(owner));
    }
}
