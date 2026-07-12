package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Direccion;
import model.GuiaTuristico;
import model.Persona;
import util.RutInvalidoException;

/** Formulario de registro de un guía turístico (Persona + Registrable). */
public class PanelFormularioGuia extends PanelFormularioBase {

    private final VentanaPrincipal ventana;
    private final CampoFormulario campoNombre;
    private final CampoFormulario campoApellido;
    private final CampoFormulario campoRut;
    private final CampoFormulario campoCorreo;
    private final CampoFormulario campoCalle;
    private final CampoFormulario campoNumero;
    private final CampoFormulario campoCiudad;
    private final CampoFormulario campoRegion;
    private final CampoFormulario campoEspecialidad;
    private final CampoFormulario campoIdiomas;
    private final CampoFormulario campoAnios;

    public PanelFormularioGuia(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 16));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 24, 50));

        JLabel titulo = new JLabel("Registrar Guía Turístico");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel();
        campos.setOpaque(false);
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
        campos.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 16));

        campoNombre = crearCampoTexto(campos, "Nombre", "Ej: Camila",
                Persona.LARGO_MAXIMO_NOMBRE, Persona.PATRON_NOMBRE, "Solo letras, sin números ni símbolos.");
        campoApellido = crearCampoTexto(campos, "Apellido", "Ej: Soto",
                Persona.LARGO_MAXIMO_NOMBRE, Persona.PATRON_NOMBRE, "Solo letras, sin números ni símbolos.");
        campoRut = crearCampo(campos, "RUT", "Ej: 12345678-9",
                validadorPatron(Persona.PATRON_RUT,
                        "El RUT debe tener 7 u 8 números, un guion, y terminar en un número o la letra K."));
        campoRut.setPista("7 u 8 números, guion, y un número o K al final.");
        campoRut.establecerLargoMaximo(10);
        campoCorreo = crearCampoTexto(campos, "Correo", "Ej: camila@gmail.com",
                Persona.LARGO_MAXIMO_CORREO, Persona.PATRON_CORREO,
                "Escribe un correo válido, por ejemplo: usuario@gmail.com");
        campoCorreo.setPista("Con arroba (@) y un punto, ej: usuario@gmail.com");
        campoCalle = crearCampoTexto(campos, "Calle", "Ej: Av. Costanera",
                Direccion.LARGO_MAXIMO_CALLE, Direccion.PATRON_CALLE,
                "Usa solo letras, números, espacios, puntos, numeral (#), comas o guiones.");
        campoNumero = crearCampo(campos, "Número", "Ej: 250",
                validadorEntero(Direccion.NUMERO_MINIMO, Direccion.NUMERO_MAXIMO));
        campoCiudad = crearCampoTexto(campos, "Ciudad", "Ej: Frutillar",
                Direccion.LARGO_MAXIMO_CIUDAD_REGION, Direccion.PATRON_CIUDAD_REGION, "Solo letras, sin números ni símbolos.");
        campoRegion = crearCampoTexto(campos, "Región", "Ej: Los Lagos",
                Direccion.LARGO_MAXIMO_CIUDAD_REGION, Direccion.PATRON_CIUDAD_REGION, "Solo letras, sin números ni símbolos.");
        campoEspecialidad = crearCampoTexto(campos, "Especialidad", "Ej: Montañismo",
                GuiaTuristico.LARGO_MAXIMO_ESPECIALIDAD, GuiaTuristico.PATRON_ESPECIALIDAD,
                "Solo letras, sin números ni símbolos.");
        campoIdiomas = crearCampoTexto(campos, "Idiomas", "Ej: Español, Inglés",
                GuiaTuristico.LARGO_MAXIMO_IDIOMAS, GuiaTuristico.PATRON_IDIOMAS,
                "Solo letras y comas (separa los idiomas así: Español, Inglés).");
        campoAnios = crearCampo(campos, "Años de experiencia", "Ej: 5",
                validadorEntero(0, 70));

        JScrollPane scroll = new JScrollPane(campos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

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
        valido &= campoNombre.esValido();
        valido &= campoApellido.esValido();
        valido &= campoRut.esValido();
        valido &= campoCorreo.esValido();
        valido &= campoCalle.esValido();
        valido &= campoNumero.esValido();
        valido &= campoCiudad.esValido();
        valido &= campoRegion.esValido();
        valido &= campoEspecialidad.esValido();
        valido &= campoIdiomas.esValido();
        valido &= campoAnios.esValido();
        if (!valido) {
            labelError.setText("Revisa los campos marcados en rojo.");
            return;
        }

        try {
            Direccion direccion = new Direccion(campoCalle.getTexto(), Integer.parseInt(campoNumero.getTexto()),
                    campoCiudad.getTexto(), campoRegion.getTexto());
            ventana.getGestorEntidades().agregar(new GuiaTuristico(
                    campoNombre.getTexto(), campoApellido.getTexto(), campoRut.getTexto(), campoCorreo.getTexto(),
                    direccion, campoEspecialidad.getTexto(), campoIdiomas.getTexto(),
                    Integer.parseInt(campoAnios.getTexto()), true));
            limpiar();
            JOptionPane.showMessageDialog(ventana, "Guía turístico registrado con éxito.");
            ventana.mostrarMenuEntidades();
        } catch (RutInvalidoException | IllegalArgumentException e) {
            labelError.setText(e.getMessage());
        }
    }

    private void limpiar() {
        campoNombre.limpiar();
        campoApellido.limpiar();
        campoRut.limpiar();
        campoCorreo.limpiar();
        campoCalle.limpiar();
        campoNumero.limpiar();
        campoCiudad.limpiar();
        campoRegion.limpiar();
        campoEspecialidad.limpiar();
        campoIdiomas.limpiar();
        campoAnios.limpiar();
        labelError.setText(" ");
    }
}
