package edu.pdx.cs410J.chanwai;

import java.io.IOException;
import java.io.PrintWriter;

import edu.pdx.cs410J.AppointmentBookDumper;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    private final PrintWriter writer;

    public TextDumper(PrintWriter writer){
        this.writer = writer;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException {

        PrintWriter pw = this.writer;
        for (Appointment a : book.getAppointments()){
            pw.println(a.owner + " " + a.description + " " + a.getBDateString() + " " + a.getEDateString());
        }
        pw.flush();
    }
}
