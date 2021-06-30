package edu.pdx.cs410J.chanwai;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {
    Appointment appointment = new Appointment();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if("-README".equals(args[0])) {
      try {
        File myfile = new File("src/main/resources/edu/pdx/cs410J/chanwai/README.txt");
        Scanner myScanner = new Scanner(myfile);
        while (myScanner.hasNextLine()) {
          String txt = myScanner.nextLine();
          System.out.println(txt);
        }
        myScanner.close();
      } catch (FileNotFoundException exception) {
        System.out.println("Can not find the file.");
        exception.printStackTrace();
      }
      appointment.owner = args[1];
      appointment.description = args[2];
      appointment.beginDate = args[3];
      appointment.beginTime = args[4];
      appointment.endDate = args[5];
      appointment.endTime = args[6];

    }else if("-print".equals(args[0])){
      appointment.toString();
      appointment.owner = args[1];
      appointment.description = args[2];
      appointment.beginDate = args[3];
      appointment.beginTime = args[4];
      appointment.endDate = args[5];
      appointment.endTime = args[6];
    }else{
      appointment.owner = args[0];
      appointment.description = args[1];
      appointment.beginDate = args[2];
      appointment.beginTime = args[3];
      appointment.endDate = args[4];
      appointment.endTime = args[5];
    }
    /**
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
     */

    System.out.print("args 2" + args[2]);
    System.out.print("\n");
    System.out.print("owner " + appointment.owner);
    System.out.print("\n");
    System.out.print("args 5" + args[5]);
    System.out.print("\n");

    System.exit(1);
  }
}