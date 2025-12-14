package com.example.examfinal.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.examfinal.IniciarActivity;
import com.example.examfinal.R;
import com.example.examfinal.RegistrarseActivity;

public class AdminMenuActivity extends AppCompatActivity {

    Button btnVCarta, btnVClientes, btnVAdministadores;

    TextView btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVCarta = findViewById(R.id.btnAdminVerCarta);
        btnVClientes = findViewById(R.id.btnAdminVerClientes);
        btnVAdministadores = findViewById(R.id.btnAdminVerAdministradores);
        btnCerrarSesion = findViewById(R.id.tvAdminMenuCS);


        // Botón VerCarta
        btnVCarta.setOnClickListener(v -> {
            Intent x = new Intent(AdminMenuActivity.this, AdminVerCartaActivity.class);
            startActivity(x);
        });

        // Botón VerClientes
        btnVClientes.setOnClickListener(v -> {
            Intent x = new Intent(AdminMenuActivity.this, AdminVerClientesActivity.class);
            startActivity(x);
        });

        // Botón Ver Administadores
        btnVAdministadores.setOnClickListener(v -> {
            Intent x = new Intent(AdminMenuActivity.this, AdminVerAdministradoresActivity.class);
            startActivity(x);
        });

        // Botón CerrarSesion
        btnCerrarSesion.setOnClickListener(v -> {
            finish();
        });


    }
}