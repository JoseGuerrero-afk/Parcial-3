package com.tienda.patrones.factory;

public class CreadorPagoTransferencia extends CreadorPago {
    private final String bancoOrigen;

    public CreadorPagoTransferencia(String bancoOrigen) {
        this.bancoOrigen = bancoOrigen;
    }

    @Override
    public MetodoPago crearMetodoPago() {
        return new PagoTransferencia(bancoOrigen);
    }
}