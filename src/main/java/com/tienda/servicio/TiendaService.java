package com.tienda.servicio;

import com.tienda.modelo.ItemPedido;
import com.tienda.modelo.Pedido;
import com.tienda.modelo.Producto;
import com.tienda.patrones.factory.CreadorPago;
import com.tienda.patrones.factory.MetodoPago;
import com.tienda.patrones.strategy.EstrategiaDescuento;
import com.tienda.patrones.strategy.GestorDescuentos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TiendaService {
    private final List<Producto> catalogo = new ArrayList<>();
    private final List<ItemPedido> carrito = new ArrayList<>();
    private final GestorDescuentos gestorDescuentos = new GestorDescuentos();

    public TiendaService() {
        cargarCatalogoInicial();
    }

    private void cargarCatalogoInicial() {
        catalogo.add(new Producto("P01", "Audífonos Inalámbricos Pro", "Tecnología", 180000.0));
        catalogo.add(new Producto("P02", "Teclado Mecánico RGB", "Tecnología", 240000.0));
        catalogo.add(new Producto("P03", "Chaqueta Impermeable", "Ropa", 150000.0));
        catalogo.add(new Producto("P04", "Tenis Deportivos Urban", "Ropa", 210000.0));
        catalogo.add(new Producto("P05", "Café Especial Origen 500g", "Alimentos", 35000.0));
    }

    public List<Producto> getCatalogo() {
        return Collections.unmodifiableList(catalogo);
    }

    public Producto buscarProductoPorId(String id) {
        for (Producto p : catalogo) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    public void agregarAlCarrito(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("El ítem no puede ser nulo.");
        }
        carrito.add(item);
    }

    public List<ItemPedido> getCarrito() {
        return Collections.unmodifiableList(carrito);
    }

    public void vaciarCarrito() {
        carrito.clear();
        gestorDescuentos.limpiarEstrategias();
    }

    public void agregarDescuento(EstrategiaDescuento estrategia) {
        gestorDescuentos.agregarEstrategia(estrategia);
    }

    public void limpiarDescuentos() {
        gestorDescuentos.limpiarEstrategias();
    }

    public List<EstrategiaDescuento> getDescuentosAplicados() {
        return gestorDescuentos.getEstrategias();
    }

    public double calcularSubtotalCarrito() {
        double subtotal = 0.0;
        for (ItemPedido item : carrito) {
            subtotal += item.calcularSubtotal();
        }
        return subtotal;
    }

    public double calcularDescuentoTotal() {
        return gestorDescuentos.calcularDescuentoTotal(calcularSubtotalCarrito());
    }

    public double calcularTotalConDescuentos() {
        return calcularSubtotalCarrito() - calcularDescuentoTotal();
    }

    public Pedido cerrarPedido(CreadorPago creadorPago) {
        if (carrito.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío. Agregue productos antes de pagar.");
        }
        if (creadorPago == null) {
            throw new IllegalArgumentException("Debe seleccionar un medio de pago.");
        }

        double subtotal = calcularSubtotalCarrito();
        double totalDescuento = calcularDescuentoTotal();
        double montoConDescuento = subtotal - totalDescuento;

        MetodoPago metodoPago = creadorPago.obtenerMetodoListo();
        double recargo = metodoPago.calcularRecargo(montoConDescuento);
        double totalFinal = montoConDescuento + recargo;

        String confirmacion = metodoPago.procesarPago(totalFinal);

        List<String> nombresDescuentos = new ArrayList<>();
        for (EstrategiaDescuento est : gestorDescuentos.getEstrategias()) {
            nombresDescuentos.add(est.getNombre());
        }

        Pedido pedidoConfirmado = new Pedido(
                carrito,
                subtotal,
                nombresDescuentos,
                totalDescuento,
                metodoPago.getNombreMetodo(),
                recargo,
                totalFinal,
                confirmacion
        );

        vaciarCarrito();
        return pedidoConfirmado;
    }
}   