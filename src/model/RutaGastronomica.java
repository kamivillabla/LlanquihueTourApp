package model;

/** Ruta gastronómica; es un servicio turístico con un número de paradas. */
public class RutaGastronomica extends ServicioTuristico {

    private int numeroDeParadas;

    public RutaGastronomica(String nombre, int duracionHoras, int numeroDeParadas) {
        super(nombre, duracionHoras);
        setNumeroDeParadas(numeroDeParadas);
    }

    public int getNumeroDeParadas() {
        return numeroDeParadas;
    }

    public void setNumeroDeParadas(int numeroDeParadas) {
        if (numeroDeParadas <= 0) {
            throw new IllegalArgumentException("El número de paradas debe ser mayor a 0.");
        }
        if (numeroDeParadas > 50) {
            throw new IllegalArgumentException("El número de paradas no puede superar 50.");
        }
        this.numeroDeParadas = numeroDeParadas;
    }

    @Override
    public String mostrarInformacion() {
        return "Ruta gastronómica: " + getNombre() + "\n"
                + "Duración: " + getDuracionHoras() + " horas\n"
                + "Número de paradas: " + numeroDeParadas + "\n"
                + "----------------------------------";
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Número de paradas: " + numeroDeParadas;
    }
}
