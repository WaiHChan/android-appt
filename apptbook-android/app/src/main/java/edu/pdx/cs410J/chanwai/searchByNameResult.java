package edu.pdx.cs410J.chanwai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class searchByNameResult extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name_result);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = findViewById(R.id.displayAppointmentByName);

        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        AppointmentBook searchedBook = (AppointmentBook) intent.getSerializableExtra(searchByName.APPOINTMENTBOOKINNAME);

        ArrayList<Appointment> ApptList = new ArrayList<>(searchedBook.getAppointments());

        AppointmentListAdapter adapter = new AppointmentListAdapter(this, R.layout.adapter_view_layout, ApptList);
        mListView.setAdapter(adapter);
    }
}