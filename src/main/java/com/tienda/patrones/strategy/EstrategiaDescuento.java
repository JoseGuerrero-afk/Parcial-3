package com.tienda.patrones.strategy;

public interface EstrategiaDescuento {
    String getNombre();
    double calcularDescuento(double montoBase);
}