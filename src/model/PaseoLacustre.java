package model;

/** Paseo lacustre; es un servicio turístico con un tipo de embarcación. */
public class PaseoLacustre extends ServicioTuristico {

    private String tipoEmbarcacion;

    /**
     * @param nombre          nombre del paseo
     * @param duracionHoras   duración en horas
     * @param tipoEmbarcacion embarcación en que se navega
     */
    public PaseoLacustre(String nombre, int duracionHoras, String tipoEmbarcacion) {
        super(nombre, duracionHoras);
        setTipoEmbarcacion(tipoEmbarcacion);
    }

    /** @return el tipo de embarcación */
    public String getTipoEmbarcacion() {
        return tipoEmbarcacion;
    }

    /** @param tipoEmbarcacion embarcación en que se navega */
    public void setTipoEmbarcacion(String tipoEmbarcacion) {
        if (tipoEmbarcacion == null || tipoEmbarcacion.isBlank()) {
            throw new IllegalArgumentException("El tipo de embarcación no puede estar vacío.");
        }
        if (!tipoEmbarcacion.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El tipo de embarcación solo puede contener letras y espacios.");
        }
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    /** Muestra la información propia del paseo lacustre. */
    @Override
    public void mostrarInformacion() {
        System.out.println("Paseo lacustre: " + getNombre());
        System.out.println("Duración: " + getDuracionHoras() + " horas");
        System.out.println("Tipo de embarcación: " + tipoEmbarcacion);
        System.out.println("----------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Tipo de embarcación: " + tipoEmbarcacion;
    }
}
