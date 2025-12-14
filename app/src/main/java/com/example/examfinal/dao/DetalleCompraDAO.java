package com.example.examfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examfinal.data.Conexion;
import com.example.examfinal.modelo.DetalleCompra;

import java.util.ArrayList;

public class DetalleCompraDAO {

    private Conexion con;
    private SQLiteDatabase db;

    public DetalleCompraDAO(Context context){
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

    public long insertar(DetalleCompra det) {
        ContentValues values = new ContentValues();
        values.put("id_compra", det.getIdCompra());          // nombre correcto
        values.put("id_producto", det.getIdProducto());      // nombre correcto
        values.put("cantidad", det.getCantidad());           // OK
        values.put("precioUnitario", det.getPrecioUnitario()); // nombre correcto
        values.put("subtotal", det.getSubtotal());           // OK

        return db.insert("detalle_compra", null, values);
    }


    // Listar detalles por compra
    public ArrayList<DetalleCompra> listarPorCompra(int compraId){
        ArrayList<DetalleCompra> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM detalle_compra WHERE id_compra = ?",
                new String[]{ String.valueOf(compraId) }
        );

        if(cursor.moveToFirst()){
            do{
                DetalleCompra d = new DetalleCompra();
                d.setId(cursor.getInt(0));
                d.setIdCompra(cursor.getInt(1));
                d.setIdProducto(cursor.getInt(2));
                d.setCantidad(cursor.getInt(3));
                d.setSubtotal(cursor.getDouble(4));

                lista.add(d);

            } while(cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}
