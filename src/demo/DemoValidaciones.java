package demo;

import java.util.ArrayList;
import model.Cliente;
import model.Direccion;
import model.Guia;
import model.Tour;
import util.RutInvalidoException;

/**
 * Demostración de las validaciones y excepciones del sistema. Provoca errores a
 * propósito (dentro de try-catch) para evidenciar que las reglas de negocio
 * funcionan. Se separó de {@code Main} para mantener el punto de entrada limpio.
 */
public class DemoValidaciones {

    /**
     * Ejecuta todos los casos de validación y muestra por consola cómo el
     * sistema rechaza los datos inválidos.
     * @param clientes
     * @param tours
     * @param guias
     */
    public static void ejecutar(ArrayList<Guia> guias, ArrayList<Tour> tours,
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

    private static Tour buscarTourSinCupos(ArrayList<Tour> tours) {
        for (Tour tour : tours) {
            if (tour.getCuposDisponibles() <= 0) {
                return tour;
            }
        }
        return null;
    }

    private static Tour buscarTourConInscritos(ArrayList<Tour> tours) {
        for (Tour tour : tours) {
            if (!tour.getInscritos().isEmpty()) {
                return tour;
            }
        }
        return null;
    }
}
