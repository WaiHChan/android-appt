package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.Collection;

public class AppointmentBook<T> extends AbstractAppointmentBook {

    protected String ownerName;

    @Override
    public String getOwnerName() {
        //return ownerName;
        return null;
    }

    @Override
    public Collection<T> getAppointments() {
        return null;
    }

    @Override
    public void addAppointment(AbstractAppointment abstractAppointment) {

    }
}