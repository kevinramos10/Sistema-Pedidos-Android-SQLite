package com.example.examfinal.admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.adapter.ItemAdminVerProductosA;
import com.example.examfinal.modelo.Producto;
import com.example.examfinal.negocio.ProductoNegocio;

import java.util.List;

public class AdminVerAdicionalesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdminVerProductosA adapter;
    private TextView tvVolver;
    private ProductoNegocio PN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_ver_adicionales);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerAdminAdicionales);
        tvVolver = findViewById(R.id.tvAdminAdicionalesVolver);

        PN = new ProductoNegocio(this);

        //Supongamos que la categoría "Platillos" tiene ID = 1
        List<Producto> listaPlatillos = PN.listarPorCategoria(4);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdminVerProductosA(this, listaPlatillos);
        recyclerView.setAdapter(adapter);

        // Acción del botón Volver
        tvVolver.setOnClickListener(v -> finish());


    }
}