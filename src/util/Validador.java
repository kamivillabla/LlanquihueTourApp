package util;

/**
 * Librería propia de validaciones reutilizables.
 *
 * <p>Sus métodos son {@code static}, por lo que se usan sin crear un objeto
 * {@code Validador} (por ejemplo {@code Validador.tieneCantidadCampos(partes, 6)}).
 * Centraliza chequeos que, de otro modo, se repetirían en varias clases.
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
