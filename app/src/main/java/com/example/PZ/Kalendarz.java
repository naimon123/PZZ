package com.example.PZ;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
//import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Kalendarz extends AppCompatActivity {
    CalendarView calendarView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

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
}
