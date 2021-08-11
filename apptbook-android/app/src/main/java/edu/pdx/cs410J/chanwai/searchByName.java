package edu.pdx.cs410J.chanwai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pdx.cs410J.ParserException;

public class searchByName extends AppCompatActivity {

    public static final String APPOINTMENTBOOKINNAME = "The searched owner's appointment book";
    private AppointmentBook searchNameAappointmentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name);

        Button returnToMain = findViewById(R.id.return_to_main_search_Name);
        returnToMain.setOnClickListener(v -> finish());

        Button search = findViewById(R.id.searchButtonInName);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchForAppts();
                } catch (IOException | ParserException e) {
                    toast("While search appointment: " + e.getMessage());
                }
                Intent intent = new Intent(searchByName.this, searchByNameResult.class);
                intent.putExtra(APPOINTMENTBOOKINNAME, searchNameAappointmentBook);
                startActivity(intent);
            }
        });
    }

    private void searchForAppts() throws IOException, ParserException {
        EditText ownerId = findViewById(R.id.searchName);

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

        if (findFiles(owner)){ // book exists
            this.searchNameAappointmentBook = loadApptsFromFile(owner);
        }else {
            displayErrorMessage("File Doesn't Exists");
        }
    }

    private AppointmentBook loadApptsFromFile(String ownerFile) throws IOException{

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

        File apptsFile = getApptsFile(ownerFile);
        if (!apptsFile.exists()) {
            return new AppointmentBook(ownerFile);
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
                    displayErrorMessage("Cannot Parse the Date");
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

    private boolean findFiles(String ownerRequest){
        File contextDirectory = getApplicationContext().getDataDir();
        if (contextDirectory.exists()) {
            for (File f : Objects.requireNonNull(contextDirectory.listFiles())) {
                String name = f.getName();
                if(name.endsWith(".txt")){
                    String temp = name.substring(0, name.lastIndexOf('.'));
                    if (ownerRequest.equals(temp)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void toast(String message) {
        Toast.makeText(searchByName.this, message, Toast.LENGTH_LONG).show();
    }

    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}