package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {
    /**
     * Tests that invoking the dump() and parse() method
     * It shows that both methods run correctly
     * @throws IOException Throw an IO exception if the appointment cannot be dumped to a file
     */
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException, ParseException {
        String description = "Eyes Check";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
        Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
        var app = new Appointment("Jim", description, begin_date, end_date);
        AppointmentBook book = new AppointmentBook("Jim");
        book.addAppointment(app);

        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book);

        sw.flush();

        String dumpedText = sw.toString();
        assertThat(dumpedText, containsString("Jim"));
    }
}
