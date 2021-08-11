package edu.pdx.cs410J.chanwai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class search extends AppCompatActivity {

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

    private Map<String, AppointmentBook> books = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.books = (Map<String, AppointmentBook>) (HashMap<String, AppointmentBook>) getIntent().getSerializableExtra(MakeNewAppointmentActivity.ALLBOOK);

        Button returnToMain = findViewById(R.id.return_to_main_search);
        returnToMain.setOnClickListener(v -> finish());

        Button search = findViewById(R.id.search_button);
        search.setOnClickListener(view -> {
            try {
                searchForAppts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void searchForAppts() throws IOException {
        EditText ownerId = findViewById(R.id.ownerForSearch);
        EditText beginDateId = findViewById(R.id.beginDateForSearch);
        EditText beginTimeId = findViewById(R.id.beginTimeForSearch);
        EditText beginAmId = findViewById(R.id.beginAMForSearch);
        EditText endDateId = findViewById(R.id.endDateForSearch);
        EditText endTimeId = findViewById(R.id.endTimeForSearch);
        EditText endAmId = findViewById(R.id.endAMForSearch);
        Date begin_date = null;
        Date end_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

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

        String beginDateString = beginDate + " " + beginTime + " " + beginAM;
        String endDateString = endDate + " " + endTime + " " + endAM;
//        AppointmentBook book = getAppointmentsBasedOnDate(owner, beginDateString, endDateString);

//        if (book == null) {
//            displayErrorMessage("No appointments found");
//        }else {
//            Toast.makeText(search.this, book.ownerName, Toast.LENGTH_LONG).show();
//            File apptsFile = getApptsFile();
//            try (
//                    PrintWriter pw = new PrintWriter(new FileWriter(apptsFile));
//            ) {
//                TextDumper dumper = new TextDumper(pw);
//                dumper.dumpByDate(book, begin_date, end_date);
//            }
 //       }
    }

//    private AppointmentBook getAppointmentsBasedOnDate(String owner, String beginDateString, String endDateString) {
//
//        TextParser parser = new TextParser(new StringReader(text));
//        return parser.parse();
//    }

    @NonNull
    private File getApptsFile() {
        File contextDirectory = getApplicationContext().getDataDir();
        return new File(contextDirectory, "appts.txt");
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

    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}