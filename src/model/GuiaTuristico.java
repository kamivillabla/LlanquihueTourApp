package model;

import util.RutInvalidoException;

/**
 * Guía turístico de la agencia. Es una Persona (nombre, rut, correo,
 * dirección) y además es Registrable: puede asignarse a Tours y también
 * gestionarse desde la colección de entidades registrables de la agencia.
 */
public class GuiaTuristico extends Persona implements Registrable {

    /** Patrón de especialidad: solo letras y espacios. */
    public static final String PATRON_ESPECIALIDAD = "[\\p{L} ]+";
    public static final int LARGO_MAXIMO_ESPECIALIDAD = 40;
    /** Patrón de idiomas: letras, espacios y comas. */
    public static final String PATRON_IDIOMAS = "[\\p{L} ,]+";
    public static final int LARGO_MAXIMO_IDIOMAS = 60;

    private String especialidad;
    private String idiomas;
    private int aniosExperiencia;
    private boolean disponible;

    public GuiaTuristico(String nombre, String apellido, String rut, String correo,
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
            throw new IllegalArgumentException("Falta la especialidad.");
        }
        if (especialidad.length() > LARGO_MAXIMO_ESPECIALIDAD) {
            throw new IllegalArgumentException("Esa especialidad es muy larga (máximo " + LARGO_MAXIMO_ESPECIALIDAD + " caracteres).");
        }
        if (!especialidad.matches(PATRON_ESPECIALIDAD)) {
            throw new IllegalArgumentException("La especialidad solo puede tener letras, sin números ni símbolos.");
        }
        this.especialidad = especialidad;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        if (idiomas == null || idiomas.isBlank()) {
            throw new IllegalArgumentException("Falta al menos un idioma.");
        }
        if (idiomas.length() > LARGO_MAXIMO_IDIOMAS) {
            throw new IllegalArgumentException("Ese texto es muy largo (máximo " + LARGO_MAXIMO_IDIOMAS + " caracteres).");
        }
        if (!idiomas.matches(PATRON_IDIOMAS)) {
            throw new IllegalArgumentException("Los idiomas solo pueden tener letras y comas (ej: Español, Inglés).");
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
    public String mostrarResumen() {
        return "Guía turístico: " + getNombre() + " " + getApellido() + " (" + getRut() + ")"
                + " | Especialidad: " + especialidad
                + " | Idiomas: " + idiomas
                + " | Experiencia: " + aniosExperiencia + " años"
                + " | Disponible: " + (disponible ? "sí" : "no");
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
