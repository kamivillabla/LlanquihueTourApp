package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Panel reutilizable para mostrar el resultado de cualquier sección: un
 * título, un área de texto con scroll, y un botón para volver.
 */
public class PanelResultado extends JPanel {

    private final JLabel labelTitulo = new JLabel();
    private final JTextArea areaTexto = new JTextArea();
    private Runnable alVolver;

    public PanelResultado() {
        setLayout(new BorderLayout(0, 16));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(28, 36, 24, 36));

        labelTitulo.setFont(labelTitulo.getFont().deriveFont(Font.BOLD, 20f));
        add(labelTitulo, BorderLayout.NORTH);

        areaTexto.setEditable(false);
        areaTexto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaTexto.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 222, 226)));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(botonVolver.getFont().deriveFont(14f));
        botonVolver.setFocusPainted(false);
        botonVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botonVolver.addActionListener(e -> {
            if (alVolver != null) {
                alVolver.run();
            }
        });
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        panelBoton.add(botonVolver);
        add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Reemplaza el contenido mostrado por este panel.
     * @param titulo título de la sección
     * @param contenido texto a mostrar en el área con scroll
     * @param alVolver acción a ejecutar cuando se presiona "Volver"
     */
    public void actualizar(String titulo, String contenido, Runnable alVolver) {
        labelTitulo.setText(titulo);
        areaTexto.setText(contenido);
        areaTexto.setCaretPosition(0);
        this.alVolver = alVolver;
    }
}
