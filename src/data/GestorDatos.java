package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import model.Cliente;
import model.Direccion;
import model.Guia;
import model.Tour;
import util.FormatoArchivoInvalidoException;
import util.RutInvalidoException;
import util.Validador;

/**
 * Capa de datos: lee los archivos de texto y transforma cada línea en objetos
 * del modelo ({@link Guia}, {@link Tour}, {@link Cliente}).
 *
 * <p>Cada línea se separa con {@code split(";")}, se valida la cantidad de
 * campos y se convierten los números con {@code parseInt}. Si una línea viene
 * mal formada, se captura la excepción, se avisa por consola y se continúa con
 * las demás (una línea inválida no detiene la carga completa).
 */
public class GestorDatos {

    /**
     * Carga los guías desde un archivo .txt/.csv.
     * Formato esperado (12 campos separados por ";"):
     * nombre;apellido;rut;correo;calle;numero;ciudad;region;especialidad;idiomas;aniosExperiencia;disponible
     *
     * @param rutaArchivo ruta del archivo de guías a leer
     * @return lista de guías cargados (omite las líneas mal formadas)
     */
    public ArrayList<Guia> cargarGuias(String rutaArchivo) {
        ArrayList<Guia> guias = new ArrayList<>();
        int numeroLinea = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    String[] partes = linea.split(";");
                    if (!Validador.tieneCantidadCampos(partes, 12)) {
                        throw new FormatoArchivoInvalidoException(
                                "Se esperaban 12 campos y se encontraron " + partes.length + ".");
                    }

                    Direccion direccion = new Direccion(
                            partes[4].trim(),
                            Integer.parseInt(partes[5].trim()),
                            partes[6].trim(),
                            partes[7].trim());

                    Guia guia = new Guia(
                            partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                            direccion,
                            partes[8].trim(), partes[9].trim(),
                            Integer.parseInt(partes[10].trim()),
                            Boolean.parseBoolean(partes[11].trim()));

                    guias.add(guia);

                } catch (FormatoArchivoInvalidoException | RutInvalidoException
                         | IllegalArgumentException e) {
                    System.out.println("  [Aviso] Guía omitida (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de guías: " + e.getMessage());
        }

        return guias;
    }

    /**
     * Carga los tours desde un archivo .txt/.csv y le asigna a cada uno su guía
     * (composición), buscándolo por RUT dentro de la lista de guías ya cargada.
     * Formato esperado (6 campos): nombre;tipo;destino;precio;cupos;rutGuia
     *
     * @param rutaArchivo ruta del archivo de tours a leer
     * @param guias       lista de guías ya cargados, para asignar a cada tour
     * @return lista de tours cargados (omite las líneas mal formadas)
     */
    public ArrayList<Tour> cargarTours(String rutaArchivo, ArrayList<Guia> guias) {
        ArrayList<Tour> tours = new ArrayList<>();
        int numeroLinea = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    String[] partes = linea.split(";");
                    if (!Validador.tieneCantidadCampos(partes, 6)) {
                        throw new FormatoArchivoInvalidoException(
                                "Se esperaban 6 campos y se encontraron " + partes.length + ".");
                    }

                    String rutGuia = partes[5].trim();
                    Guia guia = buscarGuiaPorRut(guias, rutGuia);
                    if (guia == null) {
                        throw new FormatoArchivoInvalidoException(
                                "No existe un guía con RUT " + rutGuia + ".");
                    }

                    Tour tour = new Tour(
                            partes[0].trim(), partes[1].trim(), partes[2].trim(),
                            Integer.parseInt(partes[3].trim()),
                            Integer.parseInt(partes[4].trim()),
                            guia);

                    tours.add(tour);

                } catch (FormatoArchivoInvalidoException | IllegalArgumentException e) {
                    System.out.println("  [Aviso] Tour omitido (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de tours: " + e.getMessage());
        }

        return tours;
    }

    /**
     * Carga los clientes desde un archivo .txt/.csv.
     * Formato esperado (10 campos):
     * nombre;apellido;rut;correo;calle;numero;ciudad;region;nacionalidad;tipoTurismo
     *
     * @param rutaArchivo ruta del archivo de clientes a leer
     * @return lista de clientes cargados (omite las líneas mal formadas)
     */
    public ArrayList<Cliente> cargarClientes(String rutaArchivo) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        int numeroLinea = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    String[] partes = linea.split(";");
                    if (!Validador.tieneCantidadCampos(partes, 10)) {
                        throw new FormatoArchivoInvalidoException(
                                "Se esperaban 10 campos y se encontraron " + partes.length + ".");
                    }

                    Direccion direccion = new Direccion(
                            partes[4].trim(),
                            Integer.parseInt(partes[5].trim()),
                            partes[6].trim(),
                            partes[7].trim());

                    Cliente cliente = new Cliente(
                            partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                            direccion,
                            partes[8].trim(), partes[9].trim());

                    clientes.add(cliente);

                } catch (FormatoArchivoInvalidoException | RutInvalidoException
                         | IllegalArgumentException e) {
                    System.out.println("  [Aviso] Cliente omitido (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Carga las inscripciones desde un archivo .txt/.csv e inscribe cada cliente
     * en su tour (relación Tour - Cliente). Si no hay cupos o no se encuentra el
     * cliente o el tour, se avisa por consola y se continúa.
     * Formato esperado (2 campos): rutCliente;nombreTour
     *
     * @param rutaArchivo ruta del archivo de inscripciones a leer
     * @param tours       lista de tours donde se inscribirán los clientes
     * @param clientes    lista de clientes disponibles para inscribir
     */
    public void cargarInscripciones(String rutaArchivo, ArrayList<Tour> tours, ArrayList<Cliente> clientes) {
        int numeroLinea = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    String[] partes = linea.split(";");
                    if (!Validador.tieneCantidadCampos(partes, 2)) {
                        throw new FormatoArchivoInvalidoException(
                                "Se esperaban 2 campos y se encontraron " + partes.length + ".");
                    }

                    String rutCliente = partes[0].trim();
                    String nombreTour = partes[1].trim();

                    Cliente cliente = buscarClientePorRut(clientes, rutCliente);
                    if (cliente == null) {
                        throw new FormatoArchivoInvalidoException("No existe un cliente con RUT " + rutCliente + ".");
                    }

                    Tour tour = buscarTourPorNombre(tours, nombreTour);
                    if (tour == null) {
                        throw new FormatoArchivoInvalidoException("No existe un tour llamado \"" + nombreTour + "\".");
                    }

                    tour.inscribirCliente(cliente);

                } catch (FormatoArchivoInvalidoException | IllegalStateException | IllegalArgumentException e) {
                    System.out.println("  [Aviso] Inscripción omitida (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de inscripciones: " + e.getMessage());
        }
    }

    /** Busca un guía por su RUT dentro de la lista. Retorna null si no existe. */
    private Guia buscarGuiaPorRut(ArrayList<Guia> guias, String rut) {
        for (Guia guia : guias) {
            if (guia.getRut().equalsIgnoreCase(rut)) {
                return guia;
            }
        }
        return null;
    }

    /** Busca un cliente por su RUT dentro de la lista. Retorna null si no existe. */
    private Cliente buscarClientePorRut(ArrayList<Cliente> clientes, String rut) {
        for (Cliente cliente : clientes) {
            if (cliente.getRut().equalsIgnoreCase(rut)) {
                return cliente;
            }
        }
        return null;
    }

    /** Busca un tour por su nombre dentro de la lista. Retorna null si no existe. */
    private Tour buscarTourPorNombre(ArrayList<Tour> tours, String nombre) {
        for (Tour tour : tours) {
            if (tour.getNombre().equalsIgnoreCase(nombre)) {
                return tour;
            }
        }
        return null;
    }
}
