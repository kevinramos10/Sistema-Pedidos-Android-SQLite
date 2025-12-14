package com.example.examfinal.modelo;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String usuario;
    private String contrasena;

    public Cliente() {
    }

    public Cliente(int id, String dni, String nombres, String apellidos, String fechaNacimiento, String usuario, String contrasena) {
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // Constructor sin ID (para registrar nuevos clientes)
    public Cliente(String dni, String nombres, String apellidos, String fechaNacimiento, String usuario, String contrasena) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
