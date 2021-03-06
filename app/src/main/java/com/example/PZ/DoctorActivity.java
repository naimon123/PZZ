package com.example.PZ;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class DoctorActivity  extends AppCompatActivity {
    Button SignOutBtn,dodaj;
    Button profil;
    Button kalendarz;
    Button recepty;
    TextView mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

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
        dodaj = findViewById(R.id.wizyty);
        recepty = findViewById(R.id.recepty);
        dodaj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                handleWizyte();
            }
        });
        kalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this, Kalendarz.class);
                startActivity(intent);
            }
        });

        recepty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this, Recepty.class);
                startActivity(intent);
            }
        });


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent k = new Intent(ClientActivity.this, PatientAppointementsActivity.class);
                //startActivity(k);
                //AlertDialog.Builder builder1 = new AlertDialog.Builder(DoctorActivity.this);
                //builder1.setTitle("Nazywasz si??: ");
                //builder1.setMessage(imie+" "+nazwisko);
                //builder1.show();
                Intent intent = new Intent(DoctorActivity.this, ForDoctorProfile.class);
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
                builder2.setTitle("Czy na pewno chcesz si?? wylogowa???");
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
    private void handleWizyte() {
        View view = getLayoutInflater().inflate(R.layout.wizyta_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        final EditText imie = view.findViewById(R.id.imie);
        final EditText nazwisko = view.findViewById(R.id.nazwisko);

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
                        DoctorActivity.this,
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
