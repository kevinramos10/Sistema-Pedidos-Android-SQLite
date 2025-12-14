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

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

public class ItemClientePlatillos extends RecyclerView.Adapter<ItemClientePlatillos.PlatillosViewHolder> {

    private Context context;

    private List<Producto> listaPlatillos;

    public ItemClientePlatillos(Context context, List<Producto> listaPlatillos) {
        this.context = context;
        this.listaPlatillos = listaPlatillos;
    }

    @NonNull
    @Override
    public PlatillosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cliente_platillo, parent, false);
        return new PlatillosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatillosViewHolder holder, int position) {
        Producto producto = listaPlatillos.get(position);

        // Mostrar datos del producto
        holder.tvClientePlatilloNombre.setText(producto.getNombre());
        holder.tvClientePlatilloStock.setText("Stock: " + producto.getStock());
        holder.tvClientePlatilloPrecio.setText("S/ " + producto.getPrecio());

        // Mostrar imagen
        String fotoPath = producto.getFoto();
        if (fotoPath != null && !fotoPath.isEmpty()) {
            File imgFile = new File(fotoPath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgItemPlatillos.setImageBitmap(bitmap);
            } else {
                holder.imgItemPlatillos.setImageResource(R.drawable.comida);
            }
        } else {
            holder.imgItemPlatillos.setImageResource(R.drawable.comida);
        }

        // ============================================================
        //  Recuperar cantidad guardada en CarritoTemp
        // ============================================================
        int cantidadGuardada = CarritoTemp.getInstancia().obtenerCantidad(producto.getId());
        holder.tvClientePlatilloCantidad.setText(String.valueOf(cantidadGuardada));

        // ============================================================
        // ➕ Botón MAS
        // ============================================================
        holder.btnClientePlatilloMas.setOnClickListener(v -> {
            int cantidad = Integer.parseInt(holder.tvClientePlatilloCantidad.getText().toString());
            if (cantidad < producto.getStock()) {
                cantidad++;
                holder.tvClientePlatilloCantidad.setText(String.valueOf(cantidad));

                // Guardar en carrito temporal
                CarritoTemp.getInstancia().actualizar(producto.getId(), cantidad);
            }
        });

        // ============================================================
        // ➖ Botón MENOS
        // ============================================================
        holder.btnClientePlatilloMenos.setOnClickListener(v -> {
            int cantidad = Integer.parseInt(holder.tvClientePlatilloCantidad.getText().toString());
            if (cantidad > 0) {
                cantidad--;
                holder.tvClientePlatilloCantidad.setText(String.valueOf(cantidad));

                // Guardar en carrito temporal
                CarritoTemp.getInstancia().actualizar(producto.getId(), cantidad);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatillos.size();
    }

    //ViewHolder
    public static class PlatillosViewHolder extends RecyclerView.ViewHolder{
        ImageView imgItemPlatillos;
        TextView tvClientePlatilloNombre, tvClientePlatilloStock, tvClientePlatilloPrecio, tvClientePlatilloCantidad;

        Button btnClientePlatilloMenos, btnClientePlatilloMas;

        public PlatillosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemPlatillos = itemView.findViewById(R.id.imgClientePlatillo);
            tvClientePlatilloNombre = itemView.findViewById(R.id.tvNombrePlatillo);
            tvClientePlatilloStock = itemView.findViewById(R.id.tvStockPlatillo);
            tvClientePlatilloPrecio = itemView.findViewById(R.id.tvPrecioPlatillo);
            tvClientePlatilloCantidad = itemView.findViewById(R.id.tvCantidad);

            btnClientePlatilloMenos = itemView.findViewById(R.id.btnMenos);
            btnClientePlatilloMas = itemView.findViewById(R.id.btnMas);
        }
    }
}
