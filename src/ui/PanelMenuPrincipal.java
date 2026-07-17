package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/** Panel del menú principal: da acceso a cada sección del sistema. */
public class PanelMenuPrincipal extends JPanel {

    public PanelMenuPrincipal(VentanaPrincipal ventana) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel titulo = new JLabel("Llanquihue Tour", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 26f));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel subtitulo = new JLabel("Sistema de gestión turística", SwingConstants.CENTER);
        subtitulo.setFont(subtitulo.getFont().deriveFont(Font.PLAIN, 13f));
        subtitulo.setForeground(new Color(110, 110, 110));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 36, 0));
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        panelTitulo.add(titulo);
        panelTitulo.add(subtitulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));

        botones.add(crearBoton("Gestión", e -> ventana.mostrarMenuEntidades()));
        botones.add(Box.createVerticalStrut(12));
        botones.add(crearBoton("Sistema de Tours", e -> ventana.mostrarSistemaTours()));
        botones.add(Box.createVerticalStrut(12));
        botones.add(crearBoton("Registrar reserva", e -> ventana.mostrarFormularioReserva()));
        botones.add(Box.createVerticalStrut(12));
        botones.add(crearBoton("Buscar cliente por RUT", e -> ventana.buscarClientePorRut()));
        botones.add(Box.createVerticalStrut(12));
        botones.add(crearBoton("Servicios Turísticos", e -> ventana.mostrarServiciosTuristicos()));
        botones.add(Box.createVerticalStrut(24));
        botones.add(crearBoton("Salir", e -> System.exit(0)));

        add(botones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(boton.getFont().deriveFont(14f));
        boton.setAlignmentX(CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        boton.setFocusPainted(false);
        boton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        boton.addActionListener(accion);
        return boton;
    }
}
