package model;

/** Paseo lacustre; es un servicio turístico con un tipo de embarcación. */
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
        if (!tipoEmbarcacion.matches("[\\p{L}\\p{N} .,'-]+")) {
            throw new IllegalArgumentException("Usa solo letras, números, espacios, puntos, comas, apóstrofes o guiones.");
        }
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    @Override
    public String mostrarInformacion() {
        return "Paseo lacustre: " + getNombre() + "\n"
                + "Duración: " + getDuracionHoras() + " horas\n"
                + "Tipo de embarcación: " + tipoEmbarcacion + "\n"
                + "----------------------------------";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Tipo de embarcación: " + tipoEmbarcacion;
    }
}
