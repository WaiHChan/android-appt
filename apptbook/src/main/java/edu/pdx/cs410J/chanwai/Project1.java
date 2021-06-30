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
        File myfile = new File("edu/pdx/cs410J/chanwai/README.txt");
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

    }
    /**
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
     */

    System.out.print(args[0]);
    System.out.print("\n");

    System.exit(1);
  }

}