package com.example.inventario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Interapi {
    @Headers("Content-Type: application/json")
    @GET("producto")
    Call<List<Dataclass>> getproducto();
}
