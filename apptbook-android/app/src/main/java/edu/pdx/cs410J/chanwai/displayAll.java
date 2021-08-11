package edu.pdx.cs410J.chanwai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pdx.cs410J.ParserException;

public class displayAll extends AppCompatActivity {

    private ArrayAdapter<String> fileName;
    public static final String FILENAME = "Name of The File";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);

        Button returnToMain = findViewById(R.id.backToMain);
        returnToMain.setOnClickListener(v -> finish());

        this.fileName = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        findFiles();
        ListView listOfAppts = findViewById(R.id.displayAppointment);
        listOfAppts.setAdapter(this.fileName);

        listOfAppts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getAdapter().getItem(i);
                String message = "Selected Owner " + ": " + item;
                toast(message);
                Intent intent = new Intent(displayAll.this, displayAllResult.class);
                intent.putExtra(FILENAME, item.toString());
                startActivity(intent);
            }
        });
    }

    private AppointmentBook loadApptsFromFile(String ownerFile) throws IOException, ParserException {

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
            AppointmentBook book = new AppointmentBook(ownerFile);
            return book;
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

    @NonNull
    private File getApptsFile(String owner) {
        File contextDirectory = getApplicationContext().getDataDir();
        return new File(contextDirectory, owner + ".txt");
    }

    private void findFiles(){
        File contextDirectory = getApplicationContext().getDataDir();
        if (contextDirectory.exists()) {
            for (File f : Objects.requireNonNull(contextDirectory.listFiles())) {
                String name = f.getName();
                if(name.endsWith(".txt")){
                    String temp = name.substring(0, name.lastIndexOf('.'));
                    this.fileName.add(temp);
                }
            }
        }
    }

    private void toast(String message) {
        Toast.makeText(displayAll.this, message, Toast.LENGTH_LONG).show();
    }
}