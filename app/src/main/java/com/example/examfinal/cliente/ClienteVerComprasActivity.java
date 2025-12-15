package com.example.examfinal.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.adapter.ItemClienteVerCompras;
import com.example.examfinal.modelo.Compra;
import com.example.examfinal.negocio.NegocioCompra;

import java.util.List;

public class ClienteVerComprasActivity extends AppCompatActivity {

    RecyclerView recyclerCompras;
    ItemClienteVerCompras adapter;
    List<Compra> listaCompras;
    NegocioCompra negocioCompra;

    TextView btnvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_ver_compras);

        btnvolver = findViewById(R.id.tvVerClientesVerComprasVolver);

        recyclerCompras = findViewById(R.id.recyclerClienteVerCompras);
        recyclerCompras.setLayoutManager(new LinearLayoutManager(this));

        negocioCompra = new NegocioCompra(this);

        // Obtener ID del cliente logueado
        SharedPreferences prefs = getSharedPreferences("datos_session", MODE_PRIVATE);
        int clienteId = prefs.getInt("cliente_id", -1);

        // Obtener historial del cliente
        listaCompras = negocioCompra.listarPorCliente(clienteId);

        adapter = new ItemClienteVerCompras(
                this,
                listaCompras,
                compra -> {
                    Intent i = new Intent(ClienteVerComprasActivity.this, ClienteDetalleCompraActivity.class);
                    i.putExtra("id_compra", compra.getId());
                    startActivity(i);
                }
        );


        recyclerCompras.setAdapter(adapter);

        btnvolver.setOnClickListener(v -> {
            finish();
        });
    }
}
