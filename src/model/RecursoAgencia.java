package model;

/**
 * Superclase de los recursos que la agencia puede registrar y gestionar
 * (guías turísticos, vehículos, colaboradores externos). No implementa
 * Registrable directamente: cada subclase lo hace explícitamente, ya que
 * cada una define su propio mostrarResumen().
 */
public class RecursoAgencia {

    /** Patrón de código: letras, números y guiones. */
    public static final String PATRON_CODIGO = "[A-Za-z0-9-]+";
    public static final int LARGO_MAXIMO_CODIGO = 15;
    /** Patrón de nombre: solo letras y espacios. */
    public static final String PATRON_NOMBRE = "[\\p{L} ]+";
    public static final int LARGO_MAXIMO_NOMBRE = 50;

    private String codigo;
    private String nombre;
    private boolean activo;

    public RecursoAgencia(String codigo, String nombre, boolean activo) {
        setCodigo(codigo);
        setNombre(nombre);
        this.activo = activo;
    }

    public String getCodigo() {
        return codigo;
    }

    /** @param codigo identificador único del recurso, ej. "G-001" */
    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Falta el código.");
        }
        if (codigo.length() > LARGO_MAXIMO_CODIGO) {
            throw new IllegalArgumentException("Ese código es muy largo (máximo " + LARGO_MAXIMO_CODIGO + " caracteres).");
        }
        if (!codigo.matches(PATRON_CODIGO)) {
            throw new IllegalArgumentException("El código solo puede tener letras, números y guiones.");
        }
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Falta el nombre.");
        }
        if (nombre.length() > LARGO_MAXIMO_NOMBRE) {
            throw new IllegalArgumentException("Ese nombre es muy largo (máximo " + LARGO_MAXIMO_NOMBRE + " caracteres).");
        }
        if (!nombre.matches(PATRON_NOMBRE)) {
            throw new IllegalArgumentException("El nombre solo puede tener letras, sin números ni símbolos.");
        }
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + " | Nombre: " + nombre + " | Activo: " + (activo ? "sí" : "no");
    }
}
