package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

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

        ArrayList<Appointment> appointments = (ArrayList<Appointment>) book.getAppointments();

        writer.write("Appointment Book: " + book.ownerName + "\tTotal Appointments: " + book.getAppointments().size() + "\n");
        for (Appointment a : appointments){
            long duration_In_Min = duration(a);
            long diff_Hour = hour(duration_In_Min);
            long diff_Min = min(duration_In_Min);
            long diff_Day = day(duration_In_Min);

            writer.write("\n");
            writer.write(a.description + "\n");
            writer.write("From " + a.getBDateString() + " -> " + a.getEDateString() + "\n");
            writer.write("Duration: ");

            if (diff_Day > 0){
                writer.write(diff_Day + " days ");
            }
            if (diff_Hour > 0){
                writer.write(diff_Hour + " hours ");
            }
            if (diff_Min > 0){
                writer.write(diff_Min + " minutes");
            }

            writer.write("\n");
        }
        writer.close();
        System.out.println("Successfully pretty print to the file.");
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
