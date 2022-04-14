package com.example.PZ;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {


    RadioButton radioButton;
    RadioGroup radioGroup;


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLoginDialog();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignupDialog();
            }
        });
    }

    private void handleLoginDialog() {

        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.login);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());


                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if (response.code() == 200) {

                            LoginResult result = response.body();
                            //result.get;
                            if((result.getKonto()).equals("Doktor"))
                            {
                                Intent k = new Intent(MainActivity.this, DoctorActivity.class);
                                k.putExtra("imie",result.getImie());
                                k.putExtra("nazwisko",result.getNazwisko());
                                startActivity(k);
                            }
                            else
                            {
                                Intent k = new Intent(MainActivity.this, ClientActivity.class);
                                //k.putExtra("konto",result.getKonto());
                                k.putExtra("imie",result.getImie());
                                k.putExtra("nazwisko",result.getNazwisko());
                                startActivity(k);
                            }


                        } else if (response.code() == 404) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                            builder1.setTitle("Logowanie nie powiodło się");
                            builder1.setMessage("Niepoprawne dane!");

                            builder1.show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }


    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        String[] konto = { "Klient", "Lekarz"};
        Button signupBtn = view.findViewById(R.id.signup);
        final EditText nazwaEdit = view.findViewById(R.id.nazwa_uzyt);
        final EditText imieEdit = view.findViewById(R.id.imie);
        final EditText nazwiskoEdit = view.findViewById(R.id.nazwisko);
        final EditText emailEdit = view.findViewById(R.id.emailEdit);
        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);
        final EditText passwordEdit2 = view.findViewById(R.id.passwordEdit2);

        final String confirmPass;
        radioGroup = view.findViewById(R.id.radioGroup);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String confirmPass = button1.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("nazwa_uzyt", nazwaEdit.getText().toString());
                map.put("imie", imieEdit.getText().toString());
                map.put("nazwisko", nazwiskoEdit.getText().toString());
                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());
                map.put("konto", (String) radioButton.getText());

                String haslo = passwordEdit.getText().toString();
                String confirm = passwordEdit2.getText().toString();


                Call<Void> call = retrofitInterface.executeSignup(map);
                if (!haslo.equals(confirm)) {
                    Toast.makeText(MainActivity.this,
                            "Wprowadzono rozne hasla.", Toast.LENGTH_LONG).show();
                } else {
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            if (response.code() == 200) {
                                Toast.makeText(MainActivity.this,
                                        "Pomyslnie zarejestrowano", Toast.LENGTH_LONG).show();
                                Intent k = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(k);
                            } else if (response.code() == 400) {
                                Toast.makeText(MainActivity.this,
                                        "Jestes juz zarejestrowany", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
    public void checkButton(View view)
    {
        int radioID = radioGroup.getCheckedRadioButtonId();

        radioButton = view.findViewById(radioID);
    }

}
