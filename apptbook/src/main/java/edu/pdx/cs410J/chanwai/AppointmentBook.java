package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppointmentBook<T> extends AbstractAppointmentBook {

    protected String ownerName;
    List<Appointment> appointmentBook = new ArrayList<>();

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public Collection<Appointment> getAppointments() {
        return this.appointmentBook;
    }

    @Override
    public void addAppointment(AbstractAppointment appointment) {
        appointmentBook.add((Appointment) appointment);
        ownerName = ((Appointment) appointment).owner;
        System.out.println(appointmentBook.toString());
        System.out.println("\n");
        System.out.println(ownerName);
    }
}