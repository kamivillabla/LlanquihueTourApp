package model;

/** Excursión cultural; es un servicio turístico con un lugar histórico. */
public class ExcursionCultural extends ServicioTuristico {

    private String lugarHistorico;

    /**
     * @param nombre         nombre de la excursión
     * @param duracionHoras  duración en horas
     * @param lugarHistorico lugar histórico que se visita
     */
    public ExcursionCultural(String nombre, int duracionHoras, String lugarHistorico) {
        super(nombre, duracionHoras);
        setLugarHistorico(lugarHistorico);
    }

    /** @return el lugar histórico */
    public String getLugarHistorico() {
        return lugarHistorico;
    }

    /** @param lugarHistorico lugar histórico que se visita */
    public void setLugarHistorico(String lugarHistorico) {
        if (lugarHistorico == null || lugarHistorico.isBlank()) {
            throw new IllegalArgumentException("El lugar histórico no puede estar vacío.");
        }
        if (!lugarHistorico.matches("[\\p{L} .,'-]+")) {
            throw new IllegalArgumentException("El lugar histórico contiene caracteres no permitidos.");
        }
        this.lugarHistorico = lugarHistorico;
    }

    /** Muestra la información propia de la excursión cultural. */
    @Override
    public void mostrarInformacion() {
        System.out.println("Excursión cultural: " + getNombre());
        System.out.println("Duración: " + getDuracionHoras() + " horas");
        System.out.println("Lugar histórico: " + lugarHistorico);
        System.out.println("----------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Lugar histórico: " + lugarHistorico;
    }
}
