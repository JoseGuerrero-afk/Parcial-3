package com.tienda.patrones.factory;

public abstract class CreadorPago {

    // Factory Method: cada subclase decide qué producto concreto instanciar
    public abstract MetodoPago crearMetodoPago();

    public MetodoPago obtenerMetodoListo() {
        return crearMetodoPago();
    }
}