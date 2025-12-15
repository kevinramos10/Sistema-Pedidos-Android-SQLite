package com.example.examfinal.cliente;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.examfinal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ClienteSeleccionarDestinoActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng destinoSeleccionado;
    private Button btnAceptarDestino;

    // Coordenadas aproximadas del restaurante
    private final LatLng WASI_MIKUY = new LatLng(-11.79490, -77.15984);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_seleccionar_destino);

        btnAceptarDestino = findViewById(R.id.btnAceptarDestino);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapDestino);

        mapFragment.getMapAsync(this);

        btnAceptarDestino.setOnClickListener(v -> {
            if (destinoSeleccionado == null) {
                Toast.makeText(this, "Seleccione un destino en el mapa", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(
                    this,
                    "Destino elegido",
                    Toast.LENGTH_SHORT
            ).show();

            Intent i = new Intent(ClienteSeleccionarDestinoActivity.this, ClienteMetodoPagoActivity.class);
            i.putExtra("latitud", destinoSeleccionado.latitude);
            i.putExtra("longitud", destinoSeleccionado.longitude);

            startActivity(i);

        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Mostrar el restaurante Wasi Mikuy
        mMap.addMarker(new MarkerOptions()
                .position(WASI_MIKUY)
                .title("Wasi Mikuy")
                .snippet("Mikuy Wasi, Acceso a Sta. Rosa 262, Santa Rosa 15123")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(WASI_MIKUY, 15));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        destinoSeleccionado = latLng;

        // Eliminar otros marcadores excepto el de Wasi Mikuy
        mMap.clear();

        // Volver a poner el restaurante
        mMap.addMarker(new MarkerOptions()
                .position(WASI_MIKUY)
                .title("Wasi Mikuy"));

        // Poner el destino seleccionado por el cliente
        mMap.addMarker(new MarkerOptions()
                .position(destinoSeleccionado)
                .title("Mi destino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

    }
}
