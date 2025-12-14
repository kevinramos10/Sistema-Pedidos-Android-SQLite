package com.example.examfinal.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.examfinal.modelo.Cliente;
import com.example.examfinal.negocio.ClienteNegocio;

import java.util.List;

public class ItemAdminVerClientesA extends RecyclerView.Adapter<ItemAdminVerClientesA.ClienteViewHolder> {
    private Context context;

    private List<Cliente> listaCliente;

    public ItemAdminVerClientesA(Context context, List<Cliente> listaCliente) {
        this.context = context;
        this.listaCliente = listaCliente;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_ver_clientes, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = listaCliente.get(position);
        holder.tvUsersNombre.setText(cliente.getNombres() + " " + cliente.getApellidos());

        // Imagen fija por ahora
        holder.imgItemUsers.setImageResource(R.drawable.usuarios);

        // Los botones editar y eliminar se implementarán luego
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegistrarseActivity.class);
            intent.putExtra("clienteEditar", cliente); // enviar el objeto
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Cliente")
                    .setMessage("¿Seguro que deseas eliminar a " + cliente.getNombres() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {

                        ClienteNegocio negocio = new ClienteNegocio(context);
                        String respuesta = negocio.eliminarCliente(cliente.getId()); // eliminar por ID

                        Toast.makeText(context, respuesta, Toast.LENGTH_SHORT).show();

                        if (respuesta.contains("eliminado")) {
                            listaCliente.remove(position); // quitar de la lista
                            notifyItemRemoved(position); // actualizar RecyclerView
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        });

    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemUsers;
        TextView tvUsersNombre;
        ImageButton btnEditar, btnEliminar;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemUsers = itemView.findViewById(R.id.imgItemUsers);
            tvUsersNombre = itemView.findViewById(R.id.tvClienteNombre);
            btnEditar = itemView.findViewById(R.id.btnEditarCliente);
            btnEliminar = itemView.findViewById(R.id.btnEliminarCliente);
        }
    }

}
