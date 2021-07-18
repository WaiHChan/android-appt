package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    private final Writer writer;

    /**
     * Create a writer for loading data to file
     * @param writer a fileWriter
     */
    public PrettyPrinter(Writer writer){
        this.writer = writer;
    }

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

    public long duration(Appointment a){
        long diff = a.getEndTime().getTime() - a.getBeginTime().getTime();
        return diff / 60000;
    }

    public long day(long time){
        return time / 60 / 24;
    }

    public long hour(long time){
        return time / 60 % 24;
    }

    public long min(long time) {
        return time % 60;
    }
}
