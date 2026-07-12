package model;

/** Vehículo registrable de la agencia (van, bus, lancha, etc.). */
public class Vehiculo extends RecursoAgencia implements Registrable {

    /** Patrón de patente: letras y números, entre 4 y 8 caracteres (sin distinguir mayúsculas). */
    public static final String PATRON_PATENTE = "[A-Za-z0-9]{4,8}";
    /** Patrón de tipo de vehículo: solo letras y espacios. */
    public static final String PATRON_TIPO = "[\\p{L} ]+";
    public static final int LARGO_MAXIMO_TIPO = 30;

    private String patente;
    private String tipo;
    private int capacidad;

    public Vehiculo(String codigo, String nombre, boolean activo,
                     String patente, String tipo, int capacidad) {
        super(codigo, nombre, activo);
        setPatente(patente);
        setTipo(tipo);
        setCapacidad(capacidad);
    }

    public String getPatente() {
        return patente;
    }

    /** @param patente letras y números, entre 4 y 8 caracteres; se guarda en mayúsculas */
    public void setPatente(String patente) {
        if (patente == null || !patente.matches(PATRON_PATENTE)) {
            throw new IllegalArgumentException("La patente debe tener entre 4 y 8 letras y/o números.");
        }
        this.patente = patente.toUpperCase();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Falta el tipo de vehículo.");
        }
        if (tipo.length() > LARGO_MAXIMO_TIPO) {
            throw new IllegalArgumentException("Ese tipo es muy largo (máximo " + LARGO_MAXIMO_TIPO + " caracteres).");
        }
        if (!tipo.matches(PATRON_TIPO)) {
            throw new IllegalArgumentException("El tipo de vehículo solo puede tener letras, sin números ni símbolos.");
        }
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        if (capacidad > 80) {
            throw new IllegalArgumentException("La capacidad no puede superar 80 pasajeros.");
        }
        this.capacidad = capacidad;
    }

    @Override
    public String mostrarResumen() {
        return "Vehículo: " + getNombre() + " (" + patente + ")"
                + " | Tipo: " + tipo
                + " | Capacidad: " + capacidad + " pasajeros"
                + " | Activo: " + (isActivo() ? "sí" : "no");
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}
