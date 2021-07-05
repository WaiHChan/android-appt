package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

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
    void checkAddAppointment(){
        String name = "Jim";
        String description = "Eyes Check";
        String begin_Date = "1/2/1554";
        String begin_Time = "12:42";
        String end_Date = "1/2/2005";
        String end_Time = "12:42";

        var app = new Appointment(name, description, begin_Date, begin_Time, end_Date, end_Time);
        AppointmentBook newBook = new AppointmentBook(name);
        newBook.addAppointment(app);
        assertThat(newBook.toString(), containsString("Jim's appointment book with 1 appointments"));
    }
}
