package com.tienda;

import com.tienda.servicio.TiendaService;
import com.tienda.vista.consola.VistaConsola;
import com.tienda.vista.gui.VistaSwing;
import java.util.Scanner;

public class TiendaOnlinePatrones {

    public static void main(String[] args) {
        TiendaService servicioCompartido = new TiendaService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        System.out.println("   TIENDA EN LÍNEA - PATRONES DE DISEÑO   ");
        System.out.println("");
        System.out.println("1. Ejecutar versión por Consola");
        System.out.println("2. Ejecutar versión Interfaz Gráfica (Swing)");
        System.out.print("Seleccione modo de inicio: ");

        String opcion = scanner.nextLine().trim();
        if ("2".equals(opcion)) {
            new VistaSwing(servicioCompartido).iniciar();
        } else {
            new VistaConsola(servicioCompartido).iniciar();
        }
    }
}