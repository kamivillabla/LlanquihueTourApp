package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import model.Tour;

/**
 * Capa de servicio: administra la colección de tours y concentra las
 * operaciones de negocio (mostrar, buscar y filtrar).
 *
 * <p>Gracias a esta clase, la lógica no vive dentro de {@code Main}: el Main
 * solo coordina, mientras que aquí están los recorridos sobre el
 * {@code ArrayList<Tour>}.
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

    /** Recorre la colección completa e imprime cada tour. */
    public void mostrarTodos() {
        if (tours.isEmpty()) {
            System.out.println("No hay tours cargados.");
            return;
        }
        for (Tour tour : tours) {
            System.out.println(tour);
        }
    }

    /** Búsqueda: muestra el/los tour(es) cuyo nombre coincide exactamente. */
    public void buscarPorNombre(String nombreBuscado) {
        boolean encontrado = false;
        for (Tour tour : tours) {
            if (tour.getNombre().equalsIgnoreCase(nombreBuscado)) {
                System.out.println("Encontrado: " + tour);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró un tour llamado \"" + nombreBuscado + "\".");
        }
    }

    /** Filtro: muestra todos los tours de un tipo determinado. */
    public void filtrarPorTipo(String tipoBuscado) {
        for (Tour tour : tours) {
            if (tour.getTipo().equalsIgnoreCase(tipoBuscado)) {
                System.out.println(tour);
            }
        }
    }

    /** Filtro: muestra los tours que aún tienen cupos disponibles. */
    public void filtrarConCupos() {
        for (Tour tour : tours) {
            if (tour.getCuposDisponibles() > 0) {
                System.out.println(tour);
            }
        }
    }

    /** Filtro: muestra los tours con precio menor o igual al máximo indicado. */
    public void filtrarPorPrecioMaximo(int precioMaximo) {
        for (Tour tour : tours) {
            if (tour.getPrecio() <= precioMaximo) {
                System.out.println(tour);
            }
        }
    }

    /**
     * Colección complementaria clave-valor: cuenta cuántos tours hay por cada
     * tipo. El conteo se arma en un {@link HashMap} y se devuelve dentro de un
     * {@link TreeMap} para que las claves queden ordenadas alfabéticamente.
     *
     * @return mapa "tipo -> cantidad" ordenado por tipo
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
