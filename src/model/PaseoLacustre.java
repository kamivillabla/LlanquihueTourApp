package model;

/**
 * Paseo lacustre por el lago. Es un servicio turístico y suma la embarcación
 * en que se navega.
 */
public class PaseoLacustre extends ServicioTuristico {

    private String tipoEmbarcacion;

    public PaseoLacustre(String nombre, int duracionHoras, String tipoEmbarcacion) {
        super(nombre, duracionHoras);
        setTipoEmbarcacion(tipoEmbarcacion);
    }

    public String getTipoEmbarcacion() {
        return tipoEmbarcacion;
    }

    public void setTipoEmbarcacion(String tipoEmbarcacion) {
        if (tipoEmbarcacion == null || tipoEmbarcacion.isBlank()) {
            throw new IllegalArgumentException("El tipo de embarcación no puede estar vacío.");
        }
        if (!tipoEmbarcacion.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El tipo de embarcación solo puede contener letras y espacios.");
        }
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Tipo de embarcación: " + tipoEmbarcacion;
    }
}
