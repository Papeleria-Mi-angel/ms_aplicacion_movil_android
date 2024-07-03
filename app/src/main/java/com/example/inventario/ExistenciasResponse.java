package com.example.inventario;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExistenciasResponse {
    @SerializedName("productos")
    private List<Dataclass> productos;

    public List<Dataclass> getProductos() {
        return productos;
    }

    public void setProductos(List<Dataclass> productos) {
        this.productos = productos;
    }
}
