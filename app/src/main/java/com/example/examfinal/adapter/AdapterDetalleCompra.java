package com.example.examfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.modelo.DetalleCompra;

import java.util.List;

public class AdapterDetalleCompra extends RecyclerView.Adapter<AdapterDetalleCompra.ViewHolder> {

    private Context context;
    private List<DetalleCompra> lista;

    public AdapterDetalleCompra(Context context, List<DetalleCompra> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context)
                .inflate(R.layout.item_detalle_compra, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetalleCompra item = lista.get(position);

        holder.nombre.setText("Producto ID: " + item.getIdProducto());
        holder.cantidad.setText("Cantidad: " + item.getCantidad());
        holder.total.setText("Subtotal: S/ " + item.getSubtotal());

        holder.img.setImageResource(R.drawable.comida);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nombre, cantidad, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgDetalleCompra);
            nombre = itemView.findViewById(R.id.tvDetalleNombre);
            cantidad = itemView.findViewById(R.id.tvDetalleCantidad);
            total = itemView.findViewById(R.id.tvDetalleTotal);
        }
    }
}
