package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.RecursoAgencia;
import model.Vehiculo;

/** Formulario de registro de un vehículo (RecursoAgencia + Registrable). */
public class PanelFormularioVehiculo extends PanelFormularioBase {

    private final VentanaPrincipal ventana;
    private final CampoFormulario campoCodigo;
    private final CampoFormulario campoNombre;
    private final CampoFormulario campoPatente;
    private final CampoFormulario campoTipo;
    private final CampoFormulario campoCapacidad;

    public PanelFormularioVehiculo(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 16));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 24, 50));

        JLabel titulo = new JLabel("Registrar Vehículo");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel();
        campos.setOpaque(false);
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
        campos.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 16));

        campoCodigo = crearCampoTexto(campos, "Código", "Ej: V-003",
                RecursoAgencia.LARGO_MAXIMO_CODIGO, RecursoAgencia.PATRON_CODIGO, "Solo letras, números y guiones.");
        campoNombre = crearCampoTexto(campos, "Nombre", "Ej: Van Frutillar",
                RecursoAgencia.LARGO_MAXIMO_NOMBRE, RecursoAgencia.PATRON_NOMBRE, "Solo letras, sin números ni símbolos.");
        campoPatente = crearCampo(campos, "Patente", "Ej: AB1234",
                validadorPatron(Vehiculo.PATRON_PATENTE, "Entre 4 y 8 letras y/o números."));
        campoPatente.setPista("Letras y números, entre 4 y 8 caracteres.");
        campoPatente.establecerLargoMaximo(8);
        campoTipo = crearCampoTexto(campos, "Tipo", "Ej: Van, Bus, Lancha",
                Vehiculo.LARGO_MAXIMO_TIPO, Vehiculo.PATRON_TIPO, "Solo letras, sin números ni símbolos.");
        campoCapacidad = crearCampo(campos, "Capacidad (pasajeros)", "Ej: 12",
                validadorEntero(1, 80));

        add(campos, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        panelSur.setOpaque(false);
        panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
        panelSur.add(labelError);

        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.addActionListener(e -> registrar());
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(e -> {
            limpiar();
            ventana.mostrarMenuEntidades();
        });
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.add(botonCancelar);
        panelBotones.add(Box.createHorizontalStrut(8));
        panelBotones.add(botonRegistrar);
        panelSur.add(panelBotones);

        add(panelSur, BorderLayout.SOUTH);
    }

    private void registrar() {
        boolean valido = true;
        valido &= campoCodigo.esValido();
        valido &= campoNombre.esValido();
        valido &= campoPatente.esValido();
        valido &= campoTipo.esValido();
        valido &= campoCapacidad.esValido();
        if (!valido) {
            labelError.setText("Revisa los campos marcados en rojo.");
            return;
        }

        try {
            Vehiculo nuevoVehiculo = new Vehiculo(campoCodigo.getTexto(), campoNombre.getTexto(), true,
                    campoPatente.getTexto(), campoTipo.getTexto(), Integer.parseInt(campoCapacidad.getTexto()));
            ventana.getGestorEntidades().agregar(nuevoVehiculo);
            ventana.getGestorDatos().guardarVehiculo(nuevoVehiculo);
            limpiar();
            JOptionPane.showMessageDialog(ventana, "Vehículo registrado con éxito.");
            ventana.mostrarMenuEntidades();
        } catch (IllegalArgumentException | IOException e) {
            labelError.setText(e.getMessage());
        }
    }

    private void limpiar() {
        campoCodigo.limpiar();
        campoNombre.limpiar();
        campoPatente.limpiar();
        campoTipo.limpiar();
        campoCapacidad.limpiar();
        labelError.setText(" ");
    }
}
