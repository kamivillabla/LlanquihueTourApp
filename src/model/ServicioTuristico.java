package model;

/**
 * Clase base de los servicios turísticos, con los datos comunes a todos:
 * nombre y duración.
 */
public class ServicioTuristico {

    private String nombre;
    private int duracionHoras;

    /**
     * @param nombre        nombre del servicio
     * @param duracionHoras duración en horas
     */
    public ServicioTuristico(String nombre, int duracionHoras) {
        setNombre(nombre);
        setDuracionHoras(duracionHoras);
    }

    /** @return el nombre del servicio */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre nombre del servicio */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del servicio no puede estar vacío.");
        }
        if (!nombre.matches("[\\p{L}\\p{N} .,'-]+")) {
            throw new IllegalArgumentException("El nombre del servicio contiene caracteres no permitidos.");
        }
        this.nombre = nombre;
    }

    /** @return la duración en horas */
    public int getDuracionHoras() {
        return duracionHoras;
    }

    /** @param duracionHoras duración en horas */
    public void setDuracionHoras(int duracionHoras) {
        if (duracionHoras <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 horas.");
        }
        if (duracionHoras > 24) {
            throw new IllegalArgumentException("La duración no puede superar 24 horas.");
        }
        this.duracionHoras = duracionHoras;
    }

    /** Muestra por consola la información del servicio; las subclases la sobrescriben. */
    public void mostrarInformacion() {
        System.out.println("Servicio turístico: " + nombre);
        System.out.println("Duración: " + duracionHoras + " horas");
        System.out.println("----------------------------------");
    }

    @Override
    public String toString() {
        return "Servicio turístico: " + nombre +
                " | Duración: " + duracionHoras + " horas";
    }
}
