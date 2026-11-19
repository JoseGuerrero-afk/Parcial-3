package com.tienda.patrones.builder;

import com.tienda.modelo.ItemPedido;
import com.tienda.modelo.Producto;

public class ItemPedidoBuilder {
    private final Producto producto;
    private int cantidad = 1;
    private boolean garantiaExtendida = false;
    private boolean empaqueRegalo = false;
    private boolean envioExpress = false;

    public ItemPedidoBuilder(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        this.producto = producto;
    }

    public ItemPedidoBuilder conCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
        return this;
    }

    public ItemPedidoBuilder conGarantiaExtendida() {
        this.garantiaExtendida = true;
        return this;
    }

    public ItemPedidoBuilder conEmpaqueRegalo() {
        this.empaqueRegalo = true;
        return this;
    }

    public ItemPedidoBuilder conEnvioExpress() {
        this.envioExpress = true;
        return this;
    }

    public ItemPedido build() {
        return new ItemPedido(producto, cantidad, garantiaExtendida, empaqueRegalo, envioExpress);
    }
}