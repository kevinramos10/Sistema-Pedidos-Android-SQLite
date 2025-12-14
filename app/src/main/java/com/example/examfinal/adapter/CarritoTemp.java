package com.example.examfinal.adapter;

import java.util.HashMap;
import java.util.Map;

public class CarritoTemp {

    private static CarritoTemp instancia;
    private Map<Integer, Integer> items = new HashMap<>();

    private CarritoTemp() {}

    public static CarritoTemp getInstancia() {
        if (instancia == null) {
            instancia = new CarritoTemp();
        }
        return instancia;
    }

    public void agregar(int idProducto, int cantidad) {
        if (items.containsKey(idProducto)) {
            items.put(idProducto, items.get(idProducto) + cantidad);
        } else {
            items.put(idProducto, cantidad);
        }
    }

    public void actualizar(int idProducto, int cantidad) {
        if (cantidad <= 0) {
            items.remove(idProducto);
        } else {
            items.put(idProducto, cantidad);
        }
    }

    public int obtenerCantidad(int idProducto) {
        return items.getOrDefault(idProducto, 0);
    }

    public Map<Integer, Integer> obtenerTodo() {
        return items;
    }

    public void eliminar(int idProducto) {
        items.remove(idProducto);
    }

    public void limpiar() {
        items.clear();
    }
}
