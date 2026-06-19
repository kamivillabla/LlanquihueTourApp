package model;

import util.RutInvalidoException;

/** Representa a un cliente que contrata servicios turísticos de Llanquihue Tour. */
public class Cliente extends Persona {

    private String nacionalidad;
    private String tipoTurismo;

    public Cliente(String nombre, String apellido, String rut, String correo,
                   Direccion direccion, String nacionalidad, String tipoTurismo)
            throws RutInvalidoException {
        super(nombre, apellido, rut, correo, direccion);
        setNacionalidad(nacionalidad);
        setTipoTurismo(tipoTurismo);
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null || nacionalidad.isBlank()) {
            throw new IllegalArgumentException("La nacionalidad no puede estar vacía.");
        }
        if (!nacionalidad.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("La nacionalidad solo puede contener letras y espacios.");
        }
        this.nacionalidad = nacionalidad;
    }

    public String getTipoTurismo() {
        return tipoTurismo;
    }

    public void setTipoTurismo(String tipoTurismo) {
        if (tipoTurismo == null || tipoTurismo.isBlank()) {
            throw new IllegalArgumentException("El tipo de turismo no puede estar vacío.");
        }
        if (!tipoTurismo.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El tipo de turismo solo puede contener letras y espacios.");
        }
        this.tipoTurismo = tipoTurismo;
    }

    @Override
    public String toString() {
        return """
               --- Cliente ---
               """ + super.toString() +
                "\nNacionalidad: " + nacionalidad +
                " | Tipo de turismo: " + tipoTurismo;
    }
}
