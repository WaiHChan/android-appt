package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is represents a <code>Appointment</code>
 */

public class Appointment extends AbstractAppointment implements Comparable<Appointment> {

  protected String owner;
  protected String description;
  protected Date beginDate;
  protected Date endDate;
  DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

  public Appointment(){
    this.owner = "";
    this.description = "";
    this.beginDate = new Date(0);
    this.endDate = new Date(0);
  }
  public Appointment(String description){
    this.owner = owner;
    this.description = description;
    this.beginDate = beginDate;
    this.endDate = endDate;
  }
  /**
   * Creates a new <code>Appointment</code>
   *
   * @param owner
   *        The owner's name of the appointment
   * @param description
   *        A description about the appointment
   * @param beginDate
   *        The begin date of the appointment
   * @param endDate
   *        The end date of the appointment
   */
  public Appointment(String owner, String description, Date beginDate, Date endDate){
    this.owner = owner;
    this.description = description;
    this.beginDate = beginDate;
    this.endDate = endDate;
  }

  /**
   * Returns a <code>String</code> that describes the
   * <code>Appointment Begin date and time</code>
   */
  @Override
  public String getBeginTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(beginDate);
  }

  /**
   * Returns a <code>String</code> that describes the
   * <code>Appointment end date and time</code>
   */
  @Override
  public String getEndTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(endDate);
  }

  public String getBDateString() {
    return df.format(beginDate);
  }

  public String getEDateString() {
    return df.format(endDate);
  }

  @Override
  public Date getBeginTime(){
    return beginDate;
  }

  @Override
  public Date getEndTime(){
    return endDate;
  }

  /**
   * Returns a <code>String</code> that describes the
   * <code>Appointment's Description</code>
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Return 1,0,-1 according to the string compare
   * @param o appointment oject
   * @return return 1 if the begin time is bigger than the begin time of the object passed in.
   */
  @Override
  public int compareTo(Appointment o) {
    if (getBeginTime().compareTo(o.getBeginTime()) < 0){
      return -1;
    } else if (getBeginTime().compareTo(o.getBeginTime()) > 0){
      return 1;
    } else { // begin = end
      if (getEndTime().compareTo(o.getEndTime()) < 0){
        return -1;
      }else if (getEndTime().compareTo(o.getEndTime()) > 0){
        return 1;
      }else { // sort by description
        if (getDescription().compareTo(o.description) < 0){
          return -1;
        }else if (getDescription().compareTo(o.description) > 0){
          return 1;
        }else {
          return 0;
        }
      }
    }
  }
}
