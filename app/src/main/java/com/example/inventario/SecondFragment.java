package com.example.inventario;

import android.os.Bundle;
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

public class SecondFragment extends Fragment {
    private RecyclerView recyclerView;
    private Producto_adapter adapter;
    private List<Dataclass> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = view.findViewById(R.id.view_resicle);
        SearchView searchView = view.findViewById(R.id.searchView);
        orderList = new ArrayList<>();
        adapter = new Producto_adapter(getContext(), orderList, order -> {
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
        Call<ProductoResponse> call = apiService.getproducto();

        call.enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if (response.isSuccessful()) {
                    ProductoResponse productoResponse = response.body();
                    if (productoResponse != null && productoResponse.getProductos() != null) {
                        List<Dataclass> productos = productoResponse.getProductos();
                        orderList.clear();
                        orderList.addAll(productos);
                        adapter.updateData(orderList);
                        Toast.makeText(getContext(), "Productos cargados con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No se recibieron productos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
