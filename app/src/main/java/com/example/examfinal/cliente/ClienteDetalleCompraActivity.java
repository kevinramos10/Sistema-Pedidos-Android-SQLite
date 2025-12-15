package com.example.examfinal.cliente;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.adapter.AdapterDetalleCompra;
import com.example.examfinal.dao.DetalleCompraDAO;
import com.example.examfinal.modelo.DetalleCompra;

import java.util.List;

public class ClienteDetalleCompraActivity extends AppCompatActivity {

    RecyclerView recycler;
    AdapterDetalleCompra adapter;
    int idCompra;
    TextView txtTotal, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle_compra);

        recycler = findViewById(R.id.recyclerDetalleCompra);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        txtTotal = findViewById(R.id.tvTotalGeneral);

        idCompra = getIntent().getIntExtra("id_compra", -1);

        btnVolver = findViewById(R.id.tvVerClientesVerDetalleCompraVolver);

        //Obtener detalles desde BD
        DetalleCompraDAO dao = new DetalleCompraDAO(this);
        dao.abrir();
        List<DetalleCompra> lista = dao.listarPorCompra(idCompra);
        dao.cerrar();

        //Mostrar lista
        adapter = new AdapterDetalleCompra(this, lista);
        recycler.setAdapter(adapter);

        //Calcular total
        double total = 0;
        for (DetalleCompra d : lista) {
            total += d.getSubtotal();
        }

        txtTotal.setText("Total: S/ " + String.format("%.2f", total));

        btnVolver.setOnClickListener(v -> {
            finish();
        });
    }
}
