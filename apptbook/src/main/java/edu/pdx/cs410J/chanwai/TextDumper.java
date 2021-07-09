package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    private String fileName;

    TextDumper(String name){
        this.fileName = name;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException {
        FileWriter writeTo = new FileWriter(fileName);
        ArrayList<Appointment> appointments = (ArrayList<Appointment>) book.getAppointments();

        writeTo.write(book + "\n");
        for(Appointment a : appointments)
            writeTo.write(a + "\n");
        writeTo.close();
        System.out.println("Successfully wrote to the file.");
    }
}
