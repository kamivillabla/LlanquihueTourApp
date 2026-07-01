package model;

/** Ruta gastronómica; es un servicio turístico con un número de paradas. */
public class RutaGastronomica extends ServicioTuristico {

    private int numeroDeParadas;

    /**
     * @param nombre          nombre de la ruta
     * @param duracionHoras   duración en horas
     * @param numeroDeParadas cantidad de paradas
     */
    public RutaGastronomica(String nombre, int duracionHoras, int numeroDeParadas) {
        super(nombre, duracionHoras);
        setNumeroDeParadas(numeroDeParadas);
    }

    /** @return el número de paradas */
    public int getNumeroDeParadas() {
        return numeroDeParadas;
    }

    /** @param numeroDeParadas cantidad de paradas */
    public void setNumeroDeParadas(int numeroDeParadas) {
        if (numeroDeParadas <= 0) {
            throw new IllegalArgumentException("El número de paradas debe ser mayor a 0.");
        }
        if (numeroDeParadas > 50) {
            throw new IllegalArgumentException("El número de paradas no puede superar 50.");
        }
        this.numeroDeParadas = numeroDeParadas;
    }

    /** Muestra la información propia de la ruta gastronómica. */
    @Override
    public void mostrarInformacion() {
        System.out.println("Ruta gastronómica: " + getNombre());
        System.out.println("Duración: " + getDuracionHoras() + " horas");
        System.out.println("Número de paradas: " + numeroDeParadas);
        System.out.println("----------------------------------");
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Número de paradas: " + numeroDeParadas;
    }
}
