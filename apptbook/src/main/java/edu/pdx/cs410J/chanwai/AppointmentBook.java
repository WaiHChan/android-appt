package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppointmentBook<T> extends AbstractAppointmentBook {

    protected String ownerName;
    List<Appointment> appointmentList = new ArrayList<>();

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public Collection<T> getAppointments() {
        return null;
    }

    @Override
    public void addAppointment(AbstractAppointment appointment) {
        appointmentList.add((Appointment) appointment);
        System.out.println(appointmentList);
    }
}