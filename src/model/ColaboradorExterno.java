package model;

/** Colaborador externo registrable de la agencia (proveedor de un servicio puntual). */
public class ColaboradorExterno extends RecursoAgencia implements Registrable {

    /** Patrón de empresa: letras, números, espacios y puntuación básica. */
    public static final String PATRON_EMPRESA = "[\\p{L}\\p{N} .,-]+";
    public static final int LARGO_MAXIMO_EMPRESA = 60;
    /** Patrón de servicio prestado: letras, espacios y puntuación básica. */
    public static final String PATRON_SERVICIO = "[\\p{L} .,-]+";
    public static final int LARGO_MAXIMO_SERVICIO = 100;

    private String empresa;
    private String servicioPrestado;

    public ColaboradorExterno(String codigo, String nombre, boolean activo,
                               String empresa, String servicioPrestado) {
        super(codigo, nombre, activo);
        setEmpresa(empresa);
        setServicioPrestado(servicioPrestado);
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        if (empresa == null || empresa.isBlank()) {
            throw new IllegalArgumentException("Falta el nombre de la empresa.");
        }
        if (empresa.length() > LARGO_MAXIMO_EMPRESA) {
            throw new IllegalArgumentException("Ese nombre es muy largo (máximo " + LARGO_MAXIMO_EMPRESA + " caracteres).");
        }
        if (!empresa.matches(PATRON_EMPRESA)) {
            throw new IllegalArgumentException("Usa solo letras, números, espacios, puntos, comas o guiones.");
        }
        this.empresa = empresa;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        if (servicioPrestado == null || servicioPrestado.isBlank()) {
            throw new IllegalArgumentException("Falta describir el servicio prestado.");
        }
        if (servicioPrestado.length() > LARGO_MAXIMO_SERVICIO) {
            throw new IllegalArgumentException("Esa descripción es muy larga (máximo " + LARGO_MAXIMO_SERVICIO + " caracteres).");
        }
        if (!servicioPrestado.matches(PATRON_SERVICIO)) {
            throw new IllegalArgumentException("Usa solo letras, espacios, puntos, comas o guiones.");
        }
        this.servicioPrestado = servicioPrestado;
    }

    @Override
    public String mostrarResumen() {
        return "Colaborador externo: " + getNombre()
                + " | Empresa: " + empresa
                + " | Servicio: " + servicioPrestado
                + " | Activo: " + (isActivo() ? "sí" : "no");
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}
