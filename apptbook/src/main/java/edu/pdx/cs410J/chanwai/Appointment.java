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
    throw new UnsupportedOperationException("This method is not implemented yet");
    //return this.beginDate + " " + this.beginTime;
  }

  @Override
  public String getEndTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDescription() {
    //return this.description;
    return "This method is not implemented yet";
  }
}
