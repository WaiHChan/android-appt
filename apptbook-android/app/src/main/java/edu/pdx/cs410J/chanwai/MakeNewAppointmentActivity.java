package edu.pdx.cs410J.chanwai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pdx.cs410J.ParserException;

public class MakeNewAppointmentActivity extends AppCompatActivity {

    static final String YEAR_OUT_OF_BOUNDS = "Year out of bounds";
    static final String MONTH_OUT_OF_BOUNDS = "Month out of bounds" ;
    static final String DAY_OUT_OF_BOUNDS = "Day out of bounds" ;
    static final String HOUR_OUT_OF_BOUNDS = "Hour out of bounds";
    static final String MINS_OUT_OF_BOUNDS = "Minutes out of bounds";
    static final String INVALID_DATE = "Invalid Date: ";
    static final String INVALID_TIME = "Invalid Time: ";
    static final String BEGIN_DATE_AFTER_END_DATE = "Begin date occurs after End date";
    private static final String INVALID_MONTH = "Invalid Month: ";
    private static final String INVALID_YEAR = "Invalid Year: ";
    private static final String INVALID_DAY = "Invalid Day: ";
    private static final String INVALID_HOUR = "Invalid Hour: ";
    private static final String INVALID_MINS = "Invalid Minutes: ";

    private Appointment newAppointment;
    public static final String APPOINTMENT = "Appointment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_appointment);

        Button addAppt = findViewById(R.id.add);
        addAppt.setOnClickListener(view -> {
            try {
                addOperand();
            } catch (IOException | ParserException e) {
                displayErrorMessage("While creating appointment: " + e.getMessage());
            }
        });

        Button returnToMain = findViewById(R.id.return_to_main);
        returnToMain.setOnClickListener(v -> sendDataBackToMain());
    }

    private void sendDataBackToMain() {
        Intent intent = new Intent();
        intent.putExtra(APPOINTMENT, newAppointment);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void addOperand() throws IOException, ParserException {
        EditText ownerId = findViewById(R.id.owner);
        EditText descriptionId = findViewById(R.id.description);
        EditText beginDateId = findViewById(R.id.beginDate);
        EditText beginTimeId = findViewById(R.id.beginTime);
        EditText beginAmId = findViewById(R.id.beginAM);
        EditText endDateId = findViewById(R.id.endDate);
        EditText endTimeId = findViewById(R.id.endTime);
        EditText endAmId = findViewById(R.id.endAM);
        Date begin_date;
        Date end_date;
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        String owner = ownerId.getText().toString();
        try {
            String arg = ownerId.getText().toString();
            if (arg.contains(" ")){
                owner = "\"" + arg + "\"";
            }else{
                owner = arg;
            }
            if(owner.isEmpty()){
                throw new NullPointerException("Cannot parse Owner name.");
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse Owner name: " + owner;
            displayErrorMessage(message);
            return;
        }

        String description = descriptionId.getText().toString();
        try {
            String arg = descriptionId.getText().toString();
            if (arg.contains(" ")){
                description = "\"" + arg + "\"";
            }else{
                description = arg;
            }
            if(description.isEmpty()){
                displayErrorMessage("Cannot parse Description.");
                return;
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse Description: " + description;
            displayErrorMessage(message);
            return;
        }

        String beginDate = beginDateId.getText().toString();
        try {
            String arg = isDateCorrect(beginDateId.getText().toString());
            if (arg == null){
                return;
            }else{
                beginDate = arg;
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse Begin Date: " + beginDate;
            displayErrorMessage(message);
            return;
        }

        String beginTime = beginTimeId.getText().toString();
        try {
            String arg = isTimeCorrect(beginTimeId.getText().toString());
            if (arg == null){
                return;
            }else{
                beginTime = arg;
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse Begin Time: " + beginTime;
            displayErrorMessage(message);
            return;
        }

        String beginAM = beginAmId.getText().toString();
        try {
            beginAM = beginAmId.getText().toString();
        }catch (NullPointerException e) {
            String message = "Cannot parse Begin AM/PM: " + beginAM;
            displayErrorMessage(message);
            return;
        }

        String endDate = endDateId.getText().toString();
        try {
            String arg = isDateCorrect(endDateId.getText().toString());
            if (arg == null){
                return;
            }else{
                endDate = arg;
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse End Date: " + endDate;
            displayErrorMessage(message);
            return;
        }

        String endTime = endTimeId.getText().toString();
        try {
            String arg = isTimeCorrect(endTimeId.getText().toString());
            if (arg == null){
                return;
            }else{
                endTime = arg;
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse End Time: " + endTime;
            displayErrorMessage(message);
            return;
        }

        String endAM = endAmId.getText().toString();
        try {
            endAM = endAmId.getText().toString();
        }catch (NullPointerException e) {
            String message = "Cannot parse End AM/PM: " + endAM;
            displayErrorMessage(message);
            return;
        }

        try {
            begin_date = df.parse(beginDate + " " + beginTime + " " + beginAM);
            end_date = df.parse(endDate + " " + endTime + " " + endAM);
            assert begin_date != null;
            if (begin_date.compareTo(end_date) > 0){
                displayErrorMessage(BEGIN_DATE_AFTER_END_DATE);
                return;
            }
        }catch (ParseException e){
            displayErrorMessage("Can not parse the date.");
            return;
        }

        TextView appt = findViewById(R.id.print);

        Appointment newAppt = new Appointment(owner, description, begin_date, end_date);
        this.newAppointment = newAppt;

        AppointmentBook book;
        if(findApptFromDisk(newAppt.owner)){
            //Appointment book contains data from previous appointments
            book = loadApptsFromFile(newAppt.owner);
        }else {
            // file not found, create one
            book = new AppointmentBook(newAppt.owner);
        }
        book.addAppointment(newAppt);
        appt.setText(newAppt.toString()); // -print option

        try {
            writeApptsToFile(book);
        } catch (IOException e) {
            toast("While writing to file: " + e.getMessage());
        }
    }

    private void toast(String message) {
        Toast.makeText(MakeNewAppointmentActivity.this, message, Toast.LENGTH_LONG).show();
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
        Date begin_date;
        Date end_date;
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
                    throw new ParserException("While reading text", e);
                }
                book.addAppointment(new Appointment(owner, description, begin_date, end_date));
                line = br.readLine();
            }
            return book;
        }
    }

    private boolean findApptFromDisk(String owner) {
        File apptsFile = getApptsFile(owner);
        return apptsFile.exists();
    }

    private void writeApptsToFile(AppointmentBook book) throws IOException {
        File apptsFile = getApptsFile(book.ownerName);

        try (
            PrintWriter pw = new PrintWriter(new FileWriter(apptsFile))
        ){
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(book);
        }
    }

    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @NonNull
    private File getApptsFile(String owner) {
        File contextDirectory = getApplicationContext().getDataDir();
        return new File(contextDirectory, owner + ".txt");
    }

    private String isDateCorrect(String date) {
        try {
            StringTokenizer stHour = new StringTokenizer(date, "/");
            int month = -1;
            int day = -1;
            int year = -1;
            int trash = 0;

            while (stHour.hasMoreTokens()) {
                if (month == -1){
                    month = Integer.parseInt(stHour.nextToken());
                }else if (day == -1){
                    day = Integer.parseInt(stHour.nextToken());
                }else if (year == -1){
                    year = Integer.parseInt(stHour.nextToken());
                }else{
                    trash = Integer.parseInt(stHour.nextToken());
                }
            }

            if (trash != 0){
                displayErrorMessage(INVALID_DATE + date);
                return null;
            }else if (month == -1){
                displayErrorMessage(INVALID_MONTH + date);
                return null;
            }else if (day == -1){
                displayErrorMessage(INVALID_DAY + date);
                return null;
            }else if (year == -1){
                displayErrorMessage(INVALID_YEAR + date);
                return null;
            }

            if (year <= 0 || year >= 10000) {
                displayErrorMessage(YEAR_OUT_OF_BOUNDS + " " + date);
                return null;
            }
            if (month <= 0 || month >= 13) {
                displayErrorMessage(MONTH_OUT_OF_BOUNDS + " " + date);
                return null;
            }
            if (day <= 0 || day >= 32) {
                displayErrorMessage(DAY_OUT_OF_BOUNDS + " " + date);
                return null;
            }
            return date;
        } catch (NumberFormatException ex){
            displayErrorMessage("Invalid Date: " + date);
            return null;
        }
    }

    private String isTimeCorrect(String time){
        try {
            StringTokenizer st = new StringTokenizer(time, ":");
            int hour = -1;
            int min = -1;
            int trash = 0;

            while (st.hasMoreTokens()) {
                if (hour == -1){
                    hour = Integer.parseInt(st.nextToken());
                }else if (min == -1){
                    min = Integer.parseInt(st.nextToken());
                }else{
                    trash = Integer.parseInt(st.nextToken());
                }
            }

            if (trash != 0){
                displayErrorMessage(INVALID_TIME + time);
                return null;
            }else if (hour == -1){
                displayErrorMessage(INVALID_HOUR + time);
                return null;
            }else if (min == -1){
                displayErrorMessage(INVALID_MINS + time);
                return null;
            }

            if (hour < 0 || hour >= 13) {
                displayErrorMessage(HOUR_OUT_OF_BOUNDS + " " + time);
                return null;
            }
            if (min < 0 || min >= 60) {
                displayErrorMessage(MINS_OUT_OF_BOUNDS + " " + time);
                return null;
            }
            return time;
        }catch (NumberFormatException ex){
            displayErrorMessage(INVALID_TIME + time);
            return null;
        }
    }
}