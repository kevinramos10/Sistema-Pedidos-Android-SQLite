package com.example.examfinal.admin;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.examfinal.R;
import com.example.examfinal.modelo.Producto;
import com.example.examfinal.negocio.ProductoNegocio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AdminAgregarProductoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etAgregarProductoNombre, etAgregarProductoDescripcion, etAgregarProductoStock, etAgregarProductoPrecio;
    private RadioButton rbPlatillos, rbBebidas, rbPostres, rbAdicionales;
    private ImageView ivAgregarProductoFoto;
    private Button btnAgregarProductoImagen, btnAgregarProductoConfirmar;
    private Uri imageUri;
    private String rutaLocalImagen;

    private ProductoNegocio negocio;

    private Producto productoEditar = null;
    private TextView btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_agregar_producto);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ENLAZAR VISTAS
        etAgregarProductoNombre = findViewById(R.id.etAgregarProductoNombre);
        etAgregarProductoDescripcion = findViewById(R.id.etAgregarProductoDescripcion);
        etAgregarProductoStock = findViewById(R.id.etAgregarProductoStock);
        etAgregarProductoPrecio = findViewById(R.id.etAgregarProductoPrecio);
        rbPlatillos = findViewById(R.id.rbPlatillos);
        rbBebidas = findViewById(R.id.rbBebidas);
        rbPostres = findViewById(R.id.rbPostres);
        rbAdicionales = findViewById(R.id.rbAdocionales);
        btnAgregarProductoImagen = findViewById(R.id.btnAgregarProductoImagen);
        btnAgregarProductoConfirmar = findViewById(R.id.btnAgregarProductoConfirmar);
        ivAgregarProductoFoto = findViewById(R.id.ivAgregarProductoFoto);
        btnCerrarSesion = findViewById(R.id.tvAgregarProductoVolver);

        negocio = new ProductoNegocio(this);

        //RECIBIR PRODUCTO PARA EDITAR
        productoEditar = (Producto) getIntent().getSerializableExtra("ProductoEditar");

        if (productoEditar != null) {
            cargarDatosEdicion();
        }

        //SELECCIONAR IMAGEN
        btnAgregarProductoImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        //GUARDAR PRODUCTO
        btnAgregarProductoConfirmar.setOnClickListener(v -> guardarProducto());

        btnCerrarSesion.setOnClickListener(v -> finish());
    }

    private void cargarDatosEdicion() {
        etAgregarProductoNombre.setText(productoEditar.getNombre());
        etAgregarProductoDescripcion.setText(productoEditar.getDescripcion());
        etAgregarProductoPrecio.setText(String.valueOf(productoEditar.getPrecio()));
        etAgregarProductoStock.setText(String.valueOf(productoEditar.getStock()));

        switch (productoEditar.getIdCategoria()) {
            case 1: rbPlatillos.setChecked(true); break;
            case 2: rbBebidas.setChecked(true); break;
            case 3: rbPostres.setChecked(true); break;
            case 4: rbAdicionales.setChecked(true); break;
        }

        // Imagen local guardada
        if (productoEditar.getFoto() != null) {
            File file = new File(productoEditar.getFoto());
            if (file.exists()) {
                ivAgregarProductoFoto.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                rutaLocalImagen = productoEditar.getFoto();
            }
        }

        btnAgregarProductoConfirmar.setText("Actualizar Producto");
    }

    private void guardarProducto() {

        String nombre = etAgregarProductoNombre.getText().toString().trim();
        String descripcion = etAgregarProductoDescripcion.getText().toString().trim();
        String precioStr = etAgregarProductoPrecio.getText().toString().trim();
        String stockStr = etAgregarProductoStock.getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int idCategoria = 0;
        if (rbPlatillos.isChecked()) idCategoria = 1;
        else if (rbBebidas.isChecked()) idCategoria = 2;
        else if (rbPostres.isChecked()) idCategoria = 3;
        else if (rbAdicionales.isChecked()) idCategoria = 4;

        if (idCategoria == 0) {
            Toast.makeText(this, "Seleccione una categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);
        int stock = Integer.parseInt(stockStr);

        //MODO EDITAR
        if (productoEditar != null) {
            productoEditar.setNombre(nombre);
            productoEditar.setDescripcion(descripcion);
            productoEditar.setPrecio(precio);
            productoEditar.setStock(stock);
            productoEditar.setIdCategoria(idCategoria);

            // si no eligió nueva imagen, usar la existente
            productoEditar.setFoto(rutaLocalImagen);

            String mensaje = negocio.actualizarProducto(productoEditar);
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

            if (mensaje.contains("correctamente")) finish();

            return;
        }

        //MODO REGISTRAR NUEVO
        Producto nuevo = new Producto();
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(descripcion);
        nuevo.setIdCategoria(idCategoria);
        nuevo.setPrecio(precio);
        nuevo.setStock(stock);
        nuevo.setFoto(rutaLocalImagen);

        String mensaje = negocio.registrarProducto(nuevo);
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        if (mensaje.equals("Producto registrado correctamente")) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivAgregarProductoFoto.setImageURI(imageUri);

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                File file = new File(getFilesDir(), "producto_" + System.currentTimeMillis() + ".jpg");
                OutputStream outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.close();
                inputStream.close();

                rutaLocalImagen = file.getAbsolutePath();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
