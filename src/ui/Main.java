package ui;

import data.GestorDatos;
import data.GestorEntidades;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Cliente;
import model.GuiaTuristico;
import model.Tour;
import service.GestorTours;

/**
 * Punto de entrada del sistema. Carga los datos y abre {@link VentanaPrincipal},
 * la ventana (JFrame) que concentra toda la interacción del sistema.
 */
public class Main {

    public static void main(String[] args) {
        aplicarLookAndFeel();

        GestorDatos gestorDatos = new GestorDatos();
        ArrayList<GuiaTuristico> guias = gestorDatos.cargarGuias("resources/guias.txt");
        ArrayList<Tour> tours = gestorDatos.cargarTours("resources/tours.txt", guias);
        ArrayList<Cliente> clientes = gestorDatos.cargarClientes("resources/clientes.txt");
        gestorDatos.cargarInscripciones("resources/inscripciones.txt", tours, clientes);
        GestorTours gestorTours = new GestorTours(tours);
        GestorEntidades gestorEntidades = new GestorEntidades();

        if (!gestorDatos.getAvisos().isEmpty()) {
            StringBuilder texto = new StringBuilder();
            for (String aviso : gestorDatos.getAvisos()) {
                texto.append(aviso).append("\n");
            }
            JOptionPane.showMessageDialog(null, texto.toString(), "Avisos de carga de datos",
                    JOptionPane.WARNING_MESSAGE);
        }

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(guias, tours, clientes, gestorTours, gestorEntidades);
            ventana.setVisible(true);
        });
    }

    /** Si Nimbus no está disponible, sigue con el Look and Feel por defecto. */
    private static void aplicarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
            // intencionalmente vacío
        }
    }
}
