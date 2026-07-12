package model;

/**
 * Contrato para toda entidad de la agencia que pueda registrarse y mostrar
 * un resumen de sí misma dentro del sistema.
 */
public interface Registrable {

    /** @return un resumen en texto de la entidad, con el formato propio de cada tipo */
    String mostrarResumen();
}
