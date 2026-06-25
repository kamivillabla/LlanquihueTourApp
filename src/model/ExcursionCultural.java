package model;

/**
 * Excursión cultural. Es un servicio turístico y suma el lugar histórico que
 * se visita.
 */
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
            throw new IllegalArgumentException("El lugar histórico contiene caracteres no permitidos.");
        }
        this.lugarHistorico = lugarHistorico;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Lugar histórico: " + lugarHistorico;
    }
}
