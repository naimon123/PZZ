package com.example.PZ;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;



public class DoctorActivity  extends AppCompatActivity {
    Button SignOutBtn;
    Button profil;
    Button kalendarz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity);
        Intent k = getIntent();
        final String imie = k.getExtras().getString("imie");
        final String nazwisko = k.getExtras().getString("nazwisko");
        final String email = k.getExtras().getString("email");
        final String adres = k.getExtras().getString("adres");
        final String telefon = k.getExtras().getString("telefon");
        profil = findViewById(R.id.profil);
        SignOutBtn = findViewById(R.id.signOutBtn);
        kalendarz = findViewById(R.id.kalendarz);

        kalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this, Kalendarz.class);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent k = new Intent(ClientActivity.this, PatientAppointementsActivity.class);
                //startActivity(k);
                //AlertDialog.Builder builder1 = new AlertDialog.Builder(DoctorActivity.this);
                //builder1.setTitle("Nazywasz się: ");
                //builder1.setMessage(imie+" "+nazwisko);
                //builder1.show();
                Intent intent = new Intent(DoctorActivity.this, DoctorProfile.class);
                intent.putExtra("imie",imie);
                intent.putExtra("nazwisko",nazwisko);
                intent.putExtra("email",email);
                intent.putExtra("adres",adres);
                intent.putExtra("telefon",telefon);
                startActivity(intent);
            }
        });
        SignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(DoctorActivity.this);
                builder2.setTitle("Czy na pewno chcesz się wylogować?");
                builder2.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(DoctorActivity.this, MainActivity.class);
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
