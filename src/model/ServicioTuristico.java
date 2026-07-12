package model;

/**
 * Clase base de los servicios turísticos, con los datos comunes a todos:
 * nombre y duración.
 */
public class ServicioTuristico {

    private String nombre;
    private int duracionHoras;

    public ServicioTuristico(String nombre, int duracionHoras) {
        setNombre(nombre);
        setDuracionHoras(duracionHoras);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio no puede estar vacío.");
        }
        if (!nombre.matches("[\\p{L}\\p{N} .,'-]+")) {
            throw new IllegalArgumentException("Usa solo letras, números, espacios, puntos, comas, apóstrofes o guiones.");
        }
        this.nombre = nombre;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        if (duracionHoras <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 horas.");
        }
        if (duracionHoras > 24) {
            throw new IllegalArgumentException("La duración no puede superar 24 horas.");
        }
        this.duracionHoras = duracionHoras;
    }

    /** Las subclases sobrescriben este método (polimorfismo). */
    public String mostrarInformacion() {
        return "Servicio turístico: " + nombre + "\n"
                + "Duración: " + duracionHoras + " horas\n"
                + "----------------------------------";
    }

    @Override
    public String toString() {
        return "Servicio turístico: " + nombre +
                " | Duración: " + duracionHoras + " horas";
    }
}
