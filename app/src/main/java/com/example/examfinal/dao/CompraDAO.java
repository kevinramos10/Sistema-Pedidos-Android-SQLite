package com.example.examfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examfinal.data.Conexion;
import com.example.examfinal.modelo.Compra;

import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private Conexion con;
    private SQLiteDatabase db;

    public CompraDAO(Context context){
        con = new Conexion(context);
    }

    public void abrir(){
        db = con.getWritableDatabase();
    }

    public void cerrar(){
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    // Insertar compra
    public long insertar(Compra compra){
        ContentValues values = new ContentValues();
        values.put("id_cliente", compra.getIdCliente());
        values.put("fecha", compra.getFecha());
        values.put("total", compra.getTotal());
        values.put("destinoLat", compra.getDestinoLat());
        values.put("destinoLng", compra.getDestinoLng());
        values.put("metodoPago", compra.getMetodoPago());  // ← CORRECTO
        values.put("estado", compra.getEstado());

        return db.insert("compra", null, values);
    }

    // Listar compras generales
    public ArrayList<Compra> listar(){
        ArrayList<Compra> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM compra", null);

        if(cursor.moveToFirst()){
            do{
                Compra c = new Compra();
                c.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                c.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")));
                c.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
                c.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));

                lista.add(c);

            } while(cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    // listar por cliente
    public List<Compra> listarPorCliente(int idCliente) {
        List<Compra> lista = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT * FROM compra WHERE id_cliente = ? ORDER BY fecha DESC",
                    new String[]{String.valueOf(idCliente)}
            );

            if (cursor.moveToFirst()) {
                do {
                    Compra c = new Compra();

                    c.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    c.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow("id_cliente")));
                    c.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
                    c.setTotal(cursor.getDouble(cursor.getColumnIndexOrThrow("total")));

                    // CAMPOS RESTANTES CORRECTOS
                    c.setDestinoLat(cursor.getDouble(cursor.getColumnIndexOrThrow("destinoLat")));
                    c.setDestinoLng(cursor.getDouble(cursor.getColumnIndexOrThrow("destinoLng")));

                    // ❗ CORREGIDO (ANTES decías metodo_pago)
                    c.setMetodoPago(cursor.getString(cursor.getColumnIndexOrThrow("metodoPago")));

                    c.setEstado(cursor.getString(cursor.getColumnIndexOrThrow("estado")));

                    lista.add(c);

                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return lista;
    }

}
