package com.example.examfinal.negocio;

import android.content.Context;

import com.example.examfinal.dao.AdministradorDAO;
import com.example.examfinal.modelo.Administrador;
import com.example.examfinal.modelo.Cliente;

import java.util.List;

public class AdministradorNegocio {

    private AdministradorDAO dao;

    public AdministradorNegocio(Context context){
        dao = new AdministradorDAO(context);
    }

    //Registrar Administrador
    public String registrarAdministrador(Administrador admin){
        //Validar campos completos
        if(admin.getDni().isEmpty() || admin.getNombres().isEmpty() || admin.getApellidos().isEmpty() || admin.getFechaNacimiento().isEmpty() || admin.getUsuario().isEmpty() || admin.getContrasena().isEmpty()){
            return "Todos los campos deben ser completados";
        }

        if(admin.getDni().length() != 8){
            return "El DNI debe tener 8 dígitos";
        }

        dao.abrir();
        long resultado = dao.insertar(admin);
        dao.cerrar();

        if(resultado > 0){
            return "Cliente registrado correctamente";
        }else{
            return "Error al registrar";
        }
    }

    //Actualizar Administrador
    public String actualizarAdministrador(Administrador admin){
        //Validar campos completos
        if(admin.getDni().isEmpty() || admin.getNombres().isEmpty() || admin.getApellidos().isEmpty() || admin.getFechaNacimiento().isEmpty() || admin.getUsuario().isEmpty() || admin.getContrasena().isEmpty()){
            return "Todos los campos deben ser completados";
        }

        if(admin.getDni().length() != 8){
            return "El DNI debe tener 8 dígitos";
        }

        dao.abrir();
        long resultado = dao.actualizar(admin);
        dao.cerrar();

        if(resultado > 0){
            return "Cliente actulizado correctamente";
        }else{
            return "Error al registrar";
        }
    }

    //Eliminar Administrador
    public String eliminarAdministrador(int idAdministrador){
        dao.abrir();
        long resultado = dao.eliminar(idAdministrador);
        dao.cerrar();

        if(resultado > 0){
            return "Cliente eliminado correctamente";
        } else{
            return "Error al eliminar";
        }
    }

    // Listar Administrador
    public List<Administrador> listarAdministrador() {

        dao.abrir();
        List<Administrador> lista = dao.listar();
        dao.cerrar();

        return lista;
    }


    // Validar Login
    public Administrador validarLogin(String usuario, String contrasena){

        if(usuario.isEmpty() || contrasena.isEmpty()){
            return null;
        }

        dao.abrir();
        Administrador admin = dao.validarLogin(usuario, contrasena);


        return admin;
    }


}
