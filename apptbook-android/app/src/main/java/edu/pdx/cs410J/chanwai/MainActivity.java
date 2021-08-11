package edu.pdx.cs410J.chanwai;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.pdx.cs410J.chanwai.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final int GET_APPOINTMENT_FROM_ACTIVITY = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Button goToSearch = findViewById(R.id.go_to_search);
        goToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, search.class);
            startActivity(intent);
        });

        Button goToDisplay = findViewById(R.id.go_to_display);
        goToDisplay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, displayAll.class);
            startActivity(intent);
        });

        Button goToReadMe = findViewById(R.id.go_to_read);
        goToReadMe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, readme.class);
            startActivity(intent);
        });

        Button goToSearchByName = findViewById(R.id.search_by_name);
        goToSearchByName.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, searchByName.class);
            startActivity(intent);
        });

        Button makeAppt = findViewById(R.id.make_appointment);
        makeAppt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MakeNewAppointmentActivity.class);
            startActivityForResult(intent, GET_APPOINTMENT_FROM_ACTIVITY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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