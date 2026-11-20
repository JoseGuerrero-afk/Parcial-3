package com.tienda.patrones.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorDescuentos {
    private final List<EstrategiaDescuento> estrategias = new ArrayList<>();

    public void agregarEstrategia(EstrategiaDescuento estrategia) {
        if (estrategia != null) {
            estrategias.add(estrategia);
        }
    }

    public void limpiarEstrategias() {
        estrategias.clear();
    }

    public List<EstrategiaDescuento> getEstrategias() {
        return Collections.unmodifiableList(estrategias);
    }

    public double calcularDescuentoTotal(double subtotal) {
        double montoRestante = subtotal;
        double descuentoTotal = 0.0;

        for (EstrategiaDescuento estrategia : estrategias) {
            double descuentoActual = estrategia.calcularDescuento(montoRestante);
            descuentoTotal += descuentoActual;
            montoRestante -= descuentoActual;
        }
        return descuentoTotal;
    }
}