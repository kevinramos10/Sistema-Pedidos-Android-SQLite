package com.example.examfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examfinal.data.Conexion;
import com.example.examfinal.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Conexion con;
    private SQLiteDatabase db;

    public ProductoDAO(Context context) {
        con = new Conexion(context);
    }

    //Abrir DB
    public void abrir(){
        db = con.getWritableDatabase();
    }

    //Cerrar DB
    public void cerrar(){
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    // Insertar Producto
    public String insertarProducto(Producto producto) {
        String mensaje;
        try {
            ContentValues valores = new ContentValues();
            valores.put("nombre", producto.getNombre());
            valores.put("descripcion", producto.getDescripcion());
            valores.put("id_categoria", producto.getIdCategoria());
            valores.put("precio", producto.getPrecio());
            valores.put("stock", producto.getStock());
            valores.put("foto", producto.getFoto());

            long resultado = db.insert("producto", null, valores);

            if (resultado == -1) {
                mensaje = "Error al registrar el producto";
            } else {
                mensaje = "Producto registrado correctamente";
            }
        } catch (Exception e) {
            mensaje = "Error: " + e.getMessage();
        }

        return mensaje;
    }


    //Listar productos
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM producto", null);

            if (cursor.moveToFirst()) {
                do {
                    Producto p = new Producto();
                    p.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    p.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                    p.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")));
                    p.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("precio")));
                    p.setStock(cursor.getInt(cursor.getColumnIndexOrThrow("stock")));
                    p.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
                    lista.add(p);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return lista;
    }

    public List<Producto> listarPorCategoria(int idCategoria) {
        List<Producto> lista = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM producto WHERE id_categoria = ?",
                    new String[]{String.valueOf(idCategoria)});

            if (cursor.moveToFirst()) {
                do {
                    Producto p = new Producto();
                    p.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    p.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                    p.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")));
                    p.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("precio")));
                    p.setStock(cursor.getInt(cursor.getColumnIndexOrThrow("stock")));
                    p.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
                    lista.add(p);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return lista;
    }

    // Actualizar Producto
    public String actualizarProducto(Producto producto) {
        String mensaje;

        try {
            ContentValues valores = new ContentValues();
            valores.put("nombre", producto.getNombre());
            valores.put("descripcion", producto.getDescripcion());
            valores.put("id_categoria", producto.getIdCategoria());
            valores.put("precio", producto.getPrecio());
            valores.put("stock", producto.getStock());
            valores.put("foto", producto.getFoto());

            int resultado = db.update(
                    "producto",
                    valores,
                    "id = ?",
                    new String[]{String.valueOf(producto.getId())}
            );

            if (resultado > 0) {
                mensaje = "Producto actualizado correctamente";
            } else {
                mensaje = "Error al actualizar el producto";
            }

        } catch (Exception e) {
            mensaje = "Error: " + e.getMessage();
        }

        return mensaje;
    }

    // Eliminar Producto
    public String eliminarProducto(int id) {
        String mensaje;

        try {
            int resultado = db.delete(
                    "producto",
                    "id = ?",
                    new String[]{String.valueOf(id)}
            );

            if (resultado > 0) {
                mensaje = "Producto eliminado correctamente";
            } else {
                mensaje = "No se pudo eliminar el producto";
            }

        } catch (Exception e) {
            mensaje = "Error: " + e.getMessage();
        }

        return mensaje;
    }

    public Producto buscarPorId(int idProducto) {
        Producto p = null;
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT * FROM producto WHERE id = ?",
                    new String[]{String.valueOf(idProducto)}
            );

            if (cursor.moveToFirst()) {
                p = new Producto();
                p.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                p.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                p.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow("id_categoria")));
                p.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("precio")));
                p.setStock(cursor.getInt(cursor.getColumnIndexOrThrow("stock")));
                p.setFoto(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return p;
    }

    // Disminuir stock
    public boolean disminuirStock(int idProducto, int cantidad) {
        try {
            db.execSQL(
                    "UPDATE producto SET stock = stock - ? WHERE id = ?",
                    new Object[]{cantidad, idProducto}
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
