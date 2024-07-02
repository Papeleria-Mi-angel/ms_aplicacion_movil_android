package com.example.inventario;

import com.google.gson.annotations.SerializedName;

public class Dataclass {
    @SerializedName("idProducto")
    private int idProducto;

    @SerializedName("idCategorias")
    private int idCategorias;

    @SerializedName("idProveedor")
    private int idProveedor;

    @SerializedName("nombre_product")
    private String nombreProducto;

    @SerializedName("stock")
    private int stock;

    @SerializedName("codigo_producto")
    private long codigoProducto;

    @SerializedName("imagen")
    private String imagen;

    @SerializedName("precio")
    private int precio;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("estado")
    private String estado;

    // Getters and setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(int idCategorias) {
        this.idCategorias = idCategorias;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
