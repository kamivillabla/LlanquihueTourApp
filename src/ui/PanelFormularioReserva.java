package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Cliente;
import model.Tour;

/**
 * Formulario para registrar una reserva: inscribe un cliente en un tour.
 * El cliente y el tour se eligen desde las colecciones ya cargadas, y la
 * inscripción se persiste en el archivo de inscripciones.
 */
public class PanelFormularioReserva extends JPanel {

    private final VentanaPrincipal ventana;
    private final List<Tour> tours;
    private final JComboBox<Cliente> comboClientes;
    private final JComboBox<Tour> comboTours;
    private final JLabel labelError = new JLabel(" ");

    public PanelFormularioReserva(VentanaPrincipal ventana, List<Cliente> clientes, List<Tour> tours) {
        this.ventana = ventana;
        this.tours = tours;
        setLayout(new BorderLayout(0, 16));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 24, 50));

        JLabel titulo = new JLabel("Registrar Reserva");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel();
        campos.setOpaque(false);
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));

        comboClientes = new JComboBox<>(clientes.toArray(new Cliente[0]));
        comboClientes.setRenderer(new RenderCliente());
        comboTours = new JComboBox<>(tours.toArray(new Tour[0]));
        comboTours.setRenderer(new RenderTour());

        campos.add(crearEtiqueta("Cliente:"));
        campos.add(ajustar(comboClientes));
        campos.add(Box.createVerticalStrut(20));
        campos.add(crearEtiqueta("Tour:"));
        campos.add(ajustar(comboTours));
        campos.add(Box.createVerticalGlue());

        add(campos, BorderLayout.CENTER);

        labelError.setForeground(new Color(196, 43, 43));
        labelError.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 4));

        JButton botonRegistrar = new JButton("Registrar reserva");
        botonRegistrar.addActionListener(e -> registrar());
        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> {
            labelError.setText(" ");
            ventana.mostrarMenuPrincipal();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.add(botonVolver);
        panelBotones.add(Box.createHorizontalStrut(8));
        panelBotones.add(botonRegistrar);

        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
        panelSur.add(labelError);
        panelSur.add(panelBotones);
        add(panelSur, BorderLayout.SOUTH);
    }

    /** @return los tours en que el cliente ya está inscrito, entre comillas y separados por coma (vacío si ninguno) */
    private String reservasDe(Cliente cliente) {
        StringBuilder nombres = new StringBuilder();
        for (Tour tour : tours) {
            if (tour.estaInscrito(cliente)) {
                if (nombres.length() > 0) {
                    nombres.append(", ");
                }
                nombres.append("\"").append(tour.getNombre()).append("\"");
            }
        }
        return nombres.toString();
    }

    /** Crea una etiqueta alineada a la izquierda con un pequeño margen inferior. */
    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setAlignmentX(LEFT_ALIGNMENT);
        etiqueta.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return etiqueta;
    }

    /** Fija el alto del combo a su tamaño natural (evita que se estire) y lo alinea a la izquierda. */
    private JComboBox<?> ajustar(JComboBox<?> combo) {
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, combo.getPreferredSize().height));
        combo.setAlignmentX(LEFT_ALIGNMENT);
        return combo;
    }

    /** Inscribe el cliente seleccionado en el tour seleccionado y guarda la reserva. */
    private void registrar() {
        Cliente cliente = (Cliente) comboClientes.getSelectedItem();
        Tour tour = (Tour) comboTours.getSelectedItem();
        if (cliente == null || tour == null) {
            labelError.setText("Debes elegir un cliente y un tour.");
            return;
        }

        String reservasPrevias = reservasDe(cliente);
        if (!reservasPrevias.isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(ventana,
                    cliente.getNombre() + " " + cliente.getApellido()
                            + " ya tiene reserva en: " + reservasPrevias + ".\n¿Deseas reservar de todos modos?",
                    "Cliente con reserva previa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion != JOptionPane.YES_OPTION) {
                labelError.setText(" ");
                return;
            }
        }

        try {
            tour.inscribirCliente(cliente);
            ventana.getGestorDatos().guardarInscripcion(cliente, tour);
            labelError.setText(" ");
            JOptionPane.showMessageDialog(ventana,
                    cliente.getNombre() + " " + cliente.getApellido()
                            + " quedó inscrito(a) en el tour \"" + tour.getNombre() + "\".");
            ventana.mostrarMenuPrincipal();
        } catch (IllegalStateException | IllegalArgumentException | IOException e) {
            labelError.setText(e.getMessage());
        }
    }

    /** Muestra el cliente como "Nombre Apellido (RUT)" dentro del combo. */
    private static class RenderCliente extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                boolean seleccionado, boolean foco) {
            super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
            if (valor instanceof Cliente cliente) {
                setText(cliente.getNombre() + " " + cliente.getApellido() + " (" + cliente.getRut() + ")");
            }
            return this;
        }
    }

    /** Muestra el tour como "Nombre (Tipo) - N cupos disponibles" dentro del combo. */
    private static class RenderTour extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice,
                boolean seleccionado, boolean foco) {
            super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
            if (valor instanceof Tour tour) {
                setText(tour.getNombre() + " (" + tour.getTipo() + ") - "
                        + tour.getCuposDisponibles() + " cupos disponibles");
            }
            return this;
        }
    }
}
