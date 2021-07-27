package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link AppointmentBook} class.
 */

public class AppointmentBookTest {

    /**
     * Tests that invoking the AppointmentBook.getOwnerName()
     * Check if getOwnerName() returns the correct name of newly added appointment
     */
    @Test
    void ownerNamedJimIsNamedJim(){
        String name = "Jim";
        var jim = new AppointmentBook(name);

        assertThat(jim.getOwnerName(), equalTo(name));
    }

    /**
     * Tests that invoking the toString method from AbstractAppointmentBook
     * Check if the toString method displays the correct string
     */
    @Test
    void checkAddAppointment() throws ParseException {
        String description = "Eyes Check";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
        Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
        var app = new Appointment("Jim", description, begin_date, end_date);
        AppointmentBook newBook = new AppointmentBook("Jim");
        newBook.addAppointment(app);
        assertThat(newBook.toString(), containsString("Jim's appointment book with 1 appointments"));
    }

    /**
     * Test the addAppointment can add the appointment book
     * @throws ParseException
     */
    @Test
    void addingAnAppointmentAddsTheAppointment() throws ParseException {
        String description = "Eyes Check";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
        Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
        var app = new Appointment("Jim", description, begin_date, end_date);
        AppointmentBook newBook = new AppointmentBook("Jim");
        newBook.addAppointment(app);
        assertThat(newBook.getAppointments(), hasItem(app));
    }
}
