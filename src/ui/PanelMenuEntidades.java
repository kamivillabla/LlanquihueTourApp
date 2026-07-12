package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Panel del submenú "Gestión": registrar y visualizar guías, vehículos y colaboradores. */
public class PanelMenuEntidades extends JPanel {

    public PanelMenuEntidades(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel titulo = new JLabel("Gestión", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 36, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));

        botones.add(crearBoton("Registrar guía turístico", e -> ventana.mostrarFormularioGuia()));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("Registrar vehículo", e -> ventana.mostrarFormularioVehiculo()));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("Registrar colaborador externo", e -> ventana.mostrarFormularioColaborador()));
        botones.add(Box.createVerticalStrut(20));
        botones.add(crearBoton("Ver todos", e -> ventana.mostrarEntidadesTodos()));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("Ver detalle por tipo", e -> ventana.mostrarEntidadesDetallePorTipo()));
        botones.add(Box.createVerticalStrut(10));
        botones.add(crearBoton("Ver conteo por tipo", e -> ventana.mostrarEntidadesConteoPorTipo()));
        botones.add(Box.createVerticalStrut(24));
        botones.add(crearBoton("Volver al menú principal", e -> ventana.mostrarMenuPrincipal()));

        add(botones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(boton.getFont().deriveFont(14f));
        boton.setAlignmentX(CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.addActionListener(accion);
        return boton;
    }
}
