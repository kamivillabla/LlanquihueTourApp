package ui;

import data.GestorDatos;
import data.GestorServicios;
import demo.DemoValidaciones;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import model.Cliente;
import model.Guia;
import model.ServicioTuristico;
import model.Tour;
import service.GestorTours;

/**
 * Punto de entrada del sistema. Coordina: pide los datos a la capa de datos y
 * usa los gestores para mostrarlos. La lógica vive en las otras clases.
 */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        GestorDatos gestorDatos = new GestorDatos();
        ArrayList<Guia> guias = gestorDatos.cargarGuias("resources/guias.txt");
        ArrayList<Tour> tours = gestorDatos.cargarTours("resources/tours.txt", guias);
        ArrayList<Cliente> clientes = gestorDatos.cargarClientes("resources/clientes.txt");
        gestorDatos.cargarInscripciones("resources/inscripciones.txt", tours, clientes);

        GestorTours gestorTours = new GestorTours(tours);

        System.out.println("-----------------------------------------");
        System.out.println("        SISTEMA LLANQUIHUE TOUR         ");
        System.out.println("-----------------------------------------");
        System.out.println("Guías cargados:     " + guias.size());
        System.out.println("Tours cargados:     " + gestorTours.contarTours());
        System.out.println("Clientes cargados:  " + clientes.size());

        System.out.println("\n--- LISTADO COMPLETO DE TOURS ---");
        gestorTours.mostrarTodos();

        System.out.println("\n--- TOURS DE TIPO AVENTURA ---");
        gestorTours.filtrarPorTipo("Aventura");

        System.out.println("\n--- TOURS CON CUPOS DISPONIBLES ---");
        gestorTours.filtrarConCupos();

        System.out.println("\n--- TOURS DE HASTA $25.000 ---");
        gestorTours.filtrarPorPrecioMaximo(25000);

        System.out.println("\n--- BÚSQUEDA: \"Tour Kayak Familiar\" ---");
        gestorTours.buscarPorNombre("Tour Kayak Familiar");

        System.out.println("\n--- CANTIDAD DE TOURS POR TIPO ---");
        for (Map.Entry<String, Integer> entrada : gestorTours.contarPorTipo().entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        }

        System.out.println("\n--- CLIENTES INSCRITOS POR TOUR ---");
        for (Tour tour : tours) {
            System.out.println(tour.getNombre() + " (" + tour.getInscritos().size() + " inscritos):");
            if (tour.getInscritos().isEmpty()) {
                System.out.println("   Sin inscritos.");
            } else {
                for (Cliente cliente : tour.getInscritos()) {
                    System.out.println("   - " + cliente.getNombre() + " " + cliente.getApellido()
                            + " (" + cliente.getNacionalidad() + ")");
                }
            }
        }

        System.out.println("\n--- GUÍAS TURÍSTICOS ---");
        for (Guia guia : guias) {
            System.out.println(guia);
            System.out.println();
        }

        System.out.println("--- CLIENTES REGISTRADOS ---");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
            System.out.println();
        }

        DemoValidaciones.ejecutar(guias, tours, clientes);

        mostrarServiciosTuristicos();

        System.out.println("-----------------------------------------");
        System.out.println("            FIN DEL SISTEMA            ");
        System.out.println("-----------------------------------------");
    }

    /** Pide los servicios turísticos al gestor y los imprime uno por uno. */
    private static void mostrarServiciosTuristicos() {
        System.out.println("\n--- SERVICIOS TURÍSTICOS DISPONIBLES ---");
        GestorServicios gestorServicios = new GestorServicios();
        ArrayList<ServicioTuristico> servicios = gestorServicios.crearServicios();
        for (ServicioTuristico servicio : servicios) {
            System.out.println(servicio);
        }
        System.out.println();
    }
}
