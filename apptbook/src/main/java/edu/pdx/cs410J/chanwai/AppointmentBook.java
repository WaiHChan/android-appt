package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This class is represents a <code>AppointmentBook</code>
 */

public class AppointmentBook extends AbstractAppointmentBook<Appointment> {

    /**
     * The owner of the appointment book
     */
    protected String ownerName;
    List<Appointment> appointmentBook = new ArrayList<>();

    AppointmentBook(){
        this.ownerName = null;
    }

    AppointmentBook(String ownerName){
        this.ownerName = ownerName;
    }
    /**
     * Returns a <code>String</code> that describes the
     * <code>Owner of the appointment book</code>
     */
    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * Returns a <code>Collection of appointments</code> that describes this
     * <code>Appointment Book</code>
     */
    @Override
    public Collection<Appointment> getAppointments() {
        Collections.sort(appointmentBook);
        return this.appointmentBook;
    }

    /**
     * Adds a new <code>Appointment</code> to the <code>AppointmentBook</code>
     */
    @Override
    public void addAppointment(Appointment appointment) {
        appointmentBook.add(appointment);
        this.ownerName = appointment.owner;
    }
}