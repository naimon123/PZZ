package com.example.PZ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/profil")
    Call<LoginResult> executeProfil(@Body HashMap<String, String> map);

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/wyszukaj")
    Call<ArrayList<LoginResult>> executeKonto ();

    @POST("/pacjenci")
    Call<ArrayList<LoginResult>> executePacjenci ();

}
