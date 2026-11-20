package com.tienda.patrones.strategy;

public class DescuentoClienteFrecuente implements EstrategiaDescuento {
    private static final double PORCENTAJE = 0.15;

    @Override
    public String getNombre() {
        return "Descuento Cliente Frecuente (15%)";
    }

    @Override
    public double calcularDescuento(double montoBase) {
        return montoBase * PORCENTAJE;
    }
}