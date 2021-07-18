package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 *
 */
public class AppointmentTest {

  /**
   * Tests that invoking the getDescription method from appointment class
   * Check if getDescription returns the correct strings
   */
  @Test
  void checkGetDescription() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var des = new Appointment("Jim", description, begin_date, end_date);
    assertThat(des.getDescription(), equalTo(description));
  }

  /**
   * Tests that invoking the getBeginTimeString method from appointment class
   * Check if getBeginTimeString() returns the correct strings
   */
  @Test
  void checkGetBeginTime() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var date = new Appointment("Jim", description, begin_date, end_date);

    assertThat(date.getBeginTime(), equalTo(begin_date));
  }

  /**
   * Tests that invoking the getBeginTimeString method from appointment class
   * Check if getBDateString() returns the correct strings
   */
  @Test
  void checkGetBDateTime() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var date = new Appointment("Jim", description, begin_date, end_date);

    assertThat(date.getBDateString(), equalTo("11/12/2019 10:00 AM"));
  }

  /**
   * Tests that invoking the getBeginTimeString method from appointment class
   * Check if getEDateString() returns the correct strings
   */
  @Test
  void checkGetEDateTime() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var date = new Appointment("Jim", description, begin_date, end_date);

    assertThat(date.getEDateString(), equalTo("11/12/2019 10:30 AM"));
  }

  /**
   * Tests that invoking the getEndTimeString method from appointment class
   * Check if getEndTimeString() returns the correct string
   */
  @Test
  void checkGetEndTime() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var date = new Appointment("Jim", description, begin_date, end_date);
    assertThat(date.getEndTime(), equalTo(end_date));
  }

  /**
   * Tests that invoking the toString method from appointment class
   * Check if toString() returns the correct string
   */
  @Test
  void checkAssignmentToString() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var app = new Appointment("Jim", description, begin_date, end_date);

    assertThat(app.toString(), equalTo("Eyes Check from 11/12/19, 10:00 AM until 11/12/19, 10:30 AM"));
  }

  /**
   * Tests that invoking the toString method from appointment class
   * Check if toString() contains the correct string of description
   */
  @Test
  void toStringContainDescription() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var app = new Appointment("Jim", description, begin_date, end_date);

    assertThat(app.toString(), containsString("Eyes Check"));
  }

  /**
   * Tests that invoking the toString method from appointment class
   * Check if toString() contains the correct string of description begin time
   */
  @Test
  void toStringContainBeginTime() throws ParseException {
    String description = "Eyes Check";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date begin_date = df.parse("11/12/2019 " + "10:00 " + "am");
    Date end_date = df.parse("11/12/2019 " + "10:30 " + "am");
    var app = new Appointment("Jim", description, begin_date, end_date);

    assertThat(app.toString(), equalTo("Eyes Check from 11/12/19, 10:00 AM until 11/12/19, 10:30 AM"));
  }
}
