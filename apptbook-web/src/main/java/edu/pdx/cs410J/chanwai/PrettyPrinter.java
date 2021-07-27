package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;
import groovyjarjarantlr4.v4.runtime.atn.PredicateEvalInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represent a writer to a file in a nicer format
 */
public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    private final Writer writer;

    /**
     * Create a writer for loading data to file
     * @param writer a fileWriter
     */
    public PrettyPrinter(Writer writer){
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
        pw.println("");
        pw.println("Appointment Book:  " + book.getOwnerName() + "\tTotal Appointments: " + book.getAppointments().size());

        for (Appointment a : book.getAppointments()){
            long duration_In_Min = duration(a);
            long diff_Hour = hour(duration_In_Min);
            long diff_Min = min(duration_In_Min);
            long diff_Day = day(duration_In_Min);

            pw.println("\n");
            pw.println("Description: " + a.description + "\n");
            pw.println("From " + a.getBDateString() + " -> " + a.getEDateString() + "\n");
            pw.print("Duration: ");

            if (diff_Day > 0){
                pw.print(diff_Day + " days ");
            }
            if (diff_Hour > 0){
                pw.print(diff_Hour + " hours ");
            }
            if (diff_Min > 0){
                pw.print(diff_Min + " minutes\n");
            }

            pw.println("\n");
        }
        pw.flush();
    }

    /**
     * return the duration of an appointment
     * @param a an appointment
     * @return the duration of an appointment
     */
    public long duration(Appointment a){
        long diff = a.getEndTime().getTime() - a.getBeginTime().getTime();
        return diff / 60000;
    }

    /**
     * calculate the duration of an appointment to days
     * @param time the duration time in minutes
     * @return return the duration of an appointment to days
     */
    public long day(long time){
        return time / 60 / 24;
    }

    /**
     * calculate the duration of an appointment to hours
     * @param time the duration time in minutes
     * @return return the duration of an appointment to hours
     */
    public long hour(long time){
        return time / 60 % 24;
    }

    /**
     * calculate the duration of an appointment to minutes
     * @param time the duration time in minutes
     * @return return the duration of an appointment to minutes
     */
    public long min(long time) {
        return time % 60;
    }
}
