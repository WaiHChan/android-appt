package edu.pdx.cs410J.chanwai;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Objects;

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

        listOfAppts.setOnItemClickListener((adapterView, view, i, l) -> {
            Object item = adapterView.getAdapter().getItem(i);
            String message = "Selected Owner " + ": " + item;
            toast(message);
            Intent intent = new Intent(displayAll.this, displayAllResult.class);
            intent.putExtra(FILENAME, item.toString());
            startActivity(intent);
        });
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