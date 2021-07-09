package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * This class is represents a <code>Appointment</code>
 */

public class Appointment extends AbstractAppointment {

  protected String owner;
  protected String description;
  protected String beginDate;
  protected String beginTime;
  protected String endDate;
  protected String endTime;

  /**
   * Creates a new <code>Appointment</code>
   *
   * @param owner
   *        The owner's name of the appointment
   * @param description
   *        A description about the appointment
   * @param beginDate
   *        The begin date of the appointment
   * @param beginTime
   *        The begin time of the appointment
   * @param endDate
   *        The end date of the appointment
   * @param endTime
   *        The end time of the appointment
   */
  public Appointment(String owner, String description, String beginDate, String beginTime, String endDate,String endTime){
    this.owner = owner;
    this.description = description;
    this.beginDate = beginDate;
    this.beginTime = beginTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  /**
   * Returns a <code>String</code> that describes the
   * <code>Appointment Begin date and time</code>
   */
  @Override
  public String getBeginTimeString() {
    return this.beginDate + " " + this.beginTime;
  }

  /**
   * Returns a <code>String</code> that describes the
   * <code>Appointment end date and time</code>
   */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }

  /**
   * Returns a <code>String</code> that describes the
   * <code>Appointment's Description</code>
   */
  @Override
  public String getDescription() {
    return this.description;
  }
}
