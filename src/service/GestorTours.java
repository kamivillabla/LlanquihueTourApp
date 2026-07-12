package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import model.Tour;

/**
 * Maneja la colección de tours: mostrar, buscar y filtrar. Así Main solo
 * coordina y los recorridos viven acá.
 */
public class GestorTours {

    private final ArrayList<Tour> tours;

    public GestorTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }

    /** Indica cuántos tours hay cargados. */
    public int contarTours() {
        return tours.size();
    }

    /** @return el texto de todos los tours de la colección, uno por línea */
    public String formatearTodos() {
        if (tours.isEmpty()) {
            return "No hay tours cargados.";
        }
        StringBuilder texto = new StringBuilder();
        for (Tour tour : tours) {
            texto.append(tour).append("\n");
        }
        return texto.toString();
    }

    /** @return el/los tour(es) cuyo nombre coincide exactamente con {@code nombreBuscado} */
    public String formatearBusqueda(String nombreBuscado) {
        StringBuilder texto = new StringBuilder();
        boolean encontrado = false;
        for (Tour tour : tours) {
            if (tour.getNombre().equalsIgnoreCase(nombreBuscado)) {
                texto.append("Encontrado: ").append(tour).append("\n");
                encontrado = true;
            }
        }
        if (!encontrado) {
            texto.append("No se encontró un tour llamado \"").append(nombreBuscado).append("\".");
        }
        return texto.toString();
    }

    /** @return los tours de un tipo determinado */
    public String formatearPorTipo(String tipoBuscado) {
        StringBuilder texto = new StringBuilder();
        for (Tour tour : tours) {
            if (tour.getTipo().equalsIgnoreCase(tipoBuscado)) {
                texto.append(tour).append("\n");
            }
        }
        return texto.toString();
    }

    /** @return los tours que aún tienen cupos disponibles */
    public String formatearConCupos() {
        StringBuilder texto = new StringBuilder();
        for (Tour tour : tours) {
            if (tour.getCuposDisponibles() > 0) {
                texto.append(tour).append("\n");
            }
        }
        return texto.toString();
    }

    /** @return los tours con precio menor o igual al máximo indicado */
    public String formatearPorPrecioMaximo(int precioMaximo) {
        StringBuilder texto = new StringBuilder();
        for (Tour tour : tours) {
            if (tour.getPrecio() <= precioMaximo) {
                texto.append(tour).append("\n");
            }
        }
        return texto.toString();
    }

    /**
     * Cuenta cuántos tours hay por tipo. Devuelve el mapa "tipo -> cantidad"
     * ordenado alfabéticamente (usa {@link TreeMap}).
     * @return un mapa con la cantidad de tours por tipo, ordenado alfabéticamente
     */
    public Map<String, Integer> contarPorTipo() {
        HashMap<String, Integer> conteo = new HashMap<>();
        for (Tour tour : tours) {
            String tipo = tour.getTipo();
            conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
        }
        return new TreeMap<>(conteo);
    }
}
