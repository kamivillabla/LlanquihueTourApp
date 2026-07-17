package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.ColaboradorExterno;
import model.Direccion;
import model.GuiaTuristico;
import model.Tour;
import model.Vehiculo;
import util.FormatoArchivoInvalidoException;
import util.RutInvalidoException;
import util.Validador;

/**
 * Lee los archivos de texto y arma los objetos del modelo. Si una línea viene
 * mal formada, registra un aviso (ver {@link #getAvisos()}) y sigue con las demás.
 */
public class GestorDatos {

    public static final String RUTA_GUIAS = "resources/guias.txt";
    public static final String RUTA_TOURS = "resources/tours.txt";
    public static final String RUTA_CLIENTES = "resources/clientes.txt";
    public static final String RUTA_INSCRIPCIONES = "resources/inscripciones.txt";
    public static final String RUTA_VEHICULOS = "resources/vehiculos.txt";
    public static final String RUTA_COLABORADORES = "resources/colaboradores.txt";

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

    /**
     * Carga los vehículos desde el archivo. Formato (6 campos separados por ";"):
     * codigo;nombre;activo;patente;tipo;capacidad
     * @param rutaArchivo ruta del archivo de vehículos a leer
     * @return los vehículos cargados correctamente (las líneas inválidas se omiten y quedan en {@link #getAvisos()})
     */
    public ArrayList<Vehiculo> cargarVehiculos(String rutaArchivo) {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
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

                    Vehiculo vehiculo = new Vehiculo(
                            partes[0].trim(), partes[1].trim(), Boolean.parseBoolean(partes[2].trim()),
                            partes[3].trim(), partes[4].trim(), Integer.parseInt(partes[5].trim()));

                    vehiculos.add(vehiculo);

                } catch (FormatoArchivoInvalidoException | IllegalArgumentException e) {
                    avisos.add("Vehículo omitido (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            avisos.add("Error al leer el archivo de vehículos: " + e.getMessage());
        }

        return vehiculos;
    }

    /**
     * Carga los colaboradores externos desde el archivo. Formato (5 campos separados por ";"):
     * codigo;nombre;activo;empresa;servicioPrestado
     * @param rutaArchivo ruta del archivo de colaboradores a leer
     * @return los colaboradores cargados correctamente (las líneas inválidas se omiten y quedan en {@link #getAvisos()})
     */
    public ArrayList<ColaboradorExterno> cargarColaboradores(String rutaArchivo) {
        ArrayList<ColaboradorExterno> colaboradores = new ArrayList<>();
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
                    if (!Validador.tieneCantidadCampos(partes, 5)) {
                        throw new FormatoArchivoInvalidoException(
                                "Se esperaban 5 campos y se encontraron " + partes.length + ".");
                    }

                    ColaboradorExterno colaborador = new ColaboradorExterno(
                            partes[0].trim(), partes[1].trim(), Boolean.parseBoolean(partes[2].trim()),
                            partes[3].trim(), partes[4].trim());

                    colaboradores.add(colaborador);

                } catch (FormatoArchivoInvalidoException | IllegalArgumentException e) {
                    avisos.add("Colaborador omitido (línea " + numeroLinea + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            avisos.add("Error al leer el archivo de colaboradores: " + e.getMessage());
        }

        return colaboradores;
    }

    /** Agrega la inscripción al final de {@value #RUTA_INSCRIPCIONES}, para que la reserva quede disponible en el próximo inicio. */
    public void guardarInscripcion(Cliente cliente, Tour tour) throws IOException {
        agregarLinea(RUTA_INSCRIPCIONES, String.join(";", cliente.getRut(), tour.getNombre()));
    }

    /** Agrega el guía al final de {@value #RUTA_GUIAS}, para que quede disponible en el próximo inicio. */
    public void guardarGuia(GuiaTuristico guia) throws IOException {
        Direccion direccion = guia.getDireccion();
        String linea = String.join(";",
                guia.getNombre(), guia.getApellido(), guia.getRut(), guia.getCorreo(),
                direccion.getCalle(), String.valueOf(direccion.getNumero()), direccion.getCiudad(), direccion.getRegion(),
                guia.getEspecialidad(), guia.getIdiomas(), String.valueOf(guia.getAniosExperiencia()),
                String.valueOf(guia.isDisponible()));
        agregarLinea(RUTA_GUIAS, linea);
    }

    /** Agrega el vehículo al final de {@value #RUTA_VEHICULOS}, para que quede disponible en el próximo inicio. */
    public void guardarVehiculo(Vehiculo vehiculo) throws IOException {
        String linea = String.join(";",
                vehiculo.getCodigo(), vehiculo.getNombre(), String.valueOf(vehiculo.isActivo()),
                vehiculo.getPatente(), vehiculo.getTipo(), String.valueOf(vehiculo.getCapacidad()));
        agregarLinea(RUTA_VEHICULOS, linea);
    }

    /** Agrega el colaborador al final de {@value #RUTA_COLABORADORES}, para que quede disponible en el próximo inicio. */
    public void guardarColaborador(ColaboradorExterno colaborador) throws IOException {
        String linea = String.join(";",
                colaborador.getCodigo(), colaborador.getNombre(), String.valueOf(colaborador.isActivo()),
                colaborador.getEmpresa(), colaborador.getServicioPrestado());
        agregarLinea(RUTA_COLABORADORES, linea);
    }

    /** Escribe una línea nueva al final del archivo indicado, sin borrar lo que ya tenía. */
    private void agregarLinea(String rutaArchivo, String linea) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo, StandardCharsets.UTF_8, true))) {
            escritor.write(linea);
            escritor.newLine();
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
    public Cliente buscarClientePorRut(ArrayList<Cliente> clientes, String rut) {
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
