package com.example.PZ;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity);
        Intent k = getIntent();
        final String imie = k.getExtras().getString("imie");
        final String nazwisko = k.getExtras().getString("nazwisko");
        wizyta = findViewById(R.id.wizyty);

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

    }
}