package com.example.PZ;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForDoctorProfile extends AppCompatActivity {
    TextView textView,textView2,textView3,textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        final String imie = intent.getExtras().getString("imie");
        final String nazwisko = intent.getExtras().getString("nazwisko");
        final String email = intent.getExtras().getString("email");
        final String adres = intent.getExtras().getString("adres");
        final String telefon = intent.getExtras().getString("telefon");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fordoctorprofile_activity);
        textView = (TextView) findViewById(R.id.nazwa);
        textView2 = (TextView) findViewById(R.id.textView8);
        textView3 = (TextView) findViewById(R.id.textView9);
        textView4 = (TextView) findViewById(R.id.textView10);

        textView.setText(imie+" "+nazwisko);
        textView2.setText("Adres email: "+email);
        textView3.setText("Adres: "+adres);
        textView4.setText("Telefon: "+telefon);
    }

}
