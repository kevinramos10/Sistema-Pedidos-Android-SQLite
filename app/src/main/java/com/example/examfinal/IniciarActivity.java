package com.example.examfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.examfinal.admin.AdminMenuActivity;
import com.example.examfinal.cliente.ClienteMenuActivity;
import com.example.examfinal.dao.AdministradorDAO;
import com.example.examfinal.data.Conexion;
import com.example.examfinal.modelo.Administrador;
import com.example.examfinal.modelo.Cliente;
import com.example.examfinal.negocio.AdministradorNegocio;
import com.example.examfinal.negocio.ClienteNegocio;

public class IniciarActivity extends AppCompatActivity {

    EditText etUsuario, etPassword;
    Button btnIngresar, btnRegistrarse;
    Conexion db;

    @Override
    protected void onResume() {
        super.onResume();
        etUsuario.setText("");
        etPassword.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Insertar admin de prueba si no existe
        AdministradorDAO dao = new AdministradorDAO(this);
        dao.insertarAdminPrueba();

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Botón Registrarse
        btnRegistrarse.setOnClickListener(v -> {
            Intent x = new Intent(IniciarActivity.this, RegistrarseActivity.class);
            startActivity(x);
        });

        // Botón Ingresar
        btnIngresar.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString();
            String clave = etPassword.getText().toString();

            if(usuario.isEmpty() || clave.isEmpty()){
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            AdministradorNegocio adminNegocio = new AdministradorNegocio(IniciarActivity.this);
            Administrador admin = adminNegocio.validarLogin(usuario,clave);

            if(admin != null){
                Intent intent = new Intent(IniciarActivity.this, AdminMenuActivity.class);
                intent.putExtra("administrador", admin.getNombres());
                startActivity(intent);
                return;
            }

            ClienteNegocio clienteNegocio = new ClienteNegocio(IniciarActivity.this);
            Cliente cli = clienteNegocio.validarLogin(usuario,clave);

            if (cli != null) {

                // GUARDAR EL ID DEL CLIENTE EN SESIÓN
                SharedPreferences prefs = getSharedPreferences("datos_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("cliente_id", cli.getId());
                editor.apply();

                // Ir al menú del cliente
                Intent intent = new Intent(IniciarActivity.this, ClienteMenuActivity.class);
                intent.putExtra("cliente", cli.getNombres());
                startActivity(intent);
                return;
            }
            else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });




    }


}