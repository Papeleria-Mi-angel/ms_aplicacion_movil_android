package com.example.inventario;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Categoria_adapter extends RecyclerView.Adapter<Categoria_adapter.OrderViewHolder> implements Filterable {

    private Context context;
    private List<Datacatego> orders;
    private List<Datacatego> ordersFiltered;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Datacatego order);
    }

    public Categoria_adapter(Context context, List<Datacatego> orders, OnItemClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.ordersFiltered = new ArrayList<>(orders);
        this.listener = listener;
    }

    public void uptadeData(List<Datacatego> newOrders) {
        this.orders = newOrders;
        this.ordersFiltered = new ArrayList<>(newOrders);
        Log.d("ADAPTER", "Updating data. New size: " + newOrders.size());
        for (Datacatego order : newOrders) {
            Log.d("ADAPTER", "Categoria: " + order.getCategoria() + ", Descripcion: " + order.getDescripcion_categoria());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categorias, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Datacatego currentOrder = ordersFiltered.get(position);
        Log.d("ADAPTER", "Binding view for position " + position + ": " + currentOrder.getCategoria());
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
                    List<Datacatego> filteredList = new ArrayList<>();
                    for (Datacatego row : orders) {
                        if (row.getCategoria().toLowerCase().contains(charString.toLowerCase())) {
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
                ordersFiltered = (ArrayList<Datacatego>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_categoria;
        private TextView nombre_categoria;
        private TextView info_cate;
        private Button Ver_Productos;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            img_categoria = itemView.findViewById(R.id.img_categoria);
            nombre_categoria = itemView.findViewById(R.id.nombre_categoria);
            info_cate = itemView.findViewById(R.id.info_cate);
            Ver_Productos = itemView.findViewById(R.id.Ver_Productos);
        }

        public void bind(final Datacatego categorias, final OnItemClickListener listener) {
            Glide.with(itemView.getContext()).load(categorias.getImagen()).into(img_categoria);
            nombre_categoria.setText(categorias.getCategoria());
            info_cate.setText(categorias.getDescripcion_categoria());

            itemView.setOnClickListener(v -> listener.onItemClick(categorias));

            // Configurar el clic del botón para iniciar la nueva actividad
            Ver_Productos.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), Categoria_Producto.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
