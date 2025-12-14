package com.example.examfinal.modelo;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private int idCategoria;
    private double precio;
    private int stock;
    private String foto; // ruta o URI de la imagen

    public Producto() {
    }

    public Producto(int id, String nombre, String descripcion, int idCategoria, double precio, int stock, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.precio = precio;
        this.stock = stock;
        this.foto = foto;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

}
