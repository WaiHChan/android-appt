package edu.pdx.cs410J.chanwai;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.chanwai.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final int GET_APPOINTMENT_FROM_ACTIVITY = 42;
    private ArrayAdapter<Appointment> appts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Appointment appointment = new Appointment("Jim","Temp", "08/02/2021 5:00 PM", "08/02/2021 6:00 PM");
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
//        });

        Button makeAppt = findViewById(R.id.make_appointment);
        makeAppt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MakeNewAppointmentActivity.class);
            startActivityForResult(intent, GET_APPOINTMENT_FROM_ACTIVITY);
        });

        this.appts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        try {
            loadApptsFromFile();
        } catch (IOException | ParserException e) {
            toast("While reading file: " + e.getMessage());
        }

        ListView listOfAppts = findViewById(R.id.appts);
        listOfAppts.setAdapter(this.appts);
    }

    private void toast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == GET_APPOINTMENT_FROM_ACTIVITY && data != null){
            Appointment appointment = (Appointment) data.getSerializableExtra(MakeNewAppointmentActivity.APPOINTMENT);
            Toast.makeText(this, "Received Appointment: " + appointment, Toast.LENGTH_LONG).show();
            this.appts.add(appointment);
            try {
                writeApptsToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadApptsFromFile() throws IOException, ParserException {

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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        File apptsFile = getApptsFile();
        if (!apptsFile.exists()) {
            return;
        }

        try (
                BufferedReader br = new BufferedReader(new FileReader(apptsFile))
        ) {
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
                    throw new ParserException("While reading text", e);
                }
                this.appts.add(new Appointment(owner, description, begin_date, end_date));
                line = br.readLine();
            }
        }
    }

    private void writeApptsToFile() throws IOException {
        File apptsFile = getApptsFile();
        try (
            PrintWriter pw = new PrintWriter(new FileWriter(apptsFile))
        ){
            for (int i = 0; i < this.appts.getCount(); i++) {
                Appointment appt = this.appts.getItem(i);
                pw.println(appt.toString());
            }
            pw.flush();
        }
    }

    @NonNull
    private File getApptsFile() {
        File contextDirectory = getApplicationContext().getDataDir();
        return new File(contextDirectory, "appts.txt");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}