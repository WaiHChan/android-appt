package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * The class represent a writer to a file
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

//    private final Writer writer;
      private final PrintWriter write;
//
//    /**
//     * Create a writer for loading data to file
//     * @param writer a fileWriter
//     */
//    public TextDumper(Writer writer){
//        this.writer = writer;
//    }

    public TextDumper(PrintWriter write){
        this.write = write;
    }

    /**
     * Pass in an appointment book that will be loaded to a file
     * @param book An appointment book that will be load to a file
     * @throws IOException Raise an exception if the appointment book cannot be loaded
     */

    @Override
    public void dump(AppointmentBook book) throws IOException {

        write.println(book.getOwnerName());
        for (Appointment appointment : book.getAppointments()){
            write.println("This is dumper: " + appointment.getDescription());
        }
        //        ArrayList<Appointment> appointments = (ArrayList<Appointment>) book.getAppointments();
//
//        for(Appointment a : appointments) {
//            writer.write(a.owner + " ");
//            writer.write(a.description + " ");
//            writer.write(a.getBDateString() + " ");
//            writer.write(a.getEDateString() + "\n");
//        }
//        writer.close();
//
//        System.out.println("Successfully read/wrote to the text file.");
    }
}
