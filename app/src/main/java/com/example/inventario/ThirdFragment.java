package com.example.inventario;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.SearchView;

public class ThirdFragment extends Fragment {
    private RecyclerView recyclerView;
    private Existencias_adapter adapter;
    private List<Dataclass> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        recyclerView = view.findViewById(R.id.exist_resicle);
        SearchView searchView = view.findViewById(R.id.searchView);
        orderList = new ArrayList<>();
        adapter = new Existencias_adapter(getContext(), orderList, order -> {
            // Acción al hacer clic en un item
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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

        producto();

        return view;
    }

    private void producto() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ms-inventario-api-mi-angel-1.onrender.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Interapi apiService = retrofit.create(Interapi.class);
        Call<ExistenciasResponse> call = apiService.getProductosAgotados();

        call.enqueue(new Callback<ExistenciasResponse>() {
            @Override
            public void onResponse(Call<ExistenciasResponse> call, Response<ExistenciasResponse> response) {
                if (response.isSuccessful()) {
                    ExistenciasResponse existenciasResponse = response.body();
                    if (existenciasResponse != null && existenciasResponse.getProductos() != null && !existenciasResponse.getProductos().isEmpty()) {
                        List<Dataclass> productos = existenciasResponse.getProductos();
                        updateRecyclerView(productos);
                    } else {
                        Log.e("API_RESPONSE", "No se recibieron productos o la lista está vacía");
                    }
                } else {
                    Log.e("API_RESPONSE", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ExistenciasResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateRecyclerView(List<Dataclass> productos) {
        orderList.clear();
        orderList.addAll(productos);
        adapter.updateData(orderList);
        adapter.notifyDataSetChanged();
    }
}
