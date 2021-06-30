package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;

public class AppointmentBook extends AbstractAppointmentBook {

    protected String ownerName;

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public Collection getAppointments() {
        return null;
    }

    @Override
    public void addAppointment(AbstractAppointment abstractAppointment) {

    }
}