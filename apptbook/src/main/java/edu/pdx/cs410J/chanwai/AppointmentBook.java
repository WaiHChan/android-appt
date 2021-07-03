package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is represents a <code>AppointmentBook</code>
 */

public class AppointmentBook<T> extends AbstractAppointmentBook {

    /**
     * Creates a new <code>Appointment Book</code>
     *
     * @param ownerName
     *        The owner of the appointment book
     */
    protected String ownerName;
    List<Appointment> appointmentBook = new ArrayList<>();

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
        return this.appointmentBook;
    }

    /**
     * Adds a new <code>Appointment</code> to the <code>AppointmentBook</code>
     */
    @Override
    public void addAppointment(AbstractAppointment appointment) {
        appointmentBook.add((Appointment) appointment);
        ownerName = ((Appointment) appointment).owner;
    }
}