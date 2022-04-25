package com.example.PZ;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;

public class ClientActivity  extends AppCompatActivity {

    Button wizyta;
    Button kalendarz;
    Button wyloguj;
    Button wyszukaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity);
        Intent k = getIntent();
        final String imie = k.getExtras().getString("imie");
        final String nazwisko = k.getExtras().getString("nazwisko");
        final String string = k.getStringExtra("message");
        wizyta = findViewById(R.id.wizyty);
        wyloguj = findViewById(R.id.signOutBtn);
        kalendarz = findViewById(R.id.kalendarz);
        wyszukaj = findViewById(R.id.wyszukaj);

        kalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientActivity.this, Kalendarz.class);
                startActivity(intent);

            }
        });

        wyszukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientActivity.this, Wyszukaj.class);
                startActivity(intent);
            }
        });


        wizyta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent k = new Intent(ClientActivity.this, PatientAppointementsActivity.class);
                //startActivity(k);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientActivity.this);
                builder1.setTitle("Nazywasz się: ");
                builder1.setMessage(imie+" "+nazwisko);
                builder1.show();
            }
        });
        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ClientActivity.this);
                builder2.setTitle("Czy na pewno chcesz się wylogować?");
                builder2.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ClientActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder2.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder2.create();
                alertDialog.show();
            }
        });
    }
}