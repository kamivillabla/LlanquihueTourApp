package model;

import util.RutInvalidoException;

/** Clase base que representa a cualquier persona vinculada a Llanquihue Tour. */
public class Persona {

    private String nombre;
    private String apellido;
    private String rut;
    private String correo;
    private Direccion direccion;

    public Persona(String nombre, String apellido, String rut, String correo, Direccion direccion)
            throws RutInvalidoException {
        setNombre(nombre);
        setApellido(apellido);
        setRut(rut);
        setCorreo(correo);
        setDireccion(direccion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!nombre.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        if (!apellido.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El apellido solo puede contener letras y espacios.");
        }
        this.apellido = apellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) throws RutInvalidoException {
        if (rut == null || !rut.matches("[0-9]+-[0-9kK]")) {
            throw new RutInvalidoException(
                    "RUT inválido: '" + rut + "'. El formato debe ser XXXXXXXX-X.");
        }
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || !correo.matches("[^@]+@[^@]+\\.[^@]+")) {
            throw new IllegalArgumentException("El correo debe tener el formato usuario@dominio.ext.");
        }
        this.correo = correo;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        if (direccion == null) {
            throw new IllegalArgumentException("La dirección no puede ser nula.");
        }
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " " + apellido +
                " | RUT: " + rut +
                " | Correo: " + correo +
                " | Dirección: " + direccion;
    }
}
