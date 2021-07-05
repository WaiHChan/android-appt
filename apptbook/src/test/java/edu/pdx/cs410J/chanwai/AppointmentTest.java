package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest {

  @Test
  void checkGetDescription(){
    String description = "Eyes Check";
    var des = new Appointment("Jim", description, "1/2/2012", "12:21", "3/4/1997", "12:32");
    assertThat(des.getDescription(), equalTo(description));
  }


  @Test
  void checkGetBeginTimeString(){
    String begin_Date = "1/2/1554";
    String begin_Time = "12:42";

    var date = new Appointment("Jim", "Eyes Check", begin_Date, begin_Time, "12/2/1995", "1:23");
    assertThat(date.getBeginTimeString(), equalTo(begin_Date + " " + begin_Time));
  }

  @Test
  void checkGetEndTimeString(){
    String end_Date = "1/2/2005";
    String end_Time = "12:42";

    var date = new Appointment("Jim", "Eyes Check", "12/2/1995", "1:23", end_Date, end_Time);
    assertThat(date.getEndTimeString(), equalTo(end_Date + " " + end_Time));
  }

  @Test
  void checkAssignmentToString(){
    String description = "Eyes Check";
    String begin_Date = "1/2/1554";
    String begin_Time = "12:42";
    String end_Date = "1/2/2005";
    String end_Time = "12:42";

    var app = new Appointment("Jim", description, begin_Date, begin_Time, end_Date, end_Time);
    assertThat(app.toString(), equalTo("Eyes Check from 1/2/1554 12:42 until 1/2/2005 12:42"));
  }

  @Test
  void toStringContainDescription(){
    String description = "Eyes Check";
    var des = new Appointment("Jim", description, "1/2/2012", "12:21", "3/4/1997", "12:32");

    assertThat(des.toString(), containsString("Eyes Check"));
  }

  @Test
  void toStringContainBeginTime(){
    String description = "Eyes Check";
    String begin_Date = "1/2/1554";
    String begin_Time = "12:42";

    var time = new Appointment("Jim", description, begin_Date, begin_Time, "3/4/1997", "12:32");

    assertThat(time.toString(), containsString("1/2/1554 12:42"));
  }

  @Test
  void toStringContainEndTime(){
    String end_Date = "1/2/2005";
    String end_Time = "12:42";

    var date = new Appointment("Jim", "Eyes Check", "12/2/1995", "1:23", end_Date, end_Time);

    assertThat(date.toString(), containsString("1/2/2005 12:42"));
  }

}
