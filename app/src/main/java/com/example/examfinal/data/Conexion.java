package com.example.examfinal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant_v2.db";

    private static final int DATABASE_VERSION = 5;

    public Conexion(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(
                "CREATE TABLE cliente(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "dni TEXT NOT NULL," +
                        "nombres TEXT NOT NULL," +
                        "apellidos TEXT NOT NULL," +
                        "fecha_nacimiento TEXT NOT NULL," +
                        "usuario TEXT UNIQUE NOT NULL," +
                        "contrasena TEXT NOT NULL"+
                ");"
        );

        db.execSQL(
                "CREATE TABLE administrador(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "dni TEXT NOT NULL, " +
                        "nombres TEXT NOT NULL," +
                        "apellidos TEXT NOT NULL," +
                        "fecha_nacimiento TEXT NOT NULL," +
                        "usuario TEXT UNIQUE NOT NULL," +
                        "contrasena TEXT NOT NULL"+
                ");"
        );

        db.execSQL(
                "CREATE TABLE categoria (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT NOT NULL" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE producto (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT NOT NULL," +
                        "descripcion TEXT NOT NULL," +
                        "id_categoria INTEGER NOT NULL," +
                        "precio DECIMAL(10,2) NOT NULL," +
                        "stock INTEGER DEFAULT 0," +
                        "foto TEXT," +
                        "FOREIGN KEY(id_categoria) REFERENCES categoria(id)" +
                        ");"
        );

        // ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
        //          TABLA COMPRA
        // ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░

        db.execSQL(
                "CREATE TABLE compra (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_cliente INTEGER NOT NULL," +
                        "fecha TEXT NOT NULL," +
                        "total DECIMAL(10,2) NOT NULL," +
                        "destinoLat DOUBLE," +
                        "destinoLng DOUBLE," +
                        "metodoPago TEXT," +        // Yape / Plin
                        "estado TEXT," +            // Pagado, Pendiente
                        "FOREIGN KEY(id_cliente) REFERENCES cliente(id)" +
                        ");"
        );

        // ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
        //       TABLA DETALLE COMPRA
        // ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░

        db.execSQL(
                "CREATE TABLE detalle_compra (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_compra INTEGER NOT NULL," +
                        "id_producto INTEGER NOT NULL," +
                        "cantidad INTEGER NOT NULL," +
                        "precioUnitario DECIMAL(10,2) NOT NULL," +
                        "subtotal DECIMAL(10,2) NOT NULL," +
                        "FOREIGN KEY(id_compra) REFERENCES compra(id)," +
                        "FOREIGN KEY(id_producto) REFERENCES producto(id)" +
                        ");"
        );


        // Insertar las categorías predeterminadas
        db.execSQL("INSERT INTO categoria (nombre) VALUES ('Platillos');");
        db.execSQL("INSERT INTO categoria (nombre) VALUES ('Bebidas');");
        db.execSQL("INSERT INTO categoria (nombre) VALUES ('Postres');");
        db.execSQL("INSERT INTO categoria (nombre) VALUES ('Adicionales');");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS detalle_compra");
        db.execSQL("DROP TABLE IF EXISTS compra");
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS administrador");
        db.execSQL("DROP TABLE IF EXISTS cliente");

        onCreate(db);
    }
}
