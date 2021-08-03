package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {
    private final String description;
    private final String beginTimeString;
    private final String endTimeString;

    public Appointment(String description, String beginTimeString, String endTimeString){
        this.description = description;
        this.beginTimeString = beginTimeString;
        this.endTimeString = endTimeString;
    }

    @Override
    public String getBeginTimeString() {
        return beginTimeString;
    }

    @Override
    public String getEndTimeString() {
        return endTimeString;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
