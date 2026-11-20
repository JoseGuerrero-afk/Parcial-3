package com.tienda.patrones.strategy;

public class DescuentoCupon implements EstrategiaDescuento {
    private final String codigo;
    private final double porcentaje;

    public DescuentoCupon(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del cupón no puede estar vacío.");
        }
        String codigoNormalizado = codigo.trim().toUpperCase();
        switch (codigoNormalizado) {
            case "DESC10":
                this.codigo = codigoNormalizado;
                this.porcentaje = 0.10;
                break;
            case "PROMO20":
                this.codigo = codigoNormalizado;
                this.porcentaje = 0.20;
                break;
            default:
                throw new IllegalArgumentException("Cupón no válido: " + codigo);
        }
    }

    @Override
    public String getNombre() {
        return String.format("Cupón %s (%.0f%%)", codigo, porcentaje * 100);
    }

    @Override
    public double calcularDescuento(double montoBase) {
        return montoBase * porcentaje;
    }
}