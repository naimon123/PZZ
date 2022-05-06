package com.example.PZ;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PZ.info.DoktorSzukaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Wyszukaj extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private List<DoktorSzukaj> exampleList;
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
                    ArrayList<LoginResult> tab = response.body();
                    exampleList = new ArrayList<>();
                    for (int i = 0; i < tab.size(); i++)
                    {
                        exampleList.add(new DoktorSzukaj("Dr " + tab.get(i).getImie() + " " + tab.get(i).getNazwisko(),
                                tab.get(i).getName()));
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Wyszukaj.this);
                    adapter = new RecyclerViewAdapter(Wyszukaj.this,exampleList);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;

    }
}
