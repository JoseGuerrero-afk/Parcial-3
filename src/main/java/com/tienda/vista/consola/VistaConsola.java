package com.tienda.vista.consola;

import com.tienda.modelo.ItemPedido;
import com.tienda.modelo.Pedido;
import com.tienda.modelo.Producto;
import com.tienda.patrones.builder.ItemPedidoBuilder;
import com.tienda.patrones.factory.CreadorPago;
import com.tienda.patrones.factory.CreadorPagoContraEntrega;
import com.tienda.patrones.factory.CreadorPagoTarjeta;
import com.tienda.patrones.factory.CreadorPagoTransferencia;
import com.tienda.patrones.strategy.DescuentoClienteFrecuente;
import com.tienda.patrones.strategy.DescuentoCupon;
import com.tienda.patrones.strategy.DescuentoTemporada;
import com.tienda.patrones.strategy.EstrategiaDescuento;
import com.tienda.servicio.TiendaService;
import java.util.Scanner;

public class VistaConsola {
    private final TiendaService tiendaService;
    private final Scanner scanner;

    public VistaConsola(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();
            try {
                switch (opcion) {
                    case "1":
                        mostrarCatalogo();
                        break;
                    case "2":
                        agregarProductoAlCarrito();
                        break;
                    case "3":
                        verCarrito();
                        break;
                    case "4":
                        aplicarDescuento();
                        break;
                    case "5":
                        cerrarPedido();
                        break;
                    case "6":
                        tiendaService.vaciarCarrito();
                        System.out.println("Carrito y descuentos reiniciados.");
                        break;
                    case "0":
                        salir = true;
                        System.out.println("Saliendo de la tienda...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n========= TIENDA EN LÍNEA (CONSOLA) =========");
        System.out.println("1. Ver catálogo de productos");
        System.out.println("2. Agregar producto al carrito (con extras)");
        System.out.println("3. Ver carrito y descuentos actuales");
        System.out.println("4. Aplicar descuento al pedido");
        System.out.println("5. Seleccionar medio de pago y finalizar pedido");
        System.out.println("6. Vaciar carrito");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void mostrarCatalogo() {
        System.out.println("\n--- CATÁLOGO DE PRODUCTOS ---");
        for (Producto p : tiendaService.getCatalogo()) {
            System.out.println("  " + p);
        }
    }

    private void agregarProductoAlCarrito() {
        mostrarCatalogo();
        System.out.print("Ingrese el ID del producto (ej. P01): ");
        String id = scanner.nextLine();
        Producto producto = tiendaService.buscarProductoPorId(id);
        if (producto == null) {
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine().trim());

        ItemPedidoBuilder builder = new ItemPedidoBuilder(producto).conCantidad(cantidad);

        System.out.print("¿Agregar Garantía Extendida (+$25,000)? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            builder.conGarantiaExtendida();
        }

        System.out.print("¿Agregar Empaque de Regalo (+$8,000)? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            builder.conEmpaqueRegalo();
        }

        System.out.print("¿Agregar Envío Express (+$15,000)? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            builder.conEnvioExpress();
        }

        ItemPedido item = builder.build();
        tiendaService.agregarAlCarrito(item);
        System.out.println("Agregado: " + item);
    }

    private void verCarrito() {
        System.out.println("\n--- CARRITO DE COMPRAS ---");
        if (tiendaService.getCarrito().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        for (ItemPedido item : tiendaService.getCarrito()) {
            System.out.println(" - " + item);
        }
        System.out.printf("Subtotal:          $%.2f%n", tiendaService.calcularSubtotalCarrito());
        if (!tiendaService.getDescuentosAplicados().isEmpty()) {
            System.out.println("Descuentos activos:");
            for (EstrategiaDescuento d : tiendaService.getDescuentosAplicados()) {
                System.out.println("   * " + d.getNombre());
            }
        }
        System.out.printf("Descuento total:  -$%.2f%n", tiendaService.calcularDescuentoTotal());
        System.out.printf("Total parcial:     $%.2f%n", tiendaService.calcularTotalConDescuentos());
    }

    private void aplicarDescuento() {
        System.out.println("\n--- APLICAR DESCUENTO ---");
        System.out.println("1. Descuento de Temporada (10%)");
        System.out.println("2. Descuento Cliente Frecuente (15%)");
        System.out.println("3. Cupón promocional (DESC10 o PROMO20)");
        System.out.print("Elija tipo de descuento: ");
        String tipo = scanner.nextLine().trim();

        switch (tipo) {
            case "1":
                tiendaService.agregarDescuento(new DescuentoTemporada());
                System.out.println("Descuento de temporada aplicado.");
                break;
            case "2":
                tiendaService.agregarDescuento(new DescuentoClienteFrecuente());
                System.out.println("Descuento de cliente frecuente aplicado.");
                break;
            case "3":
                System.out.print("Ingrese el código del cupón: ");
                String codigo = scanner.nextLine();
                tiendaService.agregarDescuento(new DescuentoCupon(codigo));
                System.out.println("Cupón aplicado correctamente.");
                break;
            default:
                System.out.println("Tipo de descuento no válido.");
        }
    }

    private void cerrarPedido() {
        if (tiendaService.getCarrito().isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }
        verCarrito();
        System.out.println("\n--- SELECCIONE MEDIO DE PAGO ---");
        System.out.println("1. Tarjeta de crédito/débito");
        System.out.println("2. Transferencia bancaria");
        System.out.println("3. Pago contra entrega (+$5,000 recargo)");
        System.out.print("Opción de pago: ");
        String opcionPago = scanner.nextLine().trim();

        CreadorPago creador;
        switch (opcionPago) {
            case "1":
                System.out.print("Ingrese número de tarjeta (mín. 4 dígitos): ");
                creador = new CreadorPagoTarjeta(scanner.nextLine());
                break;
            case "2":
                System.out.print("Ingrese banco de origen: ");
                creador = new CreadorPagoTransferencia(scanner.nextLine());
                break;
            case "3":
                System.out.print("Ingrese dirección de entrega: ");
                creador = new CreadorPagoContraEntrega(scanner.nextLine());
                break;
            default:
                System.out.println("Medio de pago no válido.");
                return;
        }

        Pedido pedido = tiendaService.cerrarPedido(creador);
        System.out.println("\n" + pedido.generarFacturaTexto());
    }

    public static void main(String[] args) {
        new VistaConsola(new TiendaService()).iniciar();
    }
}