package edu.pdx.cs410J.chanwai;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class searchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        AppointmentBook searchedBook = (AppointmentBook) intent.getSerializableExtra(search.APPOINTMENTBOOKINRANGE);
        ArrayAdapter<Appointment> appt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Appointment a : searchedBook.getAppointments()){
            appt.add(a);
        }
        ListView listOfAppointment = findViewById(R.id.displayAppointmentInSearch);
        listOfAppointment.setAdapter(appt);
    }
}