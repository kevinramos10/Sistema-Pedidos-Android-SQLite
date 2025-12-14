package com.example.examfinal.cliente;

import android.content.Intent;
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
import com.example.examfinal.adapter.CarritoTemp;
import com.example.examfinal.adapter.ItemClienteCarritoA;
import com.example.examfinal.dao.ProductoDAO;
import com.example.examfinal.modelo.Producto;
import com.example.examfinal.negocio.ProductoNegocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteCarritoActivity extends AppCompatActivity {

    RecyclerView recyclerClientesCarrito;
    ProductoDAO productoDAO;
    List<Producto> listaProductos = new ArrayList<>();
    TextView txtTotal, tvContinuar, btnvolver;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_carrito);

        recyclerClientesCarrito = findViewById(R.id.recyclerClientesCarrito);
        productoDAO = new ProductoDAO(this);

        txtTotal = findViewById(R.id.txtClienteCarritoTotal);

        tvContinuar = findViewById(R.id.tvClientesCarritoContinuar);

        btnvolver = findViewById(R.id.tvClientesCarritoVolver);

        cargarCarrito();

        tvContinuar.setOnClickListener(v -> {
            Intent intent = new Intent(ClienteCarritoActivity.this, ClienteSeleccionarDestinoActivity.class);
            startActivity(intent);
        });

        btnvolver.setOnClickListener(v -> {
            finish(); // Regresa a la pantalla anterior
        });



    }


    private void cargarCarrito() {

        ProductoNegocio productoNegocio = new ProductoNegocio(this);

        listaProductos.clear();

        Map<Integer, Integer> items = CarritoTemp.getInstancia().obtenerTodo();

        for (Integer idProducto : items.keySet()) {

            Producto p = productoNegocio.buscarPorId(idProducto);

            if (p != null) {
                listaProductos.add(p);
            }
        }

        ItemClienteCarritoA adapter =
                new ItemClienteCarritoA(this, listaProductos, this::calcularTotal);

        recyclerClientesCarrito.setLayoutManager(new LinearLayoutManager(this));
        recyclerClientesCarrito.setAdapter(adapter);

        calcularTotal(); // ← Actualiza el total al abrir pantalla

    }

    private void calcularTotal() {
        double suma = 0;

        for (Producto p : listaProductos) {
            int cantidad = CarritoTemp.getInstancia().obtenerCantidad(p.getId());
            suma += cantidad * p.getPrecio();
        }

        txtTotal.setText("Total: S/" + String.format("%.2f", suma));
    }




}
