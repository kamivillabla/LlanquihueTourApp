package ui;

import data.GestorDatos;
import data.GestorServicios;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import model.Cliente;
import model.Direccion;
import model.Guia;
import model.ServicioTuristico;
import model.Tour;
import service.GestorTours;
import util.RutInvalidoException;

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

        demostrarValidaciones(guias, tours, clientes);

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

    /**
     * Provoca errores a propósito (dentro de try-catch) para mostrar que las
     * validaciones y excepciones del sistema funcionan.
     */
    private static void demostrarValidaciones(ArrayList<Guia> guias, ArrayList<Tour> tours,
                                              ArrayList<Cliente> clientes) {
        System.out.println("--- DEMOSTRACIÓN DE VALIDACIONES ---");
        System.out.println("(Estos errores se provocan a propósito para mostrar que las validaciones funcionan)\n");

        try {
            new Direccion("Av. Demo", -5, "Llanquihue", "Los Lagos");
        } catch (IllegalArgumentException e) {
            System.out.println("Dirección rechazada -> " + e.getMessage());
        }

        try {
            Direccion direccionValida = new Direccion("Av. Demo", 100, "Llanquihue", "Los Lagos");
            new Guia("Juan", "Pérez", "SINFORMATO", "juan@correo.cl",
                    direccionValida, "Montañismo", "Español", 5, true);
        } catch (RutInvalidoException e) {
            System.out.println("Guía rechazado -> " + e.getMessage());
        }

        if (!guias.isEmpty()) {
            try {
                new Tour("Tour Demo", "Aventura", "Volcan Osorno", -1000, 8, guias.get(0));
            } catch (IllegalArgumentException e) {
                System.out.println("Tour rechazado -> " + e.getMessage());
            }
        }

        Tour tourSinCupos = buscarTourSinCupos(tours);
        if (tourSinCupos != null && !clientes.isEmpty()) {
            try {
                tourSinCupos.inscribirCliente(clientes.get(0));
            } catch (IllegalStateException e) {
                System.out.println("Inscripción rechazada -> " + e.getMessage());
            }
        }

        Tour tourConInscritos = buscarTourConInscritos(tours);
        if (tourConInscritos != null) {
            try {
                tourConInscritos.inscribirCliente(tourConInscritos.getInscritos().get(0));
            } catch (IllegalStateException e) {
                System.out.println("Inscripción rechazada -> " + e.getMessage());
            }
        }

        System.out.println();
    }

    /** Devuelve el primer tour que ya no tiene cupos disponibles, o null si no hay. */
    private static Tour buscarTourSinCupos(ArrayList<Tour> tours) {
        for (Tour tour : tours) {
            if (tour.getCuposDisponibles() <= 0) {
                return tour;
            }
        }
        return null;
    }

    /** Devuelve el primer tour que ya tiene clientes inscritos, o null si no hay. */
    private static Tour buscarTourConInscritos(ArrayList<Tour> tours) {
        for (Tour tour : tours) {
            if (!tour.getInscritos().isEmpty()) {
                return tour;
            }
        }
        return null;
    }
}
