package com.example.inventario;

import com.google.gson.annotations.SerializedName;

public class Dataclass {
    @SerializedName("imagen")
    private String foto;
    @SerializedName("nombre_product")
    private String nom_product;
    @SerializedName("idCategorias")
    private int nom_cate;
    @SerializedName("stock")
    private int stock;
    @SerializedName("precio")
    private int precio;

    // Getters and setters
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNom_product() {
        return nom_product;
    }

    public void setNom_product(String nom_product) {
        this.nom_product = nom_product;
    }

    public int getNom_cate() {
        return nom_cate;
    }

    public void setNom_cate(int nom_cate) {
        this.nom_cate = nom_cate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
