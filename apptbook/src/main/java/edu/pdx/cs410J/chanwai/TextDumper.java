package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    private final Writer writer;

    /**
     * Create a writer for loading data to file
     * @param writer a fileWriter
     */
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

        ArrayList<Appointment> appointments = (ArrayList<Appointment>) book.getAppointments();

        for(Appointment a : appointments) {
            writer.write(a.owner + " ");
            writer.write(a.description + " ");
            writer.write(a.beginDate + " ");
            writer.write(a.beginTime + " ");
            writer.write(a.endDate + " ");
            writer.write(a.endTime + "\n");
        }
        writer.close();

        System.out.println("Successfully wrote to the file.");
    }
}
