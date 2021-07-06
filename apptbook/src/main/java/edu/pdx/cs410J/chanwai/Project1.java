package edu.pdx.cs410J.chanwai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.Month;
import java.util.StringTokenizer;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] owner description begin_date begin_time end_date end_time";
  static final String MISSING_COMMAND_LINE_ARGUMENTS = "Missing command line arguments";
  static final String TOO_MANY_COMMAND_LINE_ARGUMENTS = "Too many command line arguments";
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
    String owner;
    String description;
    String beginDate;
    String beginTime;
    String endDate;
    String endTime;

    if (args.length == 0) {
      printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
    } else if (args.length == 1) {
     if ("-print".equals(args[0])) {
     // if (isFirstArgPrint(args[0])){
        printErrorMessageAndExit(MISSING_COMMAND_LINE_ARGUMENTS);
      } else if ("-README".equals(args[0])) {
        System.out.println("This is a README file!");
        printReadme();
      } else {
        printErrorMessageAndExit(MISSING_DESCRIPTION);
      }
    } else if (args.length == 2) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_DESCRIPTION);
      } else if ("-README".equals(args[0])) {
        printReadme();
      } else {
        printErrorMessageAndExit(MISSING_BEGINE_DATE);
      }
    } else if (args.length == 3) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_BEGINE_DATE);
      } else if ("-README".equals(args[0])) {
        printReadme();
      } else {
        printErrorMessageAndExit(MISSING_BEGINE_TIME);
      }
    } else if (args.length == 4) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_BEGINE_TIME);
      } else if ("-README".equals(args[0])) {
        printReadme();
      } else {
        printErrorMessageAndExit(MISSING_END_DATE);
      }
    } else if (args.length == 5) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_END_DATE);
      } else if ("-README".equals(args[0])) {
        printReadme();
      } else {
        printErrorMessageAndExit(MISSING_END_TIME);
      }
    } else if (args.length == 6) {
      if ("-print".equals(args[0])) {
        printErrorMessageAndExit(MISSING_END_TIME);
      } else if ("-README".equals(args[0])) {
        printReadme();
      } else {
        if(isDateCorrect(args[2]) && isDateCorrect(args[4]) && isTimeCorrect(args[3]) && isTimeCorrect(args[5])) {
          owner = args[0];
          description = args[1];
          beginDate = args[2];
          beginTime = args[3];
          endDate = args[4];
          endTime = args[5];
          Appointment appointment = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
          AppointmentBook newBook = new AppointmentBook(owner);
          newBook.addAppointment(appointment);
        }
      }
    } else if (args.length == 7) {
      if ("-print".equals(args[0])) {
        if(isDateCorrect(args[3]) && isDateCorrect(args[5]) && isTimeCorrect(args[4]) && isTimeCorrect(args[6])) {
          owner = args[1];
          description = args[2];
          beginDate = args[3];
          beginTime = formatToTwoDecimal(args[4]);
          endDate = args[5];
          endTime = formatToTwoDecimal(args[6]);
          Appointment appointment = new Appointment(owner, description, beginDate, beginTime, endDate, endTime);
          AppointmentBook newBook = new AppointmentBook(owner);
          newBook.addAppointment(appointment);
          System.out.println(appointment);
        }
      }else if("-README".equals(args[0])) {
        printReadme();
      }else{
        printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
      }
    }else if(args.length >= 8){
      printErrorMessageAndExit(TOO_MANY_COMMAND_LINE_ARGUMENTS);
    }
    System.exit(0);
  }



  /**
   * Open the README.txt file
   */
  private static void printReadme() {
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
      System.err.println("File doesn't exist.");
      e.printStackTrace();
    }
    System.exit(1);
  }

  /**
   * Returns true or false if the date is correct
   * @param date a date needed to be check if it is within the range
   * @return Returns true or false if the date is valid or invalid
   */
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
      return false;
    }
    return true;
  }

  /**
   * Returns true or false if the time is correct
   * @param time the appointment time needed to be checked
   * @return True or False if the time is valid or invalid
   */
  private static boolean isTimeCorrect(String time){
    StringTokenizer st = new StringTokenizer(time, ":");
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
      printErrorMessageAndExit("Invalid Time: " + time);
      return false;
    }
    return true;
  }

  /**
   * Print error message and exit the program
   * @param message an error message that needed to be printed
   */
  private static void printErrorMessageAndExit(String message){
    System.err.println(message);
    System.err.println(USAGE_MESSAGE);
    System.exit(1);
  }

  /**
   * Convert a string to two decimal
   * @param arg an string representing the minutes of the appointment
   * @return a converted two decimal string
   */
  private static String formatToTwoDecimal(String arg){
    StringTokenizer time = new StringTokenizer(arg, ":");
    String hour = time.nextToken();
    int temp_mins = Integer.parseInt(time.nextToken());

    DecimalFormat formatter = new DecimalFormat("00");
    String mins = formatter.format(temp_mins);

    return hour + ":" + mins;
  }
}