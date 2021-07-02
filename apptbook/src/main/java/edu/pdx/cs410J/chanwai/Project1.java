package edu.pdx.cs410J.chanwai;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {

    if (args.length < 6) {
      System.err.println("Missing command line arguments");
      for (String arg : args) {
        System.out.println(arg);
      }
    } else if (args.length > 7) {
      System.err.println("Too many command line arguments");
      for (String arg : args) {
        System.out.println(arg);
      }
    }

      System.out.println(args.length);

      Appointment appointment = new Appointment();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

      if ("-README".equals(args[0])) {
        try (
          InputStream readme = Project1.class.getResourceAsStream("README.txt")
        ) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String txt = reader.readLine();
          System.out.println(txt);
        } catch (IOException e) {
          System.out.println("Can not find the file.");
          e.printStackTrace();
        }
        appointment.owner = args[1];
        appointment.description = args[2];
        appointment.beginDate = args[3];
        appointment.beginTime = args[4];
        appointment.endDate = args[5];
        appointment.endTime = args[6];
        AppointmentBook appointmentBook = new AppointmentBook();
        appointmentBook.addAppointment(appointment);

      } else if ("-print".equals(args[0])) {
        appointment.owner = args[1];
        appointment.description = args[2];
        appointment.beginDate = args[3];
        appointment.beginTime = args[4];
        appointment.endDate = args[5];
        appointment.endTime = args[6];
        System.out.println(appointment);
      } else {
        appointment.owner = args[0];
        appointment.description = args[1];
        appointment.beginDate = args[2];
        appointment.beginTime = args[3];
        appointment.endDate = args[4];
        appointment.endTime = args[5];
      }
      System.exit(1);
    }
  }