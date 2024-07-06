package com.example.inventario;

import com.google.gson.annotations.SerializedName;

public class Datacatego {

    @SerializedName("idCategorias")
    private int idCategorias;
    @SerializedName("Categoria")
    private String Categoria;
    @SerializedName("descripcion_categoria")
    private String descripcion_categoria;
    @SerializedName("imagen")
    private String imagen;
    @SerializedName("fecha")
    private String fecha;

    public int getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(int idCategorias) {
        this.idCategorias = idCategorias;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
