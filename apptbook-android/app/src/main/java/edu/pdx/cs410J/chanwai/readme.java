package edu.pdx.cs410J.chanwai;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class readme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        Button b_read = findViewById(R.id.read_me);
        TextView tv_text = findViewById(R.id.tv_text);
        b_read.setOnClickListener(v -> {
            String text = "";
            try {
                InputStream is = getAssets().open("README.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                text = new String(buffer);
            }catch (IOException ex){
                ex.printStackTrace();
            }
            tv_text.setText(text);
        });

        Button returnToMain = findViewById(R.id.return_to_main);
        returnToMain.setOnClickListener(v -> finish());
    }
}
