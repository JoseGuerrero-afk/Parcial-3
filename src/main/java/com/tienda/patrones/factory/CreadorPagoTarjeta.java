package com.tienda.patrones.factory;

public class CreadorPagoTarjeta extends CreadorPago {
    private final String numeroTarjeta;

    public CreadorPagoTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public MetodoPago crearMetodoPago() {
        return new PagoTarjeta(numeroTarjeta);
    }
}