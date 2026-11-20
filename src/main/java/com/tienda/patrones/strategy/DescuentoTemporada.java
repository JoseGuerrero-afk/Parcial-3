package com.tienda.patrones.strategy;

public class DescuentoTemporada implements EstrategiaDescuento {
    private static final double PORCENTAJE = 0.10;

    @Override
    public String getNombre() {
        return "Descuento de Temporada (10%)";
    }

    @Override
    public double calcularDescuento(double montoBase) {
        return montoBase * PORCENTAJE;
    }
}