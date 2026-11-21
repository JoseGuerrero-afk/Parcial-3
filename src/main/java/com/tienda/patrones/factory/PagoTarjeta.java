package com.tienda.patrones.factory;

public class PagoTarjeta implements MetodoPago {
    private final String numeroTarjeta;

    public PagoTarjeta(String numeroTarjeta) {
        if (numeroTarjeta == null || numeroTarjeta.trim().length() < 4) {
            throw new IllegalArgumentException("Número de tarjeta inválido (mínimo 4 dígitos).");
        }
        this.numeroTarjeta = numeroTarjeta.trim();
    }

    @Override
    public String getNombreMetodo() {
        String ultimos4 = numeroTarjeta.substring(numeroTarjeta.length() - 4);
        return "Tarjeta terminada en ****" + ultimos4;
    }

    @Override
    public double calcularRecargo(double monto) {
        return 0.0;
    }

    @Override
    public String procesarPago(double montoTotal) {
        if (montoTotal < 10000.0) {
            throw new IllegalArgumentException("El pago con tarjeta requiere un monto mínimo de $10,000.");
        }
        return String.format("Pago aprobado con %s por $%.2f.", getNombreMetodo(), montoTotal);
    }
}