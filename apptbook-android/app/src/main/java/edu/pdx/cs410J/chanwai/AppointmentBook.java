package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppointmentBook extends AbstractAppointmentBook<Appointment> {

    protected String ownerName;
    List<Appointment> appointmentBook = new ArrayList<>();

    AppointmentBook(){
        this.ownerName = null;
    }

    AppointmentBook(String ownerName){
        this.ownerName = ownerName;
    }

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public Collection<Appointment> getAppointments() {
        Collections.sort(appointmentBook);
        return this.appointmentBook;
    }

    @Override
    public void addAppointment(Appointment appointment) {
        this.appointmentBook.add(appointment);
        this.ownerName = appointment.owner;
    }
}