package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;

/**
 * The class represent a writer to a file
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    private final Writer writer;

    public TextDumper(Writer writer){
        this.writer = writer;
    }

    /**
     * Pass in an appointment book that will be loaded to a file
     * @param book An appointment book that will be load to a file
     * @throws IOException Raise an exception if the appointment book cannot be loaded
     */

    @Override
    public void dump(AppointmentBook book) throws IOException {

        PrintWriter pw = new PrintWriter(this.writer);
        for (Appointment a : book.getAppointments()){
            pw.println(a.owner + " " + a.description + " " + a.getBDateString() + " " + a.getEDateString());
        }
    }

    public void dumpByDate(AppointmentBook book, Date start, Date end) throws IOException {
        PrintWriter pw = new PrintWriter(this.writer);
        for (Appointment a : book.getAppointments()) {
            if ((a.beginDate.after(start) || a.beginDate.equals(start)) && (a.endDate.before(end) || a.endDate.equals(end))) {
                pw.println(a.owner + " " + a.description + " " + a.getBDateString() + " " + a.getEDateString());
            }
        }
    }

}
