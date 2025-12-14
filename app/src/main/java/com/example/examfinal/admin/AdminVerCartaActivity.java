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

import com.example.examfinal.R;

public class AdminVerCartaActivity extends AppCompatActivity {

    Button btnVPlatillos, btnVBebidas ,btnVPostres ,btnVAdicionales, btnAgregarP;

    TextView txvVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_ver_carta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVPlatillos = findViewById(R.id.btnAdminVerPlatillos);
        btnVBebidas = findViewById(R.id.btnAdminVerBebidas);
        btnVPostres = findViewById(R.id.btnAdminVerPostres);
        btnVAdicionales = findViewById(R.id.btnAdminVerAdicionales);
        btnAgregarP = findViewById(R.id.btnAdminAgregarProducto);
        txvVolver = findViewById(R.id.tvAdminCartaCS);


        // Botón Platillos
        btnVPlatillos.setOnClickListener(v -> {
            Intent x = new Intent(AdminVerCartaActivity.this, AdminVerPlatilloActivity.class);
            startActivity(x);
        });
        // Botón Bebidas
        btnVBebidas.setOnClickListener(v -> {
            Intent x = new Intent(AdminVerCartaActivity.this, AdminVerBebidasActivity.class);
            startActivity(x);
        });
        // Botón Postres
        btnVPostres.setOnClickListener(v -> {
            Intent x = new Intent(AdminVerCartaActivity.this, AdminVerPostresActivity.class);
            startActivity(x);
        });
        // Botón Adicionales
        btnVAdicionales.setOnClickListener(v -> {
            Intent x = new Intent(AdminVerCartaActivity.this, AdminVerAdicionalesActivity.class);
            startActivity(x);
        });

        // Botón Agregar productos
        btnAgregarP.setOnClickListener(v -> {
            Intent x = new Intent(AdminVerCartaActivity.this, AdminAgregarProductoActivity.class);
            startActivity(x);
        });

        // Botón Volver
        txvVolver.setOnClickListener(v -> {
            finish();
        });
    }
}