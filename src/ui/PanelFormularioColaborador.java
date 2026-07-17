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
import model.ColaboradorExterno;
import model.RecursoAgencia;

/** Formulario de registro de un colaborador externo (RecursoAgencia + Registrable). */
public class PanelFormularioColaborador extends PanelFormularioBase {

    private final VentanaPrincipal ventana;
    private final CampoFormulario campoCodigo;
    private final CampoFormulario campoNombre;
    private final CampoFormulario campoEmpresa;
    private final CampoFormulario campoServicio;

    public PanelFormularioColaborador(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(0, 16));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 24, 50));

        JLabel titulo = new JLabel("Registrar Colaborador Externo");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel();
        campos.setOpaque(false);
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
        campos.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 16));

        campoCodigo = crearCampoTexto(campos, "Código", "Ej: C-003",
                RecursoAgencia.LARGO_MAXIMO_CODIGO, RecursoAgencia.PATRON_CODIGO, "Solo letras, números y guiones.");
        campoNombre = crearCampoTexto(campos, "Nombre", "Ej: Restaurante Costanera",
                RecursoAgencia.LARGO_MAXIMO_NOMBRE, RecursoAgencia.PATRON_NOMBRE, "Solo letras, sin números ni símbolos.");
        campoEmpresa = crearCampoTexto(campos, "Empresa", "Ej: Costanera Gourmet SpA",
                ColaboradorExterno.LARGO_MAXIMO_EMPRESA, ColaboradorExterno.PATRON_EMPRESA,
                "Usa solo letras, números, espacios, puntos, comas o guiones.");
        campoServicio = crearCampoTexto(campos, "Servicio prestado", "Ej: Almuerzo típico para grupos",
                ColaboradorExterno.LARGO_MAXIMO_SERVICIO, ColaboradorExterno.PATRON_SERVICIO,
                "Usa solo letras, espacios, puntos, comas o guiones.");

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
        valido &= campoEmpresa.esValido();
        valido &= campoServicio.esValido();
        if (!valido) {
            labelError.setText("Revisa los campos marcados en rojo.");
            return;
        }

        try {
            ColaboradorExterno nuevoColaborador = new ColaboradorExterno(campoCodigo.getTexto(),
                    campoNombre.getTexto(), true, campoEmpresa.getTexto(), campoServicio.getTexto());
            ventana.getGestorEntidades().agregar(nuevoColaborador);
            ventana.getGestorDatos().guardarColaborador(nuevoColaborador);
            limpiar();
            JOptionPane.showMessageDialog(ventana, "Colaborador externo registrado con éxito.");
            ventana.mostrarMenuEntidades();
        } catch (IllegalArgumentException | IOException e) {
            labelError.setText(e.getMessage());
        }
    }

    private void limpiar() {
        campoCodigo.limpiar();
        campoNombre.limpiar();
        campoEmpresa.limpiar();
        campoServicio.limpiar();
        labelError.setText(" ");
    }
}
