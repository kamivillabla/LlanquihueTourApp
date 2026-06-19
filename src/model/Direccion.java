package model;

/**
 * Representa la dirección física de una persona vinculada a Llanquihue Tour.
 * Es la clase compuesta dentro de {@link Persona} (Persona "tiene una" Direccion).
 */
public class Direccion {

    private String calle;
    private int numero;
    private String ciudad;
    private String region;

    public Direccion(String calle, int numero, String ciudad, String region) {
        setCalle(calle);
        setNumero(numero);
        setCiudad(ciudad);
        setRegion(region);
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("La calle no puede estar vacía.");
        }
        if (!calle.matches("[\\p{L}\\p{N} .#,-]+")) {
            throw new IllegalArgumentException("La calle contiene caracteres no permitidos.");
        }
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número debe ser mayor que cero.");
        }
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía.");
        }
        if (!ciudad.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("La ciudad solo puede contener letras y espacios.");
        }
        this.ciudad = ciudad;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException("La región no puede estar vacía.");
        }
        if (!region.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("La región solo puede contener letras y espacios.");
        }
        this.region = region;
    }

    @Override
    public String toString() {
        return calle + " #" + numero + ", " + ciudad + ", " + region;
    }
}
