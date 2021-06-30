package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  protected String owner;
  protected String description;
  protected String beginDate;
  protected String beginTime;
  protected String endDate;
  protected String endTime;

  @Override
  public String getBeginTimeString() {
    return this.beginDate + " " + this.beginTime;
  }

  @Override
  public String getEndTimeString() {

    return this.endDate + " " + this.endTime;
  }

  @Override
  public String getDescription() {
    System.out.println("DES " + this.description);
    System.out.println("\n");
    return this.description;
    /**return "This method is not implemented yet";*/
  }
}
