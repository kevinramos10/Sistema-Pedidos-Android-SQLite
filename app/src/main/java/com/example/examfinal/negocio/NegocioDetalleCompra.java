package com.example.examfinal.negocio;

import android.content.Context;

import com.example.examfinal.dao.DetalleCompraDAO;
import com.example.examfinal.modelo.DetalleCompra;

import java.util.ArrayList;

public class NegocioDetalleCompra {

    private DetalleCompraDAO dao;

    public NegocioDetalleCompra(Context context){
        dao = new DetalleCompraDAO(context);
    }

    // Insertar un detalle
    public long insertar(DetalleCompra det){
        dao.abrir();
        long id = dao.insertar(det);
        dao.cerrar();
        return id;
    }

    // Listar detalles por compra
    public ArrayList<DetalleCompra> listarPorCompra(int compraId){
        dao.abrir();
        ArrayList<DetalleCompra> lista = dao.listarPorCompra(compraId);
        dao.cerrar();
        return lista;
    }
}
