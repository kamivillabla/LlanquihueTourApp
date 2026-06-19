package util;

/**
 * Excepción lanzada cuando una línea de un archivo de datos no cumple
 * el formato esperado (cantidad de campos incorrecta, campos vacíos, etc.).
 */
public class FormatoArchivoInvalidoException extends Exception {

    public FormatoArchivoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
