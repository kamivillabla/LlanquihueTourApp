package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import model.Tour;

/**
 * Encargada de leer el archivo de tours y transformar cada línea en un objeto Tour.
 */
public class GestorDatos {

    /**
     * Lee el archivo indicado línea por línea, separa los datos con ";"
     * y crea un Tour por cada línea no vacía.
     *
     * @param rutaArchivo ruta del archivo de tours (ej. "resources/tours.txt")
     * @return lista de tours cargados desde el archivo
     */
    public ArrayList<Tour> cargarTours(String rutaArchivo) {
        ArrayList<Tour> tours = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }
                String[] partes = linea.split(";");

                String nombre = partes[0];
                String tipo = partes[1];
                String destino = partes[2];
                int precio = Integer.parseInt(partes[3]);
                int cupos = Integer.parseInt(partes[4]);

                tours.add(new Tour(nombre, tipo, destino, precio, cupos));
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de tours: " + e.getMessage());
        }

        return tours;
    }
}
