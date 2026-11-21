package com.tienda.patrones.factory;

public class PagoTransferencia implements MetodoPago {
    private final String bancoOrigen;

    public PagoTransferencia(String bancoOrigen) {
        if (bancoOrigen == null || bancoOrigen.isBlank()) {
            throw new IllegalArgumentException("Debe indicar el banco de origen para la transferencia.");
        }
        this.bancoOrigen = bancoOrigen.trim();
    }

    @Override
    public String getNombreMetodo() {
        return "Transferencia Bancaria (" + bancoOrigen + ")";
    }

    @Override
    public double calcularRecargo(double monto) {
        return 0.0;
    }

    @Override
    public String procesarPago(double montoTotal) {
        return String.format("Transferencia verificada desde %s por $%.2f.", bancoOrigen, montoTotal);
    }
}