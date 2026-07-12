package model;

/** Excursión cultural; es un servicio turístico con un lugar histórico. */
public class ExcursionCultural extends ServicioTuristico {

    private String lugarHistorico;

    public ExcursionCultural(String nombre, int duracionHoras, String lugarHistorico) {
        super(nombre, duracionHoras);
        setLugarHistorico(lugarHistorico);
    }

    public String getLugarHistorico() {
        return lugarHistorico;
    }

    public void setLugarHistorico(String lugarHistorico) {
        if (lugarHistorico == null || lugarHistorico.isBlank()) {
            throw new IllegalArgumentException("El lugar histórico no puede estar vacío.");
        }
        if (!lugarHistorico.matches("[\\p{L} .,'-]+")) {
            throw new IllegalArgumentException("Usa solo letras, espacios, puntos, comas, apóstrofes o guiones.");
        }
        this.lugarHistorico = lugarHistorico;
    }

    @Override
    public String mostrarInformacion() {
        return "Excursión cultural: " + getNombre() + "\n"
                + "Duración: " + getDuracionHoras() + " horas\n"
                + "Lugar histórico: " + lugarHistorico + "\n"
                + "----------------------------------";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Lugar histórico: " + lugarHistorico;
    }
}
