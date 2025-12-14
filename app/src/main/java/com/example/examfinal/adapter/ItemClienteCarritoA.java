package com.example.examfinal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.modelo.Producto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemClienteCarritoA extends RecyclerView.Adapter<ItemClienteCarritoA.CarritoViewHolder> {

    private Context context;
    private List<Producto> lista;

    private Runnable onUpdateTotal;

    public ItemClienteCarritoA(Context context, List<Producto> lista, Runnable onUpdateTotal) {
        this.context = context;
        this.lista = lista;
        this.onUpdateTotal = onUpdateTotal;
    }


    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cliente_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        Producto producto = lista.get(position);

        int cantidad = CarritoTemp.getInstancia().obtenerCantidad(producto.getId());
        double total = cantidad * producto.getPrecio();

        holder.tvNombre.setText(producto.getNombre());
        holder.tvCantidad.setText("Cantidad: " + cantidad);
        holder.tvTotal.setText("Total: S/ " + total);

        // Imagen
        if (producto.getFoto() != null) {
            File file = new File(producto.getFoto());
            if (file.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                holder.img.setImageBitmap(bmp);
            }
        }

        // BOTÓN +
        holder.btnMas.setOnClickListener(v -> {
            int cantActual = CarritoTemp.getInstancia().obtenerCantidad(producto.getId());
            cantActual++;

            CarritoTemp.getInstancia().actualizar(producto.getId(), cantActual);
            notifyItemChanged(position);

            onUpdateTotal.run();   // ← ESTA ES LA LÍNEA QUE FALTABA
        });


        // BOTÓN –
        holder.btnMenos.setOnClickListener(v -> {
            int cantActual = CarritoTemp.getInstancia().obtenerCantidad(producto.getId());

            if (cantActual > 1) {
                cantActual--;
                CarritoTemp.getInstancia().actualizar(producto.getId(), cantActual);
                notifyItemChanged(position);
            } else {
                CarritoTemp.getInstancia().actualizar(producto.getId(), 0);
                lista.remove(position);
                notifyItemRemoved(position);
            }

            onUpdateTotal.run();   // ← ESTA ES LA LÍNEA QUE ACTUALIZA EL TOTAL
        });


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class CarritoViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvNombre, tvCantidad, tvTotal;
        Button btnMas, btnMenos;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgCarritoItem);
            tvNombre = itemView.findViewById(R.id.tvCarritoNombre);
            tvCantidad = itemView.findViewById(R.id.tvCarritoCantidad);
            tvTotal = itemView.findViewById(R.id.tvCarritoTotal);
            btnMas = itemView.findViewById(R.id.button3);
            btnMenos = itemView.findViewById(R.id.button2);
        }
    }
}
