package com.tienda.patrones.factory;

public class CreadorPagoContraEntrega extends CreadorPago {
    private final String direccionEntrega;

    public CreadorPagoContraEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    @Override
    public MetodoPago crearMetodoPago() {
        return new PagoContraEntrega(direccionEntrega);
    }
}