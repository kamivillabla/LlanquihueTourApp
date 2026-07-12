package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Direccion;
import model.GuiaTuristico;
import model.Tour;
import util.FormatoArchivoInvalidoException;
import util.RutInvalidoException;
import util.Validador;

/**
 * Lee los archivos de texto y arma los objetos del modelo. Si una línea viene
 * mal formada, registra un aviso (ver {@link #getAvisos()}) y sigue con las demás.
 */
public class GestorDatos {

    private final List<String> avisos = new ArrayList<>();

    /** @return los avisos acumulados durante la carga (líneas omitidas, errores de archivo) */
    public List<String> getAvisos() {
        return avisos;
    }

    /**
     * Carga los guías desde el archivo. Formato (12 campos separados por ";"):
     * nombre;apellido;rut;correo;calle;numero;ciudad;region;especialidad;idiomas;aniosExperiencia;disponible
     * @param rutaArchivo ruta del archivo de guías a leer
     * @return los guías cargados correctamente (las líneas inválidas se omiten y quedan en {@link #getAvisos()})
     */
    public ArrayList<GuiaTuristico> cargarGuias(String rutaArchivo) {
        ArrayList<GuiaTuristico> guias = new ArrayList<>();
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

                    GuiaTuristico guia = new GuiaTuristico(
                            partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim(),
                            direccion,
                            partes[8].trim(), partes[9].trim(),
                            Integer.parseInt(partes[10].trim()),
                            Boolean.parseBoolean(partes[11].trim()));

                    guias.add(guia);

                } catch (FormatoArchivoInvalidoException | RutInvalidoException
                         | IllegalArgumentException e) {
                    avisos.add("Guía omitida (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            avisos.add("Error al leer el archivo de guías: " + e.getMessage());
        }

        return guias;
    }

    /**
     * Carga los tours y a cada uno le asigna su guía, buscándolo por RUT en la
     * lista ya cargada. Formato (6 campos): nombre;tipo;destino;precio;cupos;rutGuia
     * @param rutaArchivo ruta del archivo de tours a leer
     * @param guias guías ya cargados, usados para resolver el RUT de cada tour
     * @return los tours cargados correctamente (las líneas inválidas se omiten y quedan en {@link #getAvisos()})
     */
    public ArrayList<Tour> cargarTours(String rutaArchivo, ArrayList<GuiaTuristico> guias) {
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
                    GuiaTuristico guia = buscarGuiaPorRut(guias, rutGuia);
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
                    avisos.add("Tour omitido (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            avisos.add("Error al leer el archivo de tours: " + e.getMessage());
        }

        return tours;
    }

    /**
     * Carga los clientes desde el archivo. Formato (10 campos):
     * nombre;apellido;rut;correo;calle;numero;ciudad;region;nacionalidad;tipoTurismo
     * @param rutaArchivo ruta del archivo de clientes a leer
     * @return los clientes cargados correctamente (las líneas inválidas se omiten y quedan en {@link #getAvisos()})
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
                    avisos.add("Cliente omitido (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            avisos.add("Error al leer el archivo de clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Inscribe cada cliente en su tour según el archivo. Si falta el cliente o
     * el tour, o no hay cupos, registra un aviso y sigue. Formato (2 campos): rutCliente;nombreTour
     * @param rutaArchivo ruta del archivo de inscripciones a leer
     * @param tours tours ya cargados, usados para resolver el tour de cada inscripción
     * @param clientes clientes ya cargados, usados para resolver el cliente de cada inscripción
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
                    avisos.add("Inscripción omitida (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            avisos.add("Error al leer el archivo de inscripciones: " + e.getMessage());
        }
    }

    /** Busca un guía por su RUT dentro de la lista. Retorna null si no existe. */
    private GuiaTuristico buscarGuiaPorRut(ArrayList<GuiaTuristico> guias, String rut) {
        for (GuiaTuristico guia : guias) {
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
