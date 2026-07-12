package ui;

import java.awt.Color;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Base común para los formularios de registro: crea campos con validación en
 * vivo ({@link CampoFormulario}) y construye las funciones de validación
 * que cada formulario concreto necesita.
 */
public abstract class PanelFormularioBase extends JPanel {

    /** Mensaje de error general del formulario (para excepciones del modelo, ej. RUT o patente). */
    protected final JLabel labelError = new JLabel(" ");

    protected PanelFormularioBase() {
        labelError.setForeground(new Color(196, 43, 43));
        labelError.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 4));
    }

    /**
     * Crea un campo con validación en vivo y lo agrega al contenedor.
     * @param validador función que retorna un mensaje de error, o {@code null} si el valor es válido
     * @return el campo creado, para leer su valor o revalidarlo al enviar el formulario
     */
    protected CampoFormulario crearCampo(JPanel contenedor, String etiqueta, String placeholder,
                                          Function<String, String> validador) {
        CampoFormulario campo = new CampoFormulario(etiqueta, placeholder);
        campo.setValidador(validador);
        contenedor.add(campo);
        return campo;
    }

    /** @return una función de validación que exige que el valor cumpla el patrón indicado */
    protected static Function<String, String> validadorPatron(String patron, String mensajeError) {
        return valor -> valor.matches(patron) ? null : mensajeError;
    }

    /** @return una función de validación que revisa primero el largo máximo y luego el patrón */
    protected static Function<String, String> validadorTexto(int largoMaximo, String patron, String mensajeFormato) {
        return valor -> {
            if (valor.length() > largoMaximo) {
                return "Muy largo: máximo " + largoMaximo + " caracteres.";
            }
            return valor.matches(patron) ? null : mensajeFormato;
        };
    }

    /** Crea un campo con largo máximo aplicado tanto en la validación como en la escritura. */
    protected CampoFormulario crearCampoTexto(JPanel contenedor, String etiqueta, String placeholder,
                                               int largoMaximo, String patron, String mensajeFormato) {
        CampoFormulario campo = crearCampo(contenedor, etiqueta, placeholder,
                validadorTexto(largoMaximo, patron, mensajeFormato));
        campo.establecerLargoMaximo(largoMaximo);
        return campo;
    }

    /** @return una función de validación que exige un entero dentro del rango [minimo, maximo] */
    protected static Function<String, String> validadorEntero(int minimo, int maximo) {
        return valor -> {
            try {
                int numero = Integer.parseInt(valor);
                if (numero < minimo || numero > maximo) {
                    return "Debe ser un número entre " + minimo + " y " + maximo + ".";
                }
                return null;
            } catch (NumberFormatException e) {
                return "Debe ingresar un número entero.";
            }
        };
    }
}
