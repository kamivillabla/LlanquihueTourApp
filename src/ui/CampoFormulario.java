package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Function;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Campo de formulario con validación en vivo: etiqueta, input con placeholder
 * y una línea de ayuda cuyo color indica el estado (pista gris, error rojo,
 * o borde verde cuando ya es válido).
 */
public class CampoFormulario extends JPanel {

    private static final Color COLOR_BORDE = new Color(196, 200, 206);
    private static final Color COLOR_BORDE_VALIDO = new Color(76, 175, 80);
    private static final Color COLOR_BORDE_ERROR = new Color(214, 69, 69);
    private static final Color COLOR_ERROR = new Color(196, 43, 43);
    private static final Color COLOR_PISTA = new Color(130, 134, 140);

    private enum Estado { NORMAL, VALIDO, ERROR }

    private final PlaceholderTextField campoTexto;
    private final JLabel labelAyuda;
    private Function<String, String> validador;
    private String pista;

    public CampoFormulario(String etiqueta, String placeholder) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setAlignmentX(LEFT_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JLabel labelEtiqueta = new JLabel(etiqueta);
        labelEtiqueta.setFont(labelEtiqueta.getFont().deriveFont(Font.BOLD, 13f));
        labelEtiqueta.setAlignmentX(LEFT_ALIGNMENT);
        labelEtiqueta.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        campoTexto = new PlaceholderTextField(placeholder);
        campoTexto.setAlignmentX(LEFT_ALIGNMENT);
        campoTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        campoTexto.setFont(campoTexto.getFont().deriveFont(14f));

        labelAyuda = new JLabel(" ");
        labelAyuda.setFont(labelAyuda.getFont().deriveFont(11.5f));
        labelAyuda.setAlignmentX(LEFT_ALIGNMENT);
        labelAyuda.setBorder(BorderFactory.createEmptyBorder(4, 2, 0, 0));

        add(labelEtiqueta);
        add(campoTexto);
        add(Box.createVerticalStrut(2));
        add(labelAyuda);

        actualizarEstado();

        campoTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarEstado();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarEstado();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarEstado();
            }
        });
    }

    /** @param validador función que retorna un mensaje de error, o {@code null} si el valor es válido */
    public void setValidador(Function<String, String> validador) {
        this.validador = validador;
    }

    /** Texto gris mostrado mientras el campo está vacío; se reemplaza por el error si corresponde. */
    public void setPista(String pista) {
        this.pista = pista;
        actualizarEstado();
    }

    /** Bloquea la escritura/pegado más allá del límite indicado. */
    public void establecerLargoMaximo(int largoMaximo) {
        ((AbstractDocument) campoTexto.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= largoMaximo) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int largoResultante = fb.getDocument().getLength() - length + (text == null ? 0 : text.length());
                if (largoResultante <= largoMaximo) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public String getTexto() {
        return campoTexto.getText().trim();
    }

    private void actualizarEstado() {
        String texto = getTexto();
        if (texto.isEmpty()) {
            mostrarAyuda(pista, COLOR_PISTA);
            aplicarBorde(Estado.NORMAL);
            return;
        }
        if (validador == null) {
            mostrarAyuda(null, COLOR_PISTA);
            aplicarBorde(Estado.NORMAL);
            return;
        }
        String error = validador.apply(texto);
        if (error == null) {
            mostrarAyuda(null, COLOR_PISTA);
            aplicarBorde(Estado.VALIDO);
        } else {
            mostrarAyuda(error, COLOR_ERROR);
            aplicarBorde(Estado.ERROR);
        }
    }

    /** Valida el campo (incluyendo "vacío") y marca visualmente el resultado. */
    public boolean esValido() {
        String texto = getTexto();
        if (texto.isEmpty()) {
            mostrarAyuda("Este campo no puede estar vacío.", COLOR_ERROR);
            aplicarBorde(Estado.ERROR);
            return false;
        }
        if (validador != null) {
            String error = validador.apply(texto);
            if (error != null) {
                mostrarAyuda(error, COLOR_ERROR);
                aplicarBorde(Estado.ERROR);
                return false;
            }
        }
        mostrarAyuda(null, COLOR_PISTA);
        aplicarBorde(Estado.VALIDO);
        return true;
    }

    public void limpiar() {
        campoTexto.setText("");
        actualizarEstado();
    }

    private void mostrarAyuda(String texto, Color color) {
        labelAyuda.setForeground(color);
        labelAyuda.setText(texto == null ? " " : texto);
    }

    private void aplicarBorde(Estado estado) {
        Color color = switch (estado) {
            case VALIDO -> COLOR_BORDE_VALIDO;
            case ERROR -> COLOR_BORDE_ERROR;
            default -> COLOR_BORDE;
        };
        Border linea = BorderFactory.createLineBorder(color, 1);
        campoTexto.setBorder(BorderFactory.createCompoundBorder(linea, BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    }
}
