package com.tienda.vista.gui;

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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class VistaSwing extends JFrame {
    private final TiendaService tiendaService;
    private final List<EstrategiaDescuento> cuponesAplicados = new ArrayList<>();

    private JComboBox<Producto> cmbProductos;
    private JSpinner spnCantidad;
    private JCheckBox chkGarantia;
    private JCheckBox chkRegalo;
    private JCheckBox chkExpress;

    private JCheckBox chkTemporada;
    private JCheckBox chkClienteFrecuente;
    private JTextField txtCupon;

    private JComboBox<String> cmbMetodoPago;
    private JLabel lblDatoPago;
    private JTextField txtDatoPago;

    private JTextArea txtResumen;

    public VistaSwing(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
        inicializarComponentes();
        actualizarVistaCarrito();
    }

    private void inicializarComponentes() {
        setTitle("Tienda en Línea - Patrones de Diseño");
        setSize(820, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior: Selección de producto + Extras (Builder)
        JPanel pnlProducto = new JPanel(new GridLayout(3, 1, 5, 5));
        pnlProducto.setBorder(BorderFactory.createTitledBorder("1. Catálogo y Extras del Producto (Builder)"));

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila1.add(new JLabel("Producto:"));
        cmbProductos = new JComboBox<>(tiendaService.getCatalogo().toArray(new Producto[0]));
        fila1.add(cmbProductos);
        fila1.add(new JLabel("Cantidad:"));
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        fila1.add(spnCantidad);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkGarantia = new JCheckBox("Garantía Extendida (+$25,000)");
        chkRegalo = new JCheckBox("Empaque de Regalo (+$8,000)");
        chkExpress = new JCheckBox("Envío Express (+$15,000)");
        fila2.add(chkGarantia);
        fila2.add(chkRegalo);
        fila2.add(chkExpress);

        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Agregar al Carrito");
        JButton btnVaciar = new JButton("Vaciar Carrito");
        fila3.add(btnAgregar);
        fila3.add(btnVaciar);

        pnlProducto.add(fila1);
        pnlProducto.add(fila2);
        pnlProducto.add(fila3);

        // Panel central: Descuentos (Strategy) y Pago (Factory Method)
        JPanel pnlOpciones = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel pnlDescuentos = new JPanel(new GridLayout(3, 1, 5, 5));
        pnlDescuentos.setBorder(BorderFactory.createTitledBorder("2. Descuentos Acumulables (Strategy)"));
        chkTemporada = new JCheckBox("Descuento Temporada (10%)");
        chkClienteFrecuente = new JCheckBox("Cliente Frecuente (15%)");

        JPanel filaCupon = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaCupon.add(new JLabel("Cupón (DESC10 / PROMO20):"));
        txtCupon = new JTextField(8);
        JButton btnAplicarCupon = new JButton("Aplicar Cupón");
        filaCupon.add(txtCupon);
        filaCupon.add(btnAplicarCupon);

        pnlDescuentos.add(chkTemporada);
        pnlDescuentos.add(chkClienteFrecuente);
        pnlDescuentos.add(filaCupon);

        JPanel pnlPago = new JPanel(new GridLayout(3, 1, 5, 5));
        pnlPago.setBorder(BorderFactory.createTitledBorder("3. Medio de Pago (Factory Method)"));

        JPanel filaMetodo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filaMetodo.add(new JLabel("Método:"));
        cmbMetodoPago = new JComboBox<>(new String[]{"Tarjeta", "Transferencia", "Contra Entrega"});
        filaMetodo.add(cmbMetodoPago);

        JPanel filaDato = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblDatoPago = new JLabel("Número Tarjeta:");
        txtDatoPago = new JTextField(15);
        filaDato.add(lblDatoPago);
        filaDato.add(txtDatoPago);

        JPanel filaPagar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnPagar = new JButton("Finalizar Pedido y Generar Factura");
        filaPagar.add(btnPagar);

        pnlPago.add(filaMetodo);
        pnlPago.add(filaDato);
        pnlPago.add(filaPagar);

        pnlOpciones.add(pnlDescuentos);
        pnlOpciones.add(pnlPago);

        // Área inferior: Resumen del carrito y factura final
        txtResumen = new JTextArea(14, 60);
        txtResumen.setEditable(false);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setBorder(BorderFactory.createTitledBorder("Detalle del Carrito y Factura"));

        JPanel pnlNorte = new JPanel(new BorderLayout(5, 5));
        pnlNorte.add(pnlProducto, BorderLayout.NORTH);
        pnlNorte.add(pnlOpciones, BorderLayout.CENTER);

        add(pnlNorte, BorderLayout.NORTH);
        add(scrollResumen, BorderLayout.CENTER);

        // Eventos
        btnAgregar.addActionListener(e -> agregarItem());
        btnVaciar.addActionListener(e -> reiniciarTodo());
        chkTemporada.addActionListener(e -> sincronizarDescuentos());
        chkClienteFrecuente.addActionListener(e -> sincronizarDescuentos());
        btnAplicarCupon.addActionListener(e -> agregarCupon());
        cmbMetodoPago.addActionListener(e -> actualizarEtiquetaPago());
        btnPagar.addActionListener(e -> finalizarCompra());
    }

    private void agregarItem() {
        try {
            Producto seleccionado = (Producto) cmbProductos.getSelectedItem();
            int cantidad = (Integer) spnCantidad.getValue();

            ItemPedidoBuilder builder = new ItemPedidoBuilder(seleccionado).conCantidad(cantidad);
            if (chkGarantia.isSelected()) builder.conGarantiaExtendida();
            if (chkRegalo.isSelected()) builder.conEmpaqueRegalo();
            if (chkExpress.isSelected()) builder.conEnvioExpress();

            tiendaService.agregarAlCarrito(builder.build());

            chkGarantia.setSelected(false);
            chkRegalo.setSelected(false);
            chkExpress.setSelected(false);
            spnCantidad.setValue(1);

            actualizarVistaCarrito();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCupon() {
        try {
            EstrategiaDescuento cupon = new DescuentoCupon(txtCupon.getText());
            cuponesAplicados.add(cupon);
            txtCupon.setText("");
            sincronizarDescuentos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cupón inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void sincronizarDescuentos() {
        tiendaService.limpiarDescuentos();
        if (chkTemporada.isSelected()) {
            tiendaService.agregarDescuento(new DescuentoTemporada());
        }
        if (chkClienteFrecuente.isSelected()) {
            tiendaService.agregarDescuento(new DescuentoClienteFrecuente());
        }
        for (EstrategiaDescuento cupon : cuponesAplicados) {
            tiendaService.agregarDescuento(cupon);
        }
        actualizarVistaCarrito();
    }

    private void actualizarEtiquetaPago() {
        String metodo = (String) cmbMetodoPago.getSelectedItem();
        if ("Tarjeta".equals(metodo)) {
            lblDatoPago.setText("Número Tarjeta:");
        } else if ("Transferencia".equals(metodo)) {
            lblDatoPago.setText("Banco Origen:");
        } else {
            lblDatoPago.setText("Dirección Entrega:");
        }
        txtDatoPago.setText("");
    }

    private void finalizarCompra() {
        try {
            String metodo = (String) cmbMetodoPago.getSelectedItem();
            String dato = txtDatoPago.getText();
            CreadorPago creador;

            if ("Tarjeta".equals(metodo)) {
                creador = new CreadorPagoTarjeta(dato);
            } else if ("Transferencia".equals(metodo)) {
                creador = new CreadorPagoTransferencia(dato);
            } else {
                creador = new CreadorPagoContraEntrega(dato);
            }

            Pedido pedido = tiendaService.cerrarPedido(creador);
            chkTemporada.setSelected(false);
            chkClienteFrecuente.setSelected(false);
            cuponesAplicados.clear();
            txtDatoPago.setText("");

            txtResumen.setText(pedido.generarFacturaTexto());
            JOptionPane.showMessageDialog(this, "Pedido procesado con éxito.", "Compra Confirmada", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al procesar pago", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reiniciarTodo() {
        tiendaService.vaciarCarrito();
        chkTemporada.setSelected(false);
        chkClienteFrecuente.setSelected(false);
        cuponesAplicados.clear();
        actualizarVistaCarrito();
    }

    private void actualizarVistaCarrito() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CARRITO ACTUAL ===\n");
        if (tiendaService.getCarrito().isEmpty()) {
            sb.append("El carrito está vacío. Seleccione productos arriba.\n");
        } else {
            for (ItemPedido item : tiendaService.getCarrito()) {
                sb.append(" - ").append(item).append("\n");
            }
            sb.append("\n------------------------------------------\n");
            sb.append(String.format("Subtotal:         $%.2f\n", tiendaService.calcularSubtotalCarrito()));
            for (EstrategiaDescuento d : tiendaService.getDescuentosAplicados()) {
                sb.append(" * ").append(d.getNombre()).append("\n");
            }
            sb.append(String.format("Descuento total: -$%.2f\n", tiendaService.calcularDescuentoTotal()));
            sb.append(String.format("Total parcial:    $%.2f\n", tiendaService.calcularTotalConDescuentos()));
        }
        txtResumen.setText(sb.toString());
    }

    public void iniciar() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) {
        new VistaSwing(new TiendaService()).iniciar();
    }
}