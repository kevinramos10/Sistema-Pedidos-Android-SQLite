package com.example.examfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examfinal.data.Conexion;
import com.example.examfinal.modelo.Administrador;
import com.example.examfinal.modelo.Cliente;

import java.util.ArrayList;

public class AdministradorDAO {

    private Conexion con;

    private SQLiteDatabase db;

    public AdministradorDAO(Context context){
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

    //Insertar Admin
    public long insertar(Administrador admin){
        ContentValues values = new ContentValues();
        values.put("dni", admin.getDni());
        values.put("nombres", admin.getNombres());
        values.put("apellidos", admin.getApellidos());
        values.put("fecha_nacimiento", admin.getFechaNacimiento());
        values.put("usuario", admin.getUsuario());
        values.put("contrasena", admin.getContrasena());

        return db.insert("administrador", null, values);
    }

    //Actualizar Admin
    public int actualizar(Administrador admin){
        ContentValues values = new ContentValues();
        values.put("dni", admin.getDni());
        values.put("nombres", admin.getNombres());
        values.put("apellidos", admin.getApellidos());
        values.put("fecha_nacimiento", admin.getFechaNacimiento());
        values.put("usuario", admin.getUsuario());
        values.put("contrasena", admin.getContrasena());

        return db.update("administrador", values, "id = ?", new String[]{String.valueOf(admin.getId())});
    }

    // Eliminar cliente
    public int eliminar(int id){
        return db.delete("administrador", "id = ?", new String[]{ String.valueOf(id) });
    }

    //Listar cliente
    public ArrayList<Administrador> listar(){
        ArrayList<Administrador> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM administrador", null);

        if(cursor.moveToFirst()){
            do{
                Administrador admin  = new Administrador();
                admin.setId(cursor.getInt(0));
                admin.setDni(cursor.getString(1));
                admin.setNombres(cursor.getString(2));
                admin.setApellidos(cursor.getString(3));
                admin.setFechaNacimiento(cursor.getString(4));
                admin.setUsuario(cursor.getString(5));
                admin.setContrasena(cursor.getString(6));

                lista.add(admin);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    //Validar Login
    public Administrador validarLogin(String usuario, String contrasena){
        Administrador admin = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM administrador WHERE usuario = ? AND contrasena = ?",
                new String[]{usuario, contrasena}
        );

        if(cursor.moveToFirst()){
            admin = new Administrador();
            admin.setId(cursor.getInt(0));
            admin.setDni(cursor.getString(1));
            admin.setNombres(cursor.getString(2));
            admin.setApellidos(cursor.getString(3));
            admin.setFechaNacimiento(cursor.getString(4));
            admin.setUsuario(cursor.getString(5));
            admin.setContrasena(cursor.getString(6));
        }

        cursor.close();
        return admin;
    }

    // Insertar admin por defecto (solo pruebas)
    public void insertarAdminPrueba() {
        abrir();
        Cursor cursor = db.rawQuery("SELECT * FROM administrador", null);

        // Si no hay registros, insertamos uno
        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put("dni", "12345678");
            values.put("nombres", "Admin");
            values.put("apellidos", "Principal");
            values.put("fecha_nacimiento", "01/01/1990");
            values.put("usuario", "admin");
            values.put("contrasena", "123");

            db.insert("administrador", null, values);
        }

        cursor.close();
        cerrar();
    }


}
