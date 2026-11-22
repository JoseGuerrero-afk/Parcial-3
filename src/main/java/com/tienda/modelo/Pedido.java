package com.tienda.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final List<ItemPedido> items;
    private final double subtotal;
    private final List<String> descuentosAplicados;
    private final double totalDescuento;
    private final String metodoPago;
    private final double recargoPago;
    private final double totalFinal;
    private final String mensajeConfirmacion;

    public Pedido(List<ItemPedido> items, double subtotal, List<String> descuentosAplicados,
                  double totalDescuento, String metodoPago, double recargoPago,
                  double totalFinal, String mensajeConfirmacion) {
        this.items = new ArrayList<>(items);
        this.subtotal = subtotal;
        this.descuentosAplicados = new ArrayList<>(descuentosAplicados);
        this.totalDescuento = totalDescuento;
        this.metodoPago = metodoPago;
        this.recargoPago = recargoPago;
        this.totalFinal = totalFinal;
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

    public List<ItemPedido> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotalDescuento() {
        return totalDescuento;
    }

    public double getRecargoPago() {
        return recargoPago;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String generarFacturaTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("         RESUMEN FINAL DEL PEDIDO         \n");
        sb.append("\n");
        for (ItemPedido item : items) {
            sb.append(" - ").append(item.toString()).append("\n");
        }
        sb.append("------------------------------------------\n");
        sb.append(String.format("Subtotal productos:      $%.2f\n", subtotal));
        if (descuentosAplicados.isEmpty()) {
            sb.append("Descuentos aplicados:    Ninguno\n");
        } else {
            sb.append("Descuentos aplicados:\n");
            for (String desc : descuentosAplicados) {
                sb.append("   * ").append(desc).append("\n");
            }
        }
        sb.append(String.format("Total descontado:       -$%.2f\n", totalDescuento));
        sb.append(String.format("Medio de pago:           %s\n", metodoPago));
        sb.append(String.format("Recargo medio de pago:  +$%.2f\n", recargoPago));
        sb.append("------------------------------------------\n");
        sb.append(String.format("TOTAL A PAGAR:           $%.2f\n", totalFinal));
        sb.append("Estado: ").append(mensajeConfirmacion).append("\n");
        sb.append("==========================================");
        return sb.toString();
    }
}