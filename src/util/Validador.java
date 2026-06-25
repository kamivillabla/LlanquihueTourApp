package util;

/**
 * Validaciones reutilizables. Sus métodos son {@code static}, así se usan sin
 * crear un objeto (ej. {@code Validador.tieneCantidadCampos(partes, 6)}).
 */
public class Validador {

    /** Evita que la clase utilitaria se instancie. */
    private Validador() {
    }

    /** Retorna true si el arreglo de campos tiene exactamente la cantidad esperada. */
    public static boolean tieneCantidadCampos(String[] campos, int cantidadEsperada) {
        return campos != null && campos.length == cantidadEsperada;
    }
}
