package model;

import util.RutInvalidoException;

/** Representa a un guía turístico de Llanquihue Tour. */
public class Guia extends Persona {

    private String especialidad;
    private String idiomas;
    private int aniosExperiencia;
    private boolean disponible;

    public Guia(String nombre, String apellido, String rut, String correo,
                Direccion direccion, String especialidad, String idiomas,
                int aniosExperiencia, boolean disponible) throws RutInvalidoException {
        super(nombre, apellido, rut, correo, direccion);
        setEspecialidad(especialidad);
        setIdiomas(idiomas);
        setAniosExperiencia(aniosExperiencia);
        this.disponible = disponible;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        if (!especialidad.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("La especialidad solo puede contener letras y espacios.");
        }
        this.especialidad = especialidad;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        if (idiomas == null || idiomas.isBlank()) {
            throw new IllegalArgumentException("Los idiomas no pueden estar vacíos.");
        }
        if (!idiomas.matches("[\\p{L} ,]+")) {
            throw new IllegalArgumentException("Los idiomas solo pueden contener letras, espacios y comas.");
        }
        this.idiomas = idiomas;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        if (aniosExperiencia < 0 || aniosExperiencia > 70) {
            throw new IllegalArgumentException("Los años de experiencia deben estar entre 0 y 70.");
        }
        this.aniosExperiencia = aniosExperiencia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return """
               --- Guía Turístico ---
               """ + super.toString() +
                "\nEspecialidad: " + especialidad +
                " | Idiomas: " + idiomas +
                " | Experiencia: " + aniosExperiencia + " años" +
                " | Estado: " + (disponible ? "Disponible" : "No disponible");
    }
}
