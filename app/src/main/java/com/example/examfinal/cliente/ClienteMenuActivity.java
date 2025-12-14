package com.example.examfinal.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.examfinal.IniciarActivity;
import com.example.examfinal.R;
import com.example.examfinal.admin.AdminMenuActivity;
import com.example.examfinal.admin.AdminVerCartaActivity;
import com.example.examfinal.admin.AdminVerPlatilloActivity;

public class ClienteMenuActivity extends AppCompatActivity {

    private ImageView imgClientePlatillo, imgClienteBebidas, imgClientePostres, imgClienteAdicionales;
    private TextView txtMenuClientePlatillos, txtMenuClienteBebidas, txtMenuClientePostres, txtMenuClienteAdicionales, btnCerrarSesion;

    private Button btnVerCarrito, btnVerCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgClientePlatillo = findViewById(R.id.imgPlatillo);
        imgClienteBebidas = findViewById(R.id.imgBebida);
        imgClientePostres = findViewById(R.id.imgPostre);
        imgClienteAdicionales = findViewById(R.id.imgAdicionales);
        txtMenuClientePlatillos = findViewById(R.id.txtMenuPlatillos);
        txtMenuClienteBebidas = findViewById(R.id.txtMenuBebidas);
        txtMenuClientePostres = findViewById(R.id.txtMenuPostres);
        txtMenuClienteAdicionales = findViewById(R.id.txtMenuAdicionales);

        btnVerCompras  = findViewById(R.id.btnVerClienteCompras);

        btnVerCarrito = findViewById(R.id.btnVerClienteCarrito);

        btnCerrarSesion = findViewById(R.id.tvClientesMenuVolver);


        //---

        // img Platillo
        imgClientePlatillo.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClientePlatillosActivity.class);
            startActivity(x);
        });
        // txt Platillo
        txtMenuClientePlatillos.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClientePlatillosActivity.class);
            startActivity(x);
        });

        // img Bebidas
        imgClienteBebidas.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClienteBebidasActivity.class);
            startActivity(x);
        });
        // txt Bebidas
        txtMenuClienteBebidas.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClienteBebidasActivity.class);
            startActivity(x);
        });

        // img Bebidas
        imgClientePostres.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClientePostresActivity.class);
            startActivity(x);
        });
        // txt Bebidas
        txtMenuClientePostres.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClientePostresActivity.class);
            startActivity(x);
        });

        // img Bebidas
        imgClienteAdicionales.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClienteAdicionalesActivity.class);
            startActivity(x);
        });
        // txt Bebidas
        txtMenuClienteAdicionales.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClienteAdicionalesActivity.class);
            startActivity(x);
        });

        // Botón Ver carrito
        btnVerCarrito.setOnClickListener(v -> {
            Intent x = new Intent(ClienteMenuActivity.this, ClienteCarritoActivity.class);
            startActivity(x);
        });

        // Boton Ver compras
        btnVerCompras.setOnClickListener(v -> {
            Intent intent = new Intent(ClienteMenuActivity.this, ClienteVerComprasActivity.class);
            startActivity(intent);
        });



        btnCerrarSesion.setOnClickListener(v -> {

            // 1. BORRAR SESIÓN
            SharedPreferences prefs = getSharedPreferences("datos_session", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // borra cliente_id
            editor.apply();

            // 2. IR AL LOGIN
            Intent intent = new Intent(ClienteMenuActivity.this, IniciarActivity.class);

            // 3. LIMPIAR LA PILA DE ACTIVIDADES
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        });



    }
}