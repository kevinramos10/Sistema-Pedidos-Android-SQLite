package com.example.examfinal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.examfinal.modelo.Administrador;
import com.example.examfinal.modelo.Cliente;
import com.example.examfinal.negocio.AdministradorNegocio;
import com.example.examfinal.negocio.ClienteNegocio;

import org.w3c.dom.Text;

import java.util.Calendar;

public class RegistrarseActivity extends AppCompatActivity {

    EditText etDni, etNombres, etApellidos, etFechaNacimiento, etUsuario, etContrasena;
    Button btnRegistrar;
    TextView btnvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrarse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enlazar con XML
        etDni = findViewById(R.id.etRegistrarseDNI);
        etNombres = findViewById(R.id.etRegistrarseNombre);
        etApellidos = findViewById(R.id.etRegistrarseApellido);
        etFechaNacimiento = findViewById(R.id.etRegistrarseFecha);
        etUsuario = findViewById(R.id.etRegistrarseUsuario);
        etContrasena = findViewById(R.id.etRegistrarseContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrarseRegistro);

        btnvolver = findViewById(R.id.tvRegistrarseVolver);

        // Instancia del negocio
        ClienteNegocio negocio = new ClienteNegocio(this);

        //  Evento para abrir el DatePicker al tocar el EditText
        etFechaNacimiento.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, month1, dayOfMonth) -> {
                        String fecha = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        etFechaNacimiento.setText(fecha);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
        String modo = getIntent().getStringExtra("modo");

        Cliente clienteEditar = (Cliente) getIntent().getSerializableExtra("clienteEditar");
        Administrador adminEditar = (Administrador) getIntent().getSerializableExtra("AdminEditar");

        if (clienteEditar != null) {
            // Estamos en modo EDICIÓN
            etDni.setText(clienteEditar.getDni());
            etNombres.setText(clienteEditar.getNombres());
            etApellidos.setText(clienteEditar.getApellidos());
            etFechaNacimiento.setText(clienteEditar.getFechaNacimiento());
            etUsuario.setText(clienteEditar.getUsuario());
            etContrasena.setText(clienteEditar.getContrasena());

            btnRegistrar.setText("Actualizar"); // cambiar texto del botón

        }else if(adminEditar != null){
            // Modo edicion admin
            etDni.setText(adminEditar.getDni());
            etNombres.setText(adminEditar.getNombres());
            etApellidos.setText(adminEditar.getApellidos());
            etFechaNacimiento.setText(adminEditar.getFechaNacimiento());
            etUsuario.setText(adminEditar.getUsuario());
            etContrasena.setText(adminEditar.getContrasena());
            btnRegistrar.setText("Actualizar");

        }

        btnRegistrar.setOnClickListener(v -> {

            // Edita cliente
            if (clienteEditar != null) {
                Cliente cli = new Cliente();
                cli.setId(clienteEditar.getId());
                cli.setDni(etDni.getText().toString());
                cli.setNombres(etNombres.getText().toString());
                cli.setApellidos(etApellidos.getText().toString());
                cli.setFechaNacimiento(etFechaNacimiento.getText().toString());
                cli.setUsuario(etUsuario.getText().toString());
                cli.setContrasena(etContrasena.getText().toString());

                String r = negocio.registrarCliente(cli);
                Toast.makeText(this, r, Toast.LENGTH_LONG).show();
                if (r.contains("correctamente")) finish();
                return;
            }

            // Edita administrador
            if (adminEditar != null) {

                Administrador admin = new Administrador();
                admin.setId(adminEditar.getId());
                admin.setDni(etDni.getText().toString());
                admin.setNombres(etNombres.getText().toString());
                admin.setApellidos(etApellidos.getText().toString());
                admin.setFechaNacimiento(etFechaNacimiento.getText().toString());
                admin.setUsuario(etUsuario.getText().toString());
                admin.setContrasena(etContrasena.getText().toString());

                AdministradorNegocio adminNegocio = new AdministradorNegocio(this);
                String r = adminNegocio.actualizarAdministrador(admin);

                Toast.makeText(this, r, Toast.LENGTH_LONG).show();
                if (r.contains("correctamente")) finish();
                return;
            }

            // Si es admin nuevo
            if ("admin_nuevo".equals(modo)) {

                Administrador nuevo = new Administrador();
                nuevo.setDni(etDni.getText().toString());
                nuevo.setNombres(etNombres.getText().toString());
                nuevo.setApellidos(etApellidos.getText().toString());
                nuevo.setFechaNacimiento(etFechaNacimiento.getText().toString());
                nuevo.setUsuario(etUsuario.getText().toString());
                nuevo.setContrasena(etContrasena.getText().toString());

                AdministradorNegocio adminNeg = new AdministradorNegocio(this);
                String r = adminNeg.registrarAdministrador(nuevo);

                Toast.makeText(this, r, Toast.LENGTH_LONG).show();

                if (r.contains("correctamente")) finish();
                return;
            }

            // Si es cliente nuevo
            Cliente cli = new Cliente();
            cli.setDni(etDni.getText().toString());
            cli.setNombres(etNombres.getText().toString());
            cli.setApellidos(etApellidos.getText().toString());
            cli.setFechaNacimiento(etFechaNacimiento.getText().toString());
            cli.setUsuario(etUsuario.getText().toString());
            cli.setContrasena(etContrasena.getText().toString());

            String r = negocio.registrarCliente(cli);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();
            if (r.contains("correctamente")) finish();

        });

        btnvolver.setOnClickListener(v -> {
            finish();
        });
    }
}
