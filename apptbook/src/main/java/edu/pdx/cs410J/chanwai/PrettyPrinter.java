package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    @Override
    public void dump(AppointmentBook book) throws IOException {

        //print ==== line

        ArrayList<Appointment> appointments = (ArrayList<Appointment>) book.getAppointments();
        for (Appointment a : appointments){
            Date time_different = duration(a);
        }

    }

    public Date duration(Appointment a){

    }

}
