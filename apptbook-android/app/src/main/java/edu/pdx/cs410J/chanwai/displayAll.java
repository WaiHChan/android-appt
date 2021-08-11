package edu.pdx.cs410J.chanwai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.channels.InterruptedByTimeoutException;

public class displayAll extends AppCompatActivity {

    private ArrayAdapter<Appointment> appts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);

        Intent intent = getIntent();
        Appointment appointment = (Appointment) intent.getSerializableExtra(MakeNewAppointmentActivity.APPOINTMENT);
        toast("Got appointment: " + appointment);
//        this.appts.add(appointment);
//        this.appts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        ListView listOfAppts = findViewById(R.id.displayAppointment);
//        listOfAppts.setAdapter(this.appts);
    }
    private void toast(String message) {
        Toast.makeText(displayAll.this, message, Toast.LENGTH_LONG).show();
    }
}