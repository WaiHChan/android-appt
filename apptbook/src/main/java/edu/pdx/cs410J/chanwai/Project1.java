package edu.pdx.cs410J.chanwai;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {
    Appointment appointment = new Appointment();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }



    if("-print".equals(args[2])){
      appointment.toString();
    }else if("-README".equals(args[2])){

      System.exit(1);
    }else{
      appointment.owner = args[2];
      appointment.description = args[3];
      appointment.beginDate = args[4];
      appointment.beginTime = args[5];
      appointment.endDate = args[6];
      appointment.endTime = args[7];
    }



    System.exit(1);
  }

}