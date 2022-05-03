package com.example.PZ;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;

public class DoctorProfile extends AppCompatActivity {
    TextView textView,textView2,textView3,textView4, mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button buttonCall, dodaj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        final String imie = intent.getExtras().getString("imie");
        final String nazwisko = intent.getExtras().getString("nazwisko");
        final String email = intent.getExtras().getString("email");
        final String adres = intent.getExtras().getString("adres");
        final String telefon = intent.getExtras().getString("telefon");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile_activity);
        textView = (TextView) findViewById(R.id.nazwa);
        textView2 = (TextView) findViewById(R.id.textView8);
        textView3 = (TextView) findViewById(R.id.textView9);
        textView4 = (TextView) findViewById(R.id.textView10);
        buttonCall = findViewById(R.id.btnCall);
        buttonCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telefon));
                startActivity(intent);
            }
        });
        textView.setText(imie+" "+nazwisko);
        textView2.setText("Adres email: "+email);
        textView3.setText("Adres: "+adres);
        textView4.setText("Telefon: "+telefon);
        dodaj = findViewById(R.id.dodajWizyte);
        dodaj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                handleWizyte();
            }
        });
    }
    private void handleWizyte() {
        View view = getLayoutInflater().inflate(R.layout.wizyta_pacjent_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button dodaj = view.findViewById(R.id.dodaj);
        mDisplayDate = (TextView) view.findViewById(R.id.data);
        mDisplayDate.setOnClickListener(new View.OnClickListener()
        {
             @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog dialog = new DatePickerDialog(
                        DoctorProfile.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
