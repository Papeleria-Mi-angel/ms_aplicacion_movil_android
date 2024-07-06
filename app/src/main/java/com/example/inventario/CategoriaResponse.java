package com.example.inventario;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoriaResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("status")
    private int status;

    @SerializedName("body")
    private List<Datacatego> body;

    public boolean isError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public List<Datacatego> getBody() {
        return body;
    }
}