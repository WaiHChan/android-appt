package edu.pdx.cs410J.chanwai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Month;
import java.util.StringTokenizer;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
  static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
  static final String TOO_MANY_COMMAND_LINE_ARGUMENTS = "Too many command line arguments";
  static final String MISSING_OWNER = "Missing Owner";
  static final String MISSING_DESCRIPTION = "Missing Description";
  static final String MISSING_BEGINE_DATE = "Missing Begin Date";
  static final String MISSING_BEGINE_TIME = "Missing Begin Time";
  static final String MISSING_END_DATE = "Missing End Date";
  static final String MISSING_END_TIME = "Missing End Time";
  static final String YEAR_OUT_OF_BOUNDS = "Year out of bounds";
  static final String MONTH_OUT_OF_BOUNDS = "Month out of bounds" ;
  static final String DAY_OUT_OF_BOUNDS = "Day out of bounds" ;
  static final String HOUR_OUT_OF_BOUNDS = "Hour out of bounds";
  static final String MINS_OUT_OF_BOUNDS = "Minutes out of bounds";

  public static void main(String[] args) {

    if (args.length == 0) {
      printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
    } else if (args.length == 1) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
      } else if ("-README".equals(args[0])) {
        try (
                InputStream readme = Project1.class.getResourceAsStream("README.txt")
        ) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String txt;
          while ((txt = reader.readLine()) != null) {
            System.out.println(txt);
          }
          reader.close();
        } catch (IOException e) {
          System.out.println("Can not find the file.");
          e.printStackTrace();
        }
        System.exit(1);
      } else { //If it is not -print/-readme, it is owner_name
        printErrorMessageAndExit(MISSING_DESCRIPTION);
      }
    } else if (args.length == 2) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_DESCRIPTION);
      } else if ("-README".equals(args[0])) {
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      } else {
        printErrorMessageAndExit(MISSING_BEGINE_DATE);
      }
    } else if (args.length == 3) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_BEGINE_DATE);
      } else if ("-README".equals(args[0])) {
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      } else {
        printErrorMessageAndExit(MISSING_BEGINE_TIME);
      }
    } else if (args.length == 4) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_BEGINE_TIME);
      } else if ("-README".equals(args[0])) {
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      } else {
        printErrorMessageAndExit(MISSING_END_DATE);
      }
    } else if (args.length == 5) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_END_DATE);
      } else if ("-README".equals(args[0])) {
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      } else {
        printErrorMessageAndExit(MISSING_END_TIME);
      }
    } else if (args.length == 6) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_END_TIME);
      } else if ("-README".equals(args[0])) {
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      } else {
        if(isDateCorrect(args[2]) && isTimeCorrect(args[3])) {
          Appointment appointment = new Appointment();
          appointment.owner = args[0];
          appointment.description = args[1];
          appointment.beginDate = args[2];
          appointment.beginTime = args[3];
          appointment.endDate = args[4];
          appointment.endTime = args[5];
          AppointmentBook newBook = new AppointmentBook();
          newBook.addAppointment(appointment);
        }
      }
    } else if (args.length == 7) {
      if ("-print".equals(args[0])) {
        if(isDateCorrect(args[3]) && isTimeCorrect(args[4])) {
          Appointment appointment = new Appointment();
          appointment.owner = args[1];
          appointment.description = args[2];
          appointment.beginDate = args[3];
          appointment.beginTime = args[4];
          appointment.endDate = args[5];
          appointment.endTime = args[6];
          AppointmentBook newBook = new AppointmentBook();
          newBook.addAppointment(appointment);
          System.out.println(appointment);
        }
      } else {
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      }
    }
    System.exit(0);
  }

  private static boolean isDateCorrect(String date) {
    StringTokenizer stHour = new StringTokenizer(date, "/");

    try {
      int month = Integer.parseInt(stHour.nextToken());
      int day = Integer.parseInt(stHour.nextToken());
      int year = Integer.parseInt(stHour.nextToken());

      if (year <= 0 || year >= 10000){
        printErrorMessageAndExit(YEAR_OUT_OF_BOUNDS);
      }
      if (month <= 0 || month >= 13){
        printErrorMessageAndExit(MONTH_OUT_OF_BOUNDS);
      }
      if (day <= 0 || day >= 32){
        printErrorMessageAndExit(DAY_OUT_OF_BOUNDS);
      }
    } catch (NumberFormatException ex){
      printErrorMessageAndExit("Invalid Date: " + date);
    }
    return true;
  }

  private static boolean isTimeCorrect(String mins){
    StringTokenizer st = new StringTokenizer(mins, ":");
    try {
      int hour = Integer.parseInt(st.nextToken());
      int min = Integer.parseInt(st.nextToken());

      if (hour < 0 || hour >= 24) {
        printErrorMessageAndExit(HOUR_OUT_OF_BOUNDS);
      }
      if (min < 0 || min >= 60) {
        printErrorMessageAndExit(MINS_OUT_OF_BOUNDS);
      }
    }catch (NumberFormatException ex){
      printErrorMessageAndExit("Invalid Time: " + mins);
    }
    return true;
  }

  private static void printErrorMessageAndExit(String message){
      System.err.println(message);
      System.err.println(USAGE_MESSAGE);
      System.exit(1);
    }
  }