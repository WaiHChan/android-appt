package edu.pdx.cs410J.chanwai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class MakeNewAppointmentActivity extends AppCompatActivity {

    static final String YEAR_OUT_OF_BOUNDS = "Year out of bounds";
    static final String MONTH_OUT_OF_BOUNDS = "Month out of bounds" ;
    static final String DAY_OUT_OF_BOUNDS = "Day out of bounds" ;
    static final String HOUR_OUT_OF_BOUNDS = "Hour out of bounds";
    static final String MINS_OUT_OF_BOUNDS = "Minutes out of bounds";
    static final String INVALID_DATE = "Invalid Date: ";
    static final String INVALID_TIME = "Invalid Time: ";
    static final String BEGIN_DATE_AFTER_END_DATE = "Begin date occurs after End date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_appointment);

        Button addAppt = findViewById(R.id.add);
        addAppt.setOnClickListener(view -> addOperand());
    }

    private void addOperand() {
        EditText ownerId = findViewById(R.id.owner);
        EditText descriptionId = findViewById(R.id.description);
        EditText beginDateId = findViewById(R.id.beginDate);
        EditText beginTimeId = findViewById(R.id.beginTime);
        EditText beginAmId = findViewById(R.id.beginAM);
        EditText endDateId = findViewById(R.id.endDate);
        EditText endTimeId = findViewById(R.id.endTime);
        EditText endAmId = findViewById(R.id.endAM);
        Date begin_date = null;
        Date end_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

        String owner = ownerId.getText().toString();
        try {
            owner = ownerId.getText().toString();
            if(owner.isEmpty()){
                throw new NullPointerException("Cannot parse Owner name");
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse Owner name: " + owner;
            displayErrorMessage(message);
            return;
        }

        String description = descriptionId.getText().toString();
        try {
            description = descriptionId.getText().toString();
            if(description.isEmpty()){
                throw new NullPointerException("Cannot parse Description");
            }
        }catch (NullPointerException e) {
            String message = "Cannot parse Description: " + description;
            displayErrorMessage(message);
            return;
        }

        String beginDate = beginDateId.getText().toString();
        try {
            beginDate = isDateCorrect(beginDateId.getText().toString());
        }catch (NullPointerException e) {
            String message = "Cannot parse Begin Date: " + beginDate;
            displayErrorMessage(message);
            return;
        }

        String beginTime = beginTimeId.getText().toString();
        try {
            beginTime = isTimeCorrect(beginTimeId.getText().toString());
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
            endDate = isDateCorrect(endDateId.getText().toString());
        }catch (NullPointerException e) {
            String message = "Cannot parse End Date: " + endDate;
            displayErrorMessage(message);
            return;
        }

        String endTime = endTimeId.getText().toString();
        try {
            endTime = isTimeCorrect(endTimeId.getText().toString());
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
            }
        }catch (ParseException e){
            displayErrorMessage("Can not parse the date.");
        }
    }

    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
            }else if (month == -1){
                displayErrorMessage(INVALID_DATE + date);
            }else if (day == -1){
                displayErrorMessage(INVALID_DATE + date);
            }else if (year == -1){
                displayErrorMessage(INVALID_DATE + date);
            }

            if (year <= 0 || year >= 10000) {
                displayErrorMessage(YEAR_OUT_OF_BOUNDS + " " + date);
            }
            if (month <= 0 || month >= 13) {
                displayErrorMessage(MONTH_OUT_OF_BOUNDS + " " + date);
            }
            if (day <= 0 || day >= 32) {
                displayErrorMessage(DAY_OUT_OF_BOUNDS + " " + date);
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
            }else if (hour == -1){
                displayErrorMessage(INVALID_TIME + time);
            }else if (min == -1){
                displayErrorMessage(INVALID_TIME + time);
            }

            if (hour < 0 || hour >= 13) {
                displayErrorMessage(HOUR_OUT_OF_BOUNDS + " " + time);
            }
            if (min < 0 || min >= 60) {
                displayErrorMessage(MINS_OUT_OF_BOUNDS + " " + time);
            }
            return time;
        }catch (NumberFormatException ex){
            displayErrorMessage(INVALID_TIME + time);
            return null;
        }
    }
}