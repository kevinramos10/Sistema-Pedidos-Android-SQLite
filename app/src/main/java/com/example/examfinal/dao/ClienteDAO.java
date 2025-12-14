package com.example.examfinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examfinal.data.Conexion;
import com.example.examfinal.modelo.Cliente;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ClienteDAO {

    private Conexion con;
    private SQLiteDatabase db;

    public ClienteDAO(Context context){
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

    //Insertar cliente
    public long insertar(Cliente cli){
        ContentValues values = new ContentValues();
        values.put("dni", cli.getDni());
        values.put("nombres", cli.getNombres());
        values.put("apellidos", cli.getApellidos());
        values.put("fecha_nacimiento", cli.getFechaNacimiento());
        values.put("usuario", cli.getUsuario());
        values.put("contrasena", cli.getContrasena());

        return db.insert("cliente", null, values);
    }

    //Actualizar cliente
    public int actualizar(Cliente cli){
        ContentValues values = new ContentValues();
        values.put("dni", cli.getDni());
        values.put("nombres", cli.getNombres());
        values.put("apellidos", cli.getApellidos());
        values.put("fecha_nacimiento", cli.getFechaNacimiento());
        values.put("usuario", cli.getUsuario());
        values.put("contrasena", cli.getContrasena());

        return db.update("cliente", values, "id = ?", new String[]{ String.valueOf(cli.getId()) });
    }

    // Eliminar cliente
    public int eliminar(int id){
        return db.delete("cliente", "id = ?", new String[]{ String.valueOf(id) });
    }

    //Listar cliente
    public ArrayList<Cliente> listar(){
        ArrayList<Cliente> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM cliente", null);

        if(cursor.moveToFirst()){
            do{
                Cliente cli  = new Cliente();
                cli.setId(cursor.getInt(0));
                cli.setDni(cursor.getString(1));
                cli.setNombres(cursor.getString(2));
                cli.setApellidos(cursor.getString(3));
                cli.setFechaNacimiento(cursor.getString(4));
                cli.setUsuario(cursor.getString(5));
                cli.setContrasena(cursor.getString(6));

                lista.add(cli);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return lista;

    }

    //Validar Login
    public Cliente validarLogin(String usuario, String contrasena){
        Cliente cli = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM cliente WHERE usuario = ? AND contrasena = ?",
                new String[]{usuario, contrasena}
        );

        if(cursor.moveToFirst()){
            cli = new Cliente();
            cli.setId(cursor.getInt(0));
            cli.setDni(cursor.getString(1));
            cli.setNombres(cursor.getString(2));
            cli.setApellidos(cursor.getString(3));
            cli.setFechaNacimiento(cursor.getString(4));
            cli.setUsuario(cursor.getString(5));
            cli.setContrasena(cursor.getString(6));
        }

        cursor.close();
        return cli;
    }


}
