package com.example.PZ;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
//import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Wyszukaj extends AppCompatActivity {
    CalendarView calendarView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureToolbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wyszukaj_activity);

        calendarView = (CalendarView)findViewById(R.id.calendar1);
        textView = (TextView) findViewById(R.id.date_view);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int rok, int miesiac, int dzien) {
                String Date = dzien + "-" + (miesiac+1) + "-" + rok;
                textView.setText(Date);
            }
        });
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle("Doctors list");
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }
}
