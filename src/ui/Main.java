package ui;

import data.GestorDatos;
import data.GestorEntidades;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Cliente;
import model.ColaboradorExterno;
import model.GuiaTuristico;
import model.Tour;
import model.Vehiculo;
import service.GestorTours;

/**
 * Punto de entrada del sistema. Carga los datos y abre {@link VentanaPrincipal},
 * la ventana (JFrame) que concentra toda la interacción del sistema.
 */
public class Main {

    public static void main(String[] args) {
        aplicarLookAndFeel();

        GestorDatos gestorDatos = new GestorDatos();
        ArrayList<GuiaTuristico> guias = gestorDatos.cargarGuias(GestorDatos.RUTA_GUIAS);
        ArrayList<Tour> tours = gestorDatos.cargarTours(GestorDatos.RUTA_TOURS, guias);
        ArrayList<Cliente> clientes = gestorDatos.cargarClientes(GestorDatos.RUTA_CLIENTES);
        gestorDatos.cargarInscripciones(GestorDatos.RUTA_INSCRIPCIONES, tours, clientes);
        ArrayList<Vehiculo> vehiculos = gestorDatos.cargarVehiculos(GestorDatos.RUTA_VEHICULOS);
        ArrayList<ColaboradorExterno> colaboradores = gestorDatos.cargarColaboradores(GestorDatos.RUTA_COLABORADORES);

        GestorTours gestorTours = new GestorTours(tours);
        GestorEntidades gestorEntidades = new GestorEntidades();
        gestorEntidades.agregar(guias);
        gestorEntidades.agregar(vehiculos);
        gestorEntidades.agregar(colaboradores);

        if (!gestorDatos.getAvisos().isEmpty()) {
            StringBuilder texto = new StringBuilder();
            for (String aviso : gestorDatos.getAvisos()) {
                texto.append(aviso).append("\n");
            }
            JOptionPane.showMessageDialog(null, texto.toString(), "Avisos de carga de datos",
                    JOptionPane.WARNING_MESSAGE);
        }

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(guias, tours, clientes, gestorTours, gestorEntidades, gestorDatos);
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
