package com.example.inventario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Producto_adapter extends RecyclerView.Adapter<Producto_adapter.OrderViewHolder> {
    private Context context;
    private List<Dataclass> orders;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Dataclass order);
    }

    public Producto_adapter(Context context, List<Dataclass> orders, OnItemClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producto, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Dataclass currentOrder = orders.get(position);
        holder.bind(currentOrder, listener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView foto;
        private TextView nom_product;
        private TextView nom_cate;
        private TextView stock;
        private TextView precio;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            nom_product = itemView.findViewById(R.id.nom_product);
            nom_cate = itemView.findViewById(R.id.nom_cate);
            stock = itemView.findViewById(R.id.stock);
            precio = itemView.findViewById(R.id.precio);
        }

        public void bind(final Dataclass product, final OnItemClickListener listener) {
            Glide.with(itemView.getContext()).load(product.getImagen()).into(foto);
            nom_product.setText(product.getNombreProducto());
            nom_cate.setText("Categoría ID: " + product.getIdCategorias());
            stock.setText("Unidades: " + product.getStock());
            precio.setText("Precio: $" + product.getPrecio());

            itemView.setOnClickListener(v -> listener.onItemClick(product));
        }
    }
}
