package com.example.examfinal.negocio;

import android.content.Context;

import com.example.examfinal.dao.CompraDAO;
import com.example.examfinal.modelo.Compra;

import java.util.ArrayList;
import java.util.List;

public class NegocioCompra {

    private CompraDAO dao;

    public NegocioCompra(Context context){
        dao = new CompraDAO(context);
    }

    // Insertar compra
    public long insertar(Compra compra){
        dao.abrir();
        long id = dao.insertar(compra);
        dao.cerrar();
        return id;
    }

    // Listar todas las compras
    public ArrayList<Compra> listar(){
        dao.abrir();
        ArrayList<Compra> lista = dao.listar();
        dao.cerrar();
        return lista;
    }

    public List<Compra> listarPorCliente(int idCliente) {
        dao.abrir();
        List<Compra> lista = dao.listarPorCliente(idCliente);
        dao.cerrar();
        return lista;
    }

}
