package com.example.examfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.modelo.Compra;

import java.util.List;

public class ItemClienteVerCompras
        extends RecyclerView.Adapter<ItemClienteVerCompras.ViewHolder> {

    private Context context;
    private List<Compra> listaCompras;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onVerDetalleClick(Compra compra);
    }

    public ItemClienteVerCompras(Context context, List<Compra> listaCompras,
                                 OnItemClickListener listener) {
        this.context = context;
        this.listaCompras = listaCompras;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cliente_ver_compras, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compra compra = listaCompras.get(position);

        // Imagen fija del bolso
        holder.imgCompra.setImageResource(R.drawable.bolso);

        // Texto: Compra 1, Compra 2, etc.
        holder.txtTitulo.setText("Compra " + (position + 1));

        // Fecha de la compra
        holder.txtFecha.setText("Fecha: " + compra.getFecha());

        // Botón "Ver detalle"
        holder.btnVerDetalle.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVerDetalleClick(compra);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCompra;
        TextView txtTitulo, txtFecha;
        Button btnVerDetalle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCompra = itemView.findViewById(R.id.imgCompra);
            txtTitulo = itemView.findViewById(R.id.txtCompraTitulo);
            txtFecha = itemView.findViewById(R.id.txtCompraFecha);
            btnVerDetalle = itemView.findViewById(R.id.btnVerDetalle);
        }
    }
}
