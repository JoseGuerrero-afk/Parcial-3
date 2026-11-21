package com.tienda.patrones.factory;

public class PagoContraEntrega implements MetodoPago {
    private static final double RECARGO_EFECTIVO = 5000.0;
    private static final double MONTO_MAXIMO = 500000.0;
    private final String direccionEntrega;

    public PagoContraEntrega(String direccionEntrega) {
        if (direccionEntrega == null || direccionEntrega.isBlank()) {
            throw new IllegalArgumentException("Debe ingresar una dirección para el pago contra entrega.");
        }
        this.direccionEntrega = direccionEntrega.trim();
    }

    @Override
    public String getNombreMetodo() {
        return "Pago Contra Entrega (Recargo $5,000)";
    }

    @Override
    public double calcularRecargo(double monto) {
        return RECARGO_EFECTIVO;
    }

    @Override
    public String procesarPago(double montoTotal) {
        if (montoTotal > MONTO_MAXIMO) {
            throw new IllegalArgumentException("El pago contra entrega no permite pedidos mayores a $500,000.");
        }
        return String.format("Pedido programado contra entrega en '%s'. Cobrar $%.2f al recibir.",
                direccionEntrega, montoTotal);
    }
}