package model;

import util.RutInvalidoException;

/** Clase base que representa a cualquier persona vinculada a Llanquihue Tour. */
public class Persona {

    /** Patrón de nombre/apellido: solo letras y espacios. */
    public static final String PATRON_NOMBRE = "[\\p{L} ]+";
    public static final int LARGO_MAXIMO_NOMBRE = 50;
    /** Patrón de RUT chileno: 7 u 8 dígitos, guion y dígito verificador. */
    public static final String PATRON_RUT = "[0-9]{7,8}-[0-9kK]";
    /** Patrón de correo: usuario@dominio.ext, sin espacios. */
    public static final String PATRON_CORREO = "[^\\s@]+@[^\\s@]+\\.[^\\s@]+";
    public static final int LARGO_MAXIMO_CORREO = 60;

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
            throw new IllegalArgumentException("Falta el nombre.");
        }
        if (nombre.length() > LARGO_MAXIMO_NOMBRE) {
            throw new IllegalArgumentException("Ese nombre es muy largo (máximo " + LARGO_MAXIMO_NOMBRE + " caracteres).");
        }
        if (!nombre.matches(PATRON_NOMBRE)) {
            throw new IllegalArgumentException("El nombre solo puede tener letras, sin números ni símbolos.");
        }
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("Falta el apellido.");
        }
        if (apellido.length() > LARGO_MAXIMO_NOMBRE) {
            throw new IllegalArgumentException("Ese apellido es muy largo (máximo " + LARGO_MAXIMO_NOMBRE + " caracteres).");
        }
        if (!apellido.matches(PATRON_NOMBRE)) {
            throw new IllegalArgumentException("El apellido solo puede tener letras, sin números ni símbolos.");
        }
        this.apellido = apellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) throws RutInvalidoException {
        if (rut == null || !rut.matches(PATRON_RUT)) {
            throw new RutInvalidoException(
                    "El RUT debe tener 7 u 8 números, un guion, y terminar en un número o la letra K. Ej: 12345678-9.");
        }
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("Falta el correo.");
        }
        if (correo.length() > LARGO_MAXIMO_CORREO) {
            throw new IllegalArgumentException("Ese correo es muy largo (máximo " + LARGO_MAXIMO_CORREO + " caracteres).");
        }
        if (!correo.matches(PATRON_CORREO)) {
            throw new IllegalArgumentException("Escribe un correo válido, por ejemplo: usuario@gmail.com");
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
