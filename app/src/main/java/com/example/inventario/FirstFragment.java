package com.example.inventario;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
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

public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private Categoria_adapter adapter;
    private List<Datacatego> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        recyclerView = view.findViewById(R.id.catego_resicle);
        SearchView searchView = view.findViewById(R.id.busqueda);
        orderList = new ArrayList<>();
        adapter = new Categoria_adapter(getContext(), orderList, order -> {
            // Implement your onItemClick logic here
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Log.d("FRAGMENT", "RecyclerView setup complete");

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

        categoria();
        return view;
    }

    private void categoria() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ms-inventario-api-mi-angel-1.onrender.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        Interapi apiService = retrofit.create(Interapi.class);
        Call<CategoriaResponse> call = apiService.getCategoria();

        call.enqueue(new Callback<CategoriaResponse>() {
            @Override
            public void onResponse(Call<CategoriaResponse> call, Response<CategoriaResponse> response) {
                if (response.isSuccessful()) {
                    CategoriaResponse categoriaResponse = response.body();
                    if (categoriaResponse != null && !categoriaResponse.isError() && categoriaResponse.getBody() != null && !categoriaResponse.getBody().isEmpty()) {
                        List<Datacatego> categorias = categoriaResponse.getBody();
                        Log.d("API_RESPONSE", "Number of categories: " + categorias.size());
                        updateRecyclerView(categorias);
                    } else {
                        Log.e("API_RESPONSE", "No se recibieron categorías o la lista está vacía");
                    }
                } else {
                    Log.e("API_RESPONSE", "Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriaResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_RESPONSE", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

    private void updateRecyclerView(List<Datacatego> categorias) {
        getActivity().runOnUiThread(() -> {
            orderList.clear();
            orderList.addAll(categorias);
            adapter.uptadeData(orderList);
            adapter.notifyDataSetChanged();
            Log.d("UPDATE_RV", "RecyclerView updated with " + categorias.size() + " items");
        });
    }
}
