package com.tienda.patrones.factory;

public interface MetodoPago {
    String getNombreMetodo();
    double calcularRecargo(double monto);
    String procesarPago(double montoTotal);
}