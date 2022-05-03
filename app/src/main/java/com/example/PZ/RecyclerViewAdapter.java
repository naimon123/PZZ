package com.example.PZ;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.PZ.info.DoktorSzukaj;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.internal.tls.OkHostnameVerifier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 1/1/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<DoktorSzukaj> Nazwa;
    private ArrayList<DoktorSzukaj> Okrojone;
    private Context mContext;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";


    RecyclerViewAdapter(Context context, ArrayList<DoktorSzukaj> Okrojone) {
        this.Okrojone = Okrojone;
        Nazwa = new ArrayList<>(Okrojone);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        DoktorSzukaj currentItem = Nazwa.get(position);
        holder.imageName.setText(currentItem.getNazwa());
        holder.nick.setText(currentItem.getNick());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofitInterface = retrofit.create(RetrofitInterface.class);
                final String nick = Okrojone.get(position).getNick();
                HashMap<String, String> map = new HashMap<>();
                map.put("nazwa_uzyt", nick);
                Call<LoginResult> call = retrofitInterface.executeProfil(map);
                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if (response.code() == 200) {
                            LoginResult result = response.body();
                            Intent intent = new Intent(mContext, DoctorProfile.class);
                            intent.putExtra("imie",result.getImie());
                            intent.putExtra("nazwisko",result.getNazwisko());
                            intent.putExtra("email",result.getEmail());
                            intent.putExtra("adres",result.getAdres());
                            intent.putExtra("telefon",result.getTelefon());
                            mContext.startActivity(intent);
                        } else if (response.code() == 404) {
                            System.out.println("blad");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        System.out.println("blad");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return Nazwa.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<DoktorSzukaj> filtered = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0)
            {
                filtered.addAll(Nazwa);
            }
            else
            {
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for(DoktorSzukaj item : Nazwa)
                {
                    if(item.getNazwa().toLowerCase().contains(filterPattern))
                        filtered.add(item);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Okrojone.clear();
            Okrojone.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView imageName;
        TextView nick;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.nick);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}