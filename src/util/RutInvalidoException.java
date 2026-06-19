package util;

/** Excepción lanzada cuando un RUT no cumple el formato XXXXXXXX-X. */
public class RutInvalidoException extends Exception {

    public RutInvalidoException(String mensaje) {
        super(mensaje);
    }
}
