package com.example.PZ;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Wyszukaj extends AppCompatActivity {

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wyszukaj_activity);


        Call <ArrayList<LoginResult>> call =  retrofitInterface.executeKonto();

        call.enqueue(new Callback<ArrayList<LoginResult>>() {
            @Override
            public void onResponse(Call<ArrayList<LoginResult>> call, Response<ArrayList<LoginResult>> response) {

                if (response.code() == 200) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Wyszukaj.this);
                    ArrayList<LoginResult> tab = response.body();

                    builder1.setTitle(tab.get(1).getNazwisko());
                    builder1.setMessage("Cos nie poszlo");
                    builder1.show();
                    //result.get;
                    mNames.add("szymomn");

                    mNames.add("TPawel Lukasi");

                    mNames.add("Portugal");

                    mNames.add("Rocky Mountain National Park");


                    initRecyclerView();
                } else if (response.code() == 404) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Wyszukaj.this);
                    builder1.setTitle("Cos nie poszlo");
                    builder1.setMessage("Cos nie poszlo");
                    builder1.show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoginResult>> call, Throwable t) {
                Toast.makeText(Wyszukaj.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        });

    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
