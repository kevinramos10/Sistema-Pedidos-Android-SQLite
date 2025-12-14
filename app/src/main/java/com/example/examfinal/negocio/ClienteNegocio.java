package com.example.examfinal.negocio;

import android.content.Context;

import com.example.examfinal.dao.ClienteDAO;
import com.example.examfinal.modelo.Cliente;

import java.util.List;

public class ClienteNegocio {

    private ClienteDAO dao;

    public ClienteNegocio(Context context){
        dao = new ClienteDAO(context);

    }

    //Registrar cliente
    public String registrarCliente(Cliente cli){

        //Validar campos completos
        if(cli.getDni().isEmpty() || cli.getNombres().isEmpty() || cli.getApellidos().isEmpty() || cli.getFechaNacimiento().isEmpty() || cli.getUsuario().isEmpty() || cli.getContrasena().isEmpty()){
            return "Todos los campos deben ser completados";
        }

        if(cli.getDni().length() != 8){
            return "El DNI debe tener 8 dígitos";
        }

        dao.abrir();
        long resultado = dao.insertar(cli);
        dao.cerrar();

        if(resultado > 0){
            return "Cliente registrado correctamente";
        } else{
            return "Error al registrar";
        }
    }

    //actualizar cliente
    public String actualizarCliente(Cliente cli){
        //Validar campos completos
        if(cli.getDni().isEmpty() || cli.getNombres().isEmpty() || cli.getApellidos().isEmpty() || cli.getFechaNacimiento().isEmpty() || cli.getUsuario().isEmpty() || cli.getContrasena().isEmpty()){
            return "Todos los campos deben ser completados";
        }

        if(cli.getDni().length() != 8){
            return "El DNI debe tener 8 dígitos";
        }

        dao.abrir();
        long resultado = dao.actualizar(cli);
        dao.cerrar();

        if(resultado > 0){
            return "Cliente actualizado correctamente";
        } else{
            return "Error al actualizar";
        }
    }

    //eliminar cliente
    public String eliminarCliente(int idCliente){

        dao.abrir();
        long resultado = dao.eliminar(idCliente);
        dao.cerrar();

        if(resultado > 0){
            return "Cliente eliminado correctamente";
        } else{
            return "Error al eliminar";
        }
    }

    // Listar clientes
    public List<Cliente> listarClientes() {

        dao.abrir();
        List<Cliente> lista = dao.listar();
        dao.cerrar();

        return lista;
    }

    // Validar Login
    public Cliente validarLogin(String usuario, String contrasena){

        if(usuario.isEmpty() || contrasena.isEmpty()){
            return null;
        }

        dao.abrir();
        Cliente cli = dao.validarLogin(usuario, contrasena);

        return cli;

    }





}
