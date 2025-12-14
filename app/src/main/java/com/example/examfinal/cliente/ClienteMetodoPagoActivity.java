package com.example.examfinal.cliente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.examfinal.R;
import com.example.examfinal.adapter.CarritoTemp;
import com.example.examfinal.dao.ProductoDAO;
import com.example.examfinal.modelo.CarritoItem;
import com.example.examfinal.modelo.Compra;
import com.example.examfinal.modelo.DetalleCompra;
import com.example.examfinal.modelo.Producto;
import com.example.examfinal.negocio.NegocioCompra;
import com.example.examfinal.negocio.NegocioDetalleCompra;
import com.example.examfinal.negocio.ProductoNegocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.google.android.material.card.MaterialCardView;

public class ClienteMetodoPagoActivity extends AppCompatActivity {

    Button btnFinalizarPedido;
    ImageView imgQrYape, imgQrPlin;


    MaterialCardView cardYape;
    MaterialCardView cardPlin;

    private double latitudDestino;
    private double longitudDestino;

    private double totalCompra = 0;

    // 👉 Nuevo: para guardar qué método se seleccionó
    private String metodoSeleccionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_metodo_pago);

        latitudDestino = getIntent().getDoubleExtra("latitud", 0);
        longitudDestino = getIntent().getDoubleExtra("longitud", 0);

        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);
        imgQrYape = findViewById(R.id.imgQrYape);
        imgQrPlin = findViewById(R.id.imgQrPlin);

        // 🔥 Inicializar correctamente MaterialCardView
        cardYape = findViewById(R.id.cardYape);
        cardPlin = findViewById(R.id.cardPlin);

        mostrarQRsFijos();

        // 👉 Detectar selección de método
        imgQrYape.setOnClickListener(v -> {
            metodoSeleccionado = "Yape";
            actualizarSeleccion();
            Toast.makeText(this, "Método seleccionado: Yape", Toast.LENGTH_SHORT).show();
        });

        imgQrPlin.setOnClickListener(v -> {
            metodoSeleccionado = "Plin";
            actualizarSeleccion();
            Toast.makeText(this, "Método seleccionado: Plin", Toast.LENGTH_SHORT).show();
        });

        // 👉 Confirmación antes de pagar
        btnFinalizarPedido.setOnClickListener(v -> {

            if (metodoSeleccionado.isEmpty()) {
                Toast.makeText(this, "Seleccione Yape o Plin antes de continuar", Toast.LENGTH_SHORT).show();
                return;
            }

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Confirmar pago")
                    .setMessage("¿Confirma que ya realizó el pago con " + metodoSeleccionado + "?")
                    .setPositiveButton("Sí", (dialog, which) -> registrarCompra())
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void mostrarQRsFijos() {
        imgQrYape.setImageResource(R.drawable.yape);
        imgQrPlin.setImageResource(R.drawable.plin);
    }

    private void registrarCompra() {
        SharedPreferences prefs = getSharedPreferences("datos_session", MODE_PRIVATE);
        int clienteId = prefs.getInt("cliente_id", -1);

        if (clienteId == -1) {
            Toast.makeText(this, "Error con la sesión.", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<CarritoItem> carrito = obtenerCarritoDetalles();
        if (carrito.isEmpty()) {
            Toast.makeText(this, "Carrito vacío.", Toast.LENGTH_SHORT).show();
            return;
        }

        totalCompra = 0;
        for (CarritoItem c : carrito) {
            totalCompra += c.getSubtotal();
        }

        Compra compra = new Compra();
        compra.setIdCliente(clienteId);
        compra.setTotal(totalCompra);

        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        compra.setFecha(fecha);

        compra.setDestinoLat(latitudDestino);
        compra.setDestinoLng(longitudDestino);

        // 👉 Guardamos YAPE o PLIN según selección
        compra.setMetodoPago(metodoSeleccionado);

        // Puedes poner "Pendiente" o "Pagado" según tu flujo
        compra.setEstado("Pagado");

        NegocioCompra negCompra = new NegocioCompra(this);
        long idCompra = negCompra.insertar(compra);

        if (idCompra == -1) {
            Toast.makeText(this, "Error al registrar compra.", Toast.LENGTH_SHORT).show();
            return;
        }

        NegocioDetalleCompra negDet = new NegocioDetalleCompra(this);
        ProductoNegocio negProducto = new ProductoNegocio(this);

        for (CarritoItem item : carrito) {
            DetalleCompra det = new DetalleCompra();
            det.setIdCompra((int) idCompra);
            det.setIdProducto(item.getProductoId());
            det.setCantidad(item.getCantidad());
            det.setPrecioUnitario(item.getPrecio());
            det.setSubtotal(item.getSubtotal());
            negDet.insertar(det);

            negProducto.disminuirStock(item.getProductoId(), item.getCantidad());
        }

        CarritoTemp.getInstancia().limpiar();

        Toast.makeText(this, "Pedido realizado con éxito", Toast.LENGTH_LONG).show();

        startActivity(new Intent(ClienteMetodoPagoActivity.this, ClienteMenuActivity.class));
        finish();
    }

    private ArrayList<CarritoItem> obtenerCarritoDetalles() {
        ArrayList<CarritoItem> lista = new ArrayList<>();

        Map<Integer, Integer> mapa = CarritoTemp.getInstancia().obtenerTodo();
        ProductoDAO productoDAO = new ProductoDAO(this);
        productoDAO.abrir();

        for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
            int idProducto = entry.getKey();
            int cantidad = entry.getValue();

            Producto prod = productoDAO.buscarPorId(idProducto);
            if (prod != null) {
                lista.add(new CarritoItem(
                        prod.getId(),
                        prod.getNombre(),
                        prod.getPrecio(),
                        cantidad
                ));
            }
        }

        productoDAO.cerrar();
        return lista;
    }

    private void actualizarSeleccion() {

        int bordeSeleccionado = ContextCompat.getColor(this, R.color.borde_morado);

        int bordeNormal = getResources().getColor(android.R.color.transparent);

        if (metodoSeleccionado.equals("Yape")) {
            cardYape.setCardBackgroundColor(Color.parseColor("#FFF3FF"));
            cardYape.setStrokeColor(bordeSeleccionado);
            cardYape.setStrokeWidth(6);

            cardPlin.setCardBackgroundColor(Color.WHITE);
            cardPlin.setStrokeColor(bordeNormal);
            cardPlin.setStrokeWidth(0);

        } else if (metodoSeleccionado.equals("Plin")) {
            cardPlin.setCardBackgroundColor(Color.parseColor("#FFF3FF"));
            cardPlin.setStrokeColor(bordeSeleccionado);
            cardPlin.setStrokeWidth(6);

            cardYape.setCardBackgroundColor(Color.WHITE);
            cardYape.setStrokeColor(bordeNormal);
            cardYape.setStrokeWidth(0);
        }
    }
}
