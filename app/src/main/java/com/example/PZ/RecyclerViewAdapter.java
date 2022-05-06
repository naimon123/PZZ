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
import java.util.List;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ExampleViewHolder> implements Filterable {
    private List<DoktorSzukaj> exampleList;
    private List<DoktorSzukaj> exampleListFull;
    private Retrofit retrofit;
    private Context mContext;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView imageName;
        TextView nick;
        int position;

        ExampleViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.image_name);
            nick = itemView.findViewById(R.id.nick);

            itemView.findViewById(R.id.przycisk).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    DoktorSzukaj currentItem = exampleList.get(position);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    retrofitInterface = retrofit.create(RetrofitInterface.class);
                    final String nick = exampleList.get(position).getNick();
                    System.out.println(nick);
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


    }

    RecyclerViewAdapter(Context context,List<DoktorSzukaj> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        mContext = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DoktorSzukaj currentItem = exampleList.get(position);

        holder.imageName.setText(currentItem.getNazwa());
        holder.nick.setText(currentItem.getNick());
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DoktorSzukaj> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DoktorSzukaj item : exampleListFull) {
                    if (item.getNazwa().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}