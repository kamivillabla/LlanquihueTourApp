package model;

/**
 * Representa un tour ofrecido por la agencia Llanquihue Tour.
 */
public class Tour {

    private String nombre;
    private String tipo;
    private String destino;
    private int precio;
    private int cupos;

    /**
     * Crea un tour validando sus datos mediante los setters.
     *
     * @param nombre  nombre del tour
     * @param tipo    categoría del tour (ej. Aventura, Cultural, Lacustre)
     * @param destino lugar donde se realiza el tour
     * @param precio  precio en pesos chilenos
     * @param cupos   cantidad de cupos disponibles
     */
    public Tour(String nombre, String tipo, String destino, int precio, int cupos) {
        setNombre(nombre);
        setTipo(tipo);
        setDestino(destino);
        setPrecio(precio);
        setCupos(cupos);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del tour no puede estar vacío.");
        }
        if (!nombre.matches("[\\p{L}\\p{N} .,'-]+")) {
            throw new IllegalArgumentException("El nombre del tour contiene caracteres no permitidos.");
        }
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de tour no puede estar vacío.");
        }
        if (!tipo.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El tipo de tour solo puede contener letras y espacios.");
        }
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        if (!destino.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("El destino solo puede contener letras y espacios.");
        }
        this.destino = destino;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (precio > 1_000_000) {
            throw new IllegalArgumentException("El precio no puede superar $1.000.000.");
        }
        this.precio = precio;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        if (cupos < 0) {
            throw new IllegalArgumentException("Los cupos no pueden ser negativos.");
        }
        if (cupos > 100) {
            throw new IllegalArgumentException("Los cupos no pueden superar 100.");
        }
        this.cupos = cupos;
    }

    @Override
    public String toString() {
        return "Tour: " + nombre +
                " | Tipo: " + tipo +
                " | Destino: " + destino +
                " | Precio: $" + precio +
                " | Cupos disponibles: " + cupos;
    }
}
