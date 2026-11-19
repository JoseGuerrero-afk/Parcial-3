package com.tienda.modelo;

public class ItemPedido {
    public static final double COSTO_GARANTIA_EXTENDIDA = 25000.0;
    public static final double COSTO_EMPAQUE_REGALO = 8000.0;
    public static final double COSTO_ENVIO_EXPRESS = 15000.0;

    private final Producto producto;
    private final int cantidad;
    private final boolean garantiaExtendida;
    private final boolean empaqueRegalo;
    private final boolean envioExpress;

    public ItemPedido(Producto producto, int cantidad, boolean garantiaExtendida,
                      boolean empaqueRegalo, boolean envioExpress) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.garantiaExtendida = garantiaExtendida;
        this.empaqueRegalo = empaqueRegalo;
        this.envioExpress = envioExpress;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean tieneGarantiaExtendida() {
        return garantiaExtendida;
    }

    public boolean tieneEmpaqueRegalo() {
        return empaqueRegalo;
    }

    public boolean tieneEnvioExpress() {
        return envioExpress;
    }

    public double calcularSubtotal() {
        double extrasUnitarios = 0.0;
        if (garantiaExtendida) {
            extrasUnitarios += COSTO_GARANTIA_EXTENDIDA;
        }
        if (empaqueRegalo) {
            extrasUnitarios += COSTO_EMPAQUE_REGALO;
        }
        if (envioExpress) {
            extrasUnitarios += COSTO_ENVIO_EXPRESS;
        }
        return (producto.getPrecioBase() + extrasUnitarios) * cantidad;
    }

    public String getDetalleExtras() {
        StringBuilder extras = new StringBuilder();
        if (garantiaExtendida) extras.append("Garantía extendida, ");
        if (empaqueRegalo) extras.append("Empaque de regalo, ");
        if (envioExpress) extras.append("Envío express, ");

        if (extras.length() == 0) {
            return "Sin extras";
        }
        return extras.substring(0, extras.length() - 2);
    }

    @Override
    public String toString() {
        return String.format("%dx %s | Extras: %s | Subtotal: $%.2f",
                cantidad, producto.getNombre(), getDetalleExtras(), calcularSubtotal());
    }
}