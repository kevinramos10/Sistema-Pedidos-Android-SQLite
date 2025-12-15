package com.example.examfinal.negocio;

import android.content.Context;

import com.example.examfinal.dao.ProductoDAO;
import com.example.examfinal.modelo.Producto;

import java.util.List;

public class ProductoNegocio {

    private ProductoDAO dao;

    public ProductoNegocio(Context context) {
        dao = new ProductoDAO(context);
    }

    // Registrar producto
    public String registrarProducto(Producto p) {
        dao.abrir();
        String respuesta = dao.insertarProducto(p);
        dao.cerrar();
        return respuesta;
    }

    // Actualizar producto
    public String actualizarProducto(Producto p) {
        dao.abrir();
        String respuesta = dao.actualizarProducto(p);
        dao.cerrar();
        return respuesta;
    }

    // Eliminar producto
    public String eliminarProducto(int idProducto) {
        dao.abrir();
        String respuesta = dao.eliminarProducto(idProducto);
        dao.cerrar();
        return respuesta;
    }

    // Listar por categoría
    public List<Producto> listarPorCategoria(int idCategoria) {
        dao.abrir();
        List<Producto> lista = dao.listarPorCategoria(idCategoria);
        dao.cerrar();
        return lista;
    }

    // Listar todos
    public List<Producto> listarTodos() {
        dao.abrir();
        List<Producto> lista = dao.listarProductos();
        dao.cerrar();
        return lista;
    }

    public Producto buscarPorId(int id) {
        dao.abrir();
        Producto p = dao.buscarPorId(id);
        dao.cerrar();
        return p;
    }

    public boolean disminuirStock(int idProducto, int cantidad) {
        dao.abrir();
        boolean r = dao.disminuirStock(idProducto, cantidad);
        dao.cerrar();
        return r;
    }

}
