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
import com.example.examfinal.modelo.Administrador;
import com.example.examfinal.modelo.Cliente;
import com.example.examfinal.negocio.AdministradorNegocio;
import com.example.examfinal.negocio.ClienteNegocio;

import java.util.List;

public class ItemAdminVerAdministradoresA extends RecyclerView.Adapter<ItemAdminVerAdministradoresA.AdministradorViewHolder> {

    private Context context;

    private List<Administrador> listaAdministrador;

    public ItemAdminVerAdministradoresA(Context context, List<Administrador> listaAdministrador){
        this.context = context;
        this.listaAdministrador = listaAdministrador;
    }

    @NonNull
    @Override
    public AdministradorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_ver_administradores, parent, false);
        return new AdministradorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdminVerAdministradoresA.AdministradorViewHolder holder, int position) {
        Administrador administrador = listaAdministrador.get(position);

        holder.tvUsersNombre.setText(administrador.getNombres() + " " + administrador.getApellidos());

        // Imagen fija por ahora
        holder.imgItemUsers.setImageResource(R.drawable.usuarios);

        // Los botones editar y eliminar se implementarán luego
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegistrarseActivity.class);
            intent.putExtra("AdminEditar", administrador); // enviar el objeto
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar Administrador")
                    .setMessage("¿Seguro que deseas eliminar a " + administrador.getNombres() + "?")
                    .setPositiveButton("Sí", (dialog, which) -> {

                        AdministradorNegocio negocio = new AdministradorNegocio(context);
                        String respuesta = negocio.eliminarAdministrador(administrador.getId()); // eliminar por ID

                        Toast.makeText(context, respuesta, Toast.LENGTH_SHORT).show();

                        if (respuesta.contains("eliminado")) {
                            listaAdministrador.remove(position); // quitar de la lista
                            notifyItemRemoved(position); // actualizar RecyclerView
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaAdministrador.size();
    }

    public static class AdministradorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemUsers;
        TextView tvUsersNombre;
        ImageButton btnEditar, btnEliminar;

        public AdministradorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemUsers = itemView.findViewById(R.id.imgItemAdmin);
            tvUsersNombre = itemView.findViewById(R.id.tvAdminNombre);
            btnEditar = itemView.findViewById(R.id.btnEditarAdmin);
            btnEliminar = itemView.findViewById(R.id.btnEliminarAdmin);
        }
    }


}
