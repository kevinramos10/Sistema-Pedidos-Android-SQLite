package com.example.examfinal.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examfinal.R;
import com.example.examfinal.RegistrarseActivity;
import com.example.examfinal.adapter.ItemAdminVerAdministradoresA;
import com.example.examfinal.adapter.ItemAdminVerClientesA;
import com.example.examfinal.modelo.Administrador;
import com.example.examfinal.modelo.Cliente;
import com.example.examfinal.negocio.AdministradorNegocio;
import com.example.examfinal.negocio.ClienteNegocio;

import java.util.List;

public class AdminVerAdministradoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdminVerAdministradoresA adapter;

    private TextView tvVolver;

    private Button btnAgregar;

    private AdministradorNegocio AN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_ver_administradores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerAdminVerAdministradores);
        btnAgregar = findViewById(R.id.btnAdminCrearAdmin);
        tvVolver = findViewById(R.id.tvVerAdministradoresVolver);

        // Inicializar negocio
        AN = new AdministradorNegocio(this);

        // obtener usuarios desde BD
        List<Administrador> listaAdministrador = AN.listarAdministrador();

        // configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdminVerAdministradoresA(this, listaAdministrador);
        recyclerView.setAdapter(adapter);

        // Agregar admin
        btnAgregar.setOnClickListener(v -> {
            Intent x = new Intent(AdminVerAdministradoresActivity.this, RegistrarseActivity.class);
            x.putExtra("modo", "admin_nuevo"); // IMPORTANTE
            startActivity(x);
        });

        // volver
        tvVolver.setOnClickListener(v -> finish());


    }
}