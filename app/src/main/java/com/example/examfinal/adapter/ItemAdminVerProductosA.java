package com.example.examfinal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.RegistrarseActivity;
import com.example.examfinal.admin.AdminAgregarProductoActivity;
import com.example.examfinal.modelo.Producto;
import com.example.examfinal.negocio.ProductoNegocio;

import java.io.File;
import java.util.List;

public class ItemAdminVerProductosA extends RecyclerView.Adapter<ItemAdminVerProductosA.ProductosViewHolder> {

    private Context context;
    private List<Producto> listaProductos;

    public ItemAdminVerProductosA(Context context, List<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_ver_platillo, parent, false);
        return new ProductosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        // Mostrar datos del producto
        holder.tvPlatillosNombre.setText(producto.getNombre());
        holder.tvPlatillosPrecio.setText("S/ " + producto.getPrecio());
        holder.tvPlatillosStock.setText("Stock: " + producto.getStock());

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

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminAgregarProductoActivity.class);
            intent.putExtra("ProductoEditar", producto);
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Producto")
                    .setMessage("¿Seguro que deseas eliminar el producto: " + producto.getNombre() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {

                        ProductoNegocio negocio = new ProductoNegocio(context);
                        String respuesta = negocio.eliminarProducto(producto.getId()); // eliminar por ID

                        Toast.makeText(context, respuesta, Toast.LENGTH_SHORT).show();

                        if (respuesta.contains("eliminado")) {
                            listaProductos.remove(position); // quitar de la lista
                            notifyItemRemoved(position);     // actualizar RecyclerView
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });


    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    // ViewHolder
    public static class ProductosViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemPlatillos;
        TextView tvPlatillosNombre, tvPlatillosPrecio, tvPlatillosStock;
        ImageButton btnEditar, btnEliminar;

        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemPlatillos = itemView.findViewById(R.id.imgItemPlatillo);
            tvPlatillosNombre = itemView.findViewById(R.id.tvPlatilloNombre);
            tvPlatillosPrecio = itemView.findViewById(R.id.tvPlatilloPrecio);
            tvPlatillosStock = itemView.findViewById(R.id.tvPlatilloCantidad);
            btnEditar = itemView.findViewById(R.id.btnEditarPlatillo);
            btnEliminar = itemView.findViewById(R.id.btnEliminarPlatillo);
        }
    }
}
