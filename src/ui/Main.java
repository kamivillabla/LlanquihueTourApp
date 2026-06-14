package ui;

import data.GestorDatos;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import model.Tour;

/**
 * Carga los tours desde resources/tours.txt, recorre la colección
 * y muestra los resultados filtrados por consola.
 */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        GestorDatos gestor = new GestorDatos();
        ArrayList<Tour> tours = gestor.cargarTours("resources/tours.txt");

        System.out.println("---TOURS DISPONIBLES - LLANQUIHUE TOUR ---");

        mostrarTodos(tours);
        mostrarPorTipo(tours, "Aventura");
        mostrarConCupos(tours);
    }

    /**
     * Recorre la colección completa e imprime cada tour.
     */
    private static void mostrarTodos(ArrayList<Tour> tours) {
        System.out.println("\n--- Listado completo ---");
        for (Tour tour : tours) {
            System.out.println(tour);
        }
    }

    /**
     * Filtra e imprime los tours cuyo tipo coincide con el indicado.
     */
    private static void mostrarPorTipo(ArrayList<Tour> tours, String tipo) {
        System.out.println("\n--- Tours de tipo " + tipo + " ---");
        for (Tour tour : tours) {
            if (tour.getTipo().equalsIgnoreCase(tipo)) {
                System.out.println(tour);
            }
        }
    }

    /**
     * Filtra e imprime los tours que aún tienen cupos disponibles.
     */
    private static void mostrarConCupos(ArrayList<Tour> tours) {
        System.out.println("\n--- Tours con cupos disponibles ---");
        for (Tour tour : tours) {
            if (tour.getCupos() > 0) {
                System.out.println(tour);
            }
        }
    }
}
