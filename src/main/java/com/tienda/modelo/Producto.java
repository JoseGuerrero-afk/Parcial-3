package com.tienda.modelo;

public class Producto {
    private final String id;
    private final String nombre;
    private final String categoria;
    private final double precioBase;

    public Producto(String id, String nombre, String categoria, double precioBase) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioBase = precioBase;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - $%.2f", id, nombre, categoria, precioBase);
    }
}