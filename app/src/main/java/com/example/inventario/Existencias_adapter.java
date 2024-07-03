package com.example.inventario;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import android.widget.Filter;
import android.widget.Filterable;

public class Existencias_adapter extends RecyclerView.Adapter<Existencias_adapter.OrderViewHolder> implements Filterable {
    private Context context;
    private List<Dataclass> orders;
    private List<Dataclass> ordersFiltered;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Dataclass order);
    }

    public Existencias_adapter(Context context, List<Dataclass> orders, OnItemClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.ordersFiltered = new ArrayList<>(orders);
        this.listener = listener;
    }

    public void updateData(List<Dataclass> newOrders) {
        this.orders = newOrders;
        this.ordersFiltered = new ArrayList<>(newOrders);
        Log.d("ADAPTER", "Updating data. New size: " + newOrders.size());
        for (Dataclass order : newOrders) {
            Log.d("ADAPTER", "Product: " + order.getNombreProducto() + ", Stock: " + order.getStock());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producto, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Dataclass currentOrder = ordersFiltered.get(position);
        holder.bind(currentOrder, listener);
    }

    @Override
    public int getItemCount() {
        return ordersFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    ordersFiltered = new ArrayList<>(orders);
                } else {
                    List<Dataclass> filteredList = new ArrayList<>();
                    for (Dataclass row : orders) {
                        if (row.getNombreProducto().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    ordersFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ordersFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ordersFiltered = (ArrayList<Dataclass>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView foto;
        private TextView nom_product;
        private TextView nom_cate;
        private TextView stock;
        private TextView estado;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            nom_product = itemView.findViewById(R.id.nom_product);
            nom_cate = itemView.findViewById(R.id.nom_cate);
            stock = itemView.findViewById(R.id.stock);
            estado = itemView.findViewById(R.id.estado);
        }

        public void bind(final Dataclass product, final OnItemClickListener listener) {
            Log.d("Existencias_adapter", "Binding product: " + product.getNombreProducto());
            Log.d("Existencias_adapter", "foto: " + (foto != null ? "not null" : "null"));
            Log.d("Existencias_adapter", "nom_product: " + (nom_product != null ? "not null" : "null"));
            Log.d("Existencias_adapter", "nom_cate: " + (nom_cate != null ? "not null" : "null"));
            Log.d("Existencias_adapter", "stock: " + (stock != null ? "not null" : "null"));
            Log.d("Existencias_adapter", "estado: " + (estado != null ? "not null" : "null"));
            if (foto != null && product.getImagen() != null) {
                Glide.with(itemView.getContext()).load(product.getImagen()).into(foto);
            }
            if (nom_product != null) {
                nom_product.setText(product.getNombreProducto());
            }
            if (nom_cate != null) {
                nom_cate.setText("Categoría ID: " + product.getIdCategorias());
            }
            if (stock != null) {
                stock.setText("Unidades: " + product.getStock());
            }
            if (estado != null) {
                estado.setText("Estado: " + product.getEstado());
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(product);
                }
            });
        }
    }

}
