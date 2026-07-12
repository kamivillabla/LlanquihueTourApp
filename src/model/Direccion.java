package model;

/**
 * Dirección física de una persona. Una Persona "tiene una" Direccion (composición).
 */
public class Direccion {

    /** Patrón de calle: letras, números, espacios y puntuación básica. */
    public static final String PATRON_CALLE = "[\\p{L}\\p{N} .#,-]+";
    public static final int LARGO_MAXIMO_CALLE = 60;
    /** Patrón de ciudad/región: solo letras y espacios. */
    public static final String PATRON_CIUDAD_REGION = "[\\p{L} ]+";
    public static final int LARGO_MAXIMO_CIUDAD_REGION = 40;
    public static final int NUMERO_MINIMO = 1;
    public static final int NUMERO_MAXIMO = 99999;

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
            throw new IllegalArgumentException("Falta la calle.");
        }
        if (calle.length() > LARGO_MAXIMO_CALLE) {
            throw new IllegalArgumentException("Esa calle es muy larga (máximo " + LARGO_MAXIMO_CALLE + " caracteres).");
        }
        if (!calle.matches(PATRON_CALLE)) {
            throw new IllegalArgumentException("Usa solo letras, números, espacios, puntos, numeral (#), comas o guiones.");
        }
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero < NUMERO_MINIMO || numero > NUMERO_MAXIMO) {
            throw new IllegalArgumentException(
                    "Ingresa un número de dirección válido (entre " + NUMERO_MINIMO + " y " + NUMERO_MAXIMO + ").");
        }
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("Falta la ciudad.");
        }
        if (ciudad.length() > LARGO_MAXIMO_CIUDAD_REGION) {
            throw new IllegalArgumentException("Esa ciudad es muy larga (máximo " + LARGO_MAXIMO_CIUDAD_REGION + " caracteres).");
        }
        if (!ciudad.matches(PATRON_CIUDAD_REGION)) {
            throw new IllegalArgumentException("La ciudad solo puede tener letras, sin números ni símbolos.");
        }
        this.ciudad = ciudad;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        if (region == null || region.isBlank()) {
            throw new IllegalArgumentException("Falta la región.");
        }
        if (region.length() > LARGO_MAXIMO_CIUDAD_REGION) {
            throw new IllegalArgumentException("Esa región es muy larga (máximo " + LARGO_MAXIMO_CIUDAD_REGION + " caracteres).");
        }
        if (!region.matches(PATRON_CIUDAD_REGION)) {
            throw new IllegalArgumentException("La región solo puede tener letras, sin números ni símbolos.");
        }
        this.region = region;
    }

    @Override
    public String toString() {
        return calle + " #" + numero + ", " + ciudad + ", " + region;
    }
}
