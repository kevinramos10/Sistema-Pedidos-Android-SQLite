package com.example.examfinal.cliente;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.adapter.ItemAdminVerProductosA;
import com.example.examfinal.adapter.ItemClientePlatillos;
import com.example.examfinal.modelo.Producto;
import com.example.examfinal.negocio.ProductoNegocio;

import java.util.List;

public class ClientePlatillosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemClientePlatillos adapter;
    private TextView tvVolver;
    private ProductoNegocio PN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_platillos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerClientesPlatillos);
        tvVolver = findViewById(R.id.tvAdminPlatillosVolver);
        PN = new ProductoNegocio(this);

        List<Producto> listaPlatillos = PN.listarPorCategoria(1);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas

        adapter = new ItemClientePlatillos(this, listaPlatillos);
        recyclerView.setAdapter(adapter);
        tvVolver.setOnClickListener(v -> finish());
    }
}