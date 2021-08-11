package edu.pdx.cs410J.chanwai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pdx.cs410J.ParserException;

public class displayAllResult extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_result);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = findViewById(R.id.displayAppointment);

        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String ownerFromFile = intent.getStringExtra(displayAll.FILENAME);

        AppointmentBook book = null;
        try {
            book = loadApptsFromFile(ownerFromFile);
        } catch (IOException | ParserException e) {
            toast("While loading from file: " + e.getMessage());
        }

        assert book != null;
        ArrayList<Appointment> ApptList = new ArrayList<>(book.getAppointments());

        AppointmentListAdapter adapter = new AppointmentListAdapter(this, R.layout.adapter_view_layout, ApptList);
        mListView.setAdapter(adapter);
    }

    private void toast(String message) {
        Toast.makeText(displayAllResult.this, message, Toast.LENGTH_LONG).show();
    }

    private AppointmentBook loadApptsFromFile(String fileOwner) throws IOException, ParserException {

        String owner = null;
        String description = null;
        String beginDate = null;
        String beginTime = null;
        String beginAmPm = null;
        String endDate = null;
        String endTime = null;
        String endAmPm = null;
        Date begin_date = null;
        Date end_date = null;
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        File apptsFile = getApptsFile(fileOwner);
        if (!apptsFile.exists()) {
            return new AppointmentBook(fileOwner);
        }

        try (
                BufferedReader br = new BufferedReader(new FileReader(apptsFile))
        ) {
            AppointmentBook book = new AppointmentBook();
            String regex = "\"([^\"]*)\"|(\\S+)";
            String line = br.readLine();
            while(line != null) {
                Matcher m = Pattern.compile(regex).matcher(line);
                if (m.find()) {
                    if (m.group(1) != null) {
                        owner = "\"" + m.group(1) + "\"";
                    } else {
                        owner = m.group(2);
                    }
                }
                if (m.find()){
                    if (m.group(1) != null) {
                        description = "\"" + m.group(1) + "\"";
                    } else {
                        description = m.group(2);
                    }
                }
                if (m.find()){
                    beginDate = m.group(2);
                }
                if (m.find()){
                    beginTime = m.group(2);
                }
                if (m.find()){
                    beginAmPm = m.group(2);
                }
                if (m.find()){
                    endDate = m.group(2);
                }
                if (m.find()){
                    endTime = m.group(2);
                }
                if (m.find()){
                    endAmPm = m.group(2);
                }
                try {
                    begin_date = df.parse(beginDate + " " + beginTime + " " + beginAmPm);
                    end_date = df.parse(endDate + " " + endTime + " " + endAmPm);
                }catch (ParseException e){
                    toast("While reading from file: " + e.getMessage());
                }
                book.addAppointment(new Appointment(owner, description, begin_date, end_date));
                line = br.readLine();
            }
            return book;
        }
    }

    @NonNull
    private File getApptsFile(String owner) {
        File contextDirectory = getApplicationContext().getDataDir();
        return new File(contextDirectory, owner + ".txt");
    }
}