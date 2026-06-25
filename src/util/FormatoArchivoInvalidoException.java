package util;

/** Excepción para cuando una línea del archivo no cumple el formato esperado. */
public class FormatoArchivoInvalidoException extends Exception {

    public FormatoArchivoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
