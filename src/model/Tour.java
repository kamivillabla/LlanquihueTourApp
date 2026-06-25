package model;

import java.util.ArrayList;

/**
 * Un tour de la agencia. Tiene un {@link Guia} asignado y una lista de
 * {@link Cliente} inscritos. Los cupos disponibles se calculan restando los
 * inscritos a la capacidad total.
 */
public class Tour {

    private String nombre;
    private String tipo;
    private String destino;
    private int precio;
    private int cupos;
    private Guia guia;
    private ArrayList<Cliente> inscritos;

    public Tour(String nombre, String tipo, String destino, int precio, int cupos, Guia guia) {
        setNombre(nombre);
        setTipo(tipo);
        setDestino(destino);
        setPrecio(precio);
        setCupos(cupos);
        setGuia(guia);
        this.inscritos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del tour no puede estar vacío.");
        }
        if (!nombre.matches("[\\p{L}\\p{N} .,'-]+")) {
            throw new IllegalArgumentException("El nombre del tour contiene caracteres no permitidos.");
        }
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de tour no puede estar vacío.");
        }
        if (!tipo.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El tipo de tour solo puede contener letras y espacios.");
        }
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        if (!destino.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El destino solo puede contener letras y espacios.");
        }
        this.destino = destino;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (precio > 1_000_000) {
            throw new IllegalArgumentException("El precio no puede superar $1.000.000.");
        }
        this.precio = precio;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        if (cupos < 0) {
            throw new IllegalArgumentException("Los cupos no pueden ser negativos.");
        }
        if (cupos > 100) {
            throw new IllegalArgumentException("Los cupos no pueden superar 100.");
        }
        this.cupos = cupos;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        if (guia == null) {
            throw new IllegalArgumentException("El tour debe tener un guía asignado.");
        }
        this.guia = guia;
    }

    public ArrayList<Cliente> getInscritos() {
        return inscritos;
    }

    /** Cupos que aún quedan libres (capacidad total menos clientes inscritos). */
    public int getCuposDisponibles() {
        return cupos - inscritos.size();
    }

    /** Inscribe un cliente si no está ya inscrito y todavía quedan cupos. */
    public void inscribirCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (estaInscrito(cliente)) {
            throw new IllegalStateException(
                    "El cliente " + cliente.getNombre() + " " + cliente.getApellido()
                            + " ya está inscrito en el tour \"" + nombre + "\".");
        }
        if (getCuposDisponibles() <= 0) {
            throw new IllegalStateException("El tour \"" + nombre + "\" no tiene cupos disponibles.");
        }
        inscritos.add(cliente);
    }

    /** Indica si el cliente ya está inscrito en el tour, comparando por RUT. */
    public boolean estaInscrito(Cliente cliente) {
        for (Cliente inscrito : inscritos) {
            if (inscrito.getRut().equalsIgnoreCase(cliente.getRut())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tour: " + nombre +
                " | Tipo: " + tipo +
                " | Destino: " + destino +
                " | Precio: $" + precio +
                " | Cupos: " + getCuposDisponibles() + " disponibles de " + cupos +
                " | Guía: " + guia.getNombre() + " " + guia.getApellido() +
                " (" + guia.getEspecialidad() + ")";
    }
}
