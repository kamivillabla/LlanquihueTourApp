package ui;

import data.GestorDatos;
import data.GestorEntidades;
import data.GestorServicios;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Cliente;
import model.GuiaTuristico;
import model.Tour;
import service.GestorTours;

/**
 * Ventana principal del sistema (JFrame). Usa CardLayout para navegar entre
 * el menú principal, los resultados de cada sección y los formularios de
 * registro dentro de la misma ventana, sin abrir diálogos nuevos para el
 * contenido (JOptionPane solo se usa para confirmaciones puntuales).
 */
public class VentanaPrincipal extends JFrame {

    private static final String VISTA_MENU = "menu";
    private static final String VISTA_RESULTADO = "resultado";
    private static final String VISTA_ENTIDADES = "entidades";
    private static final String VISTA_FORM_GUIA = "formGuia";
    private static final String VISTA_FORM_VEHICULO = "formVehiculo";
    private static final String VISTA_FORM_COLABORADOR = "formColaborador";
    private static final String VISTA_FORM_RESERVA = "formReserva";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contenedor = new JPanel(cardLayout);
    private final PanelResultado panelResultado = new PanelResultado();

    private final ArrayList<GuiaTuristico> guias;
    private final ArrayList<Tour> tours;
    private final ArrayList<Cliente> clientes;
    private final GestorTours gestorTours;
    private final GestorEntidades gestorEntidades;
    private final GestorDatos gestorDatos;

    public VentanaPrincipal(ArrayList<GuiaTuristico> guias, ArrayList<Tour> tours, ArrayList<Cliente> clientes,
                             GestorTours gestorTours, GestorEntidades gestorEntidades, GestorDatos gestorDatos) {
        this.guias = guias;
        this.tours = tours;
        this.clientes = clientes;
        this.gestorTours = gestorTours;
        this.gestorEntidades = gestorEntidades;
        this.gestorDatos = gestorDatos;

        setTitle("Llanquihue Tour");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 700);
        setMinimumSize(new java.awt.Dimension(600, 500));
        setLocationRelativeTo(null);

        contenedor.add(new PanelMenuPrincipal(this), VISTA_MENU);
        contenedor.add(panelResultado, VISTA_RESULTADO);
        contenedor.add(new PanelMenuEntidades(this), VISTA_ENTIDADES);
        contenedor.add(new PanelFormularioGuia(this), VISTA_FORM_GUIA);
        contenedor.add(new PanelFormularioVehiculo(this), VISTA_FORM_VEHICULO);
        contenedor.add(new PanelFormularioColaborador(this), VISTA_FORM_COLABORADOR);
        contenedor.add(new PanelFormularioReserva(this, clientes, tours), VISTA_FORM_RESERVA);
        setContentPane(contenedor);

        mostrarMenuPrincipal();
    }

    public GestorEntidades getGestorEntidades() {
        return gestorEntidades;
    }

    public GestorDatos getGestorDatos() {
        return gestorDatos;
    }

    /** Pide un RUT al usuario y muestra la ficha completa del cliente encontrado, si existe. */
    public void buscarClientePorRut() {
        String rut = JOptionPane.showInputDialog(this, "Ingresa el RUT del cliente (ej: 12345678-9):",
                "Buscar cliente por RUT", JOptionPane.QUESTION_MESSAGE);
        if (rut == null || rut.isBlank()) {
            return;
        }
        Cliente encontrado = gestorDatos.buscarClientePorRut(clientes, rut.trim());
        String texto = encontrado != null
                ? encontrado.toString()
                : "No se encontró un cliente con RUT " + rut.trim() + ".";
        mostrarResultado("Buscar cliente por RUT", texto, this::mostrarMenuPrincipal);
    }

    public void mostrarMenuPrincipal() {
        cardLayout.show(contenedor, VISTA_MENU);
    }

    public void mostrarMenuEntidades() {
        cardLayout.show(contenedor, VISTA_ENTIDADES);
    }

    public void mostrarFormularioGuia() {
        cardLayout.show(contenedor, VISTA_FORM_GUIA);
    }

    public void mostrarFormularioVehiculo() {
        cardLayout.show(contenedor, VISTA_FORM_VEHICULO);
    }

    public void mostrarFormularioColaborador() {
        cardLayout.show(contenedor, VISTA_FORM_COLABORADOR);
    }

    public void mostrarFormularioReserva() {
        cardLayout.show(contenedor, VISTA_FORM_RESERVA);
    }

    /** Arma y muestra el resumen del sistema de Tours. */
    public void mostrarSistemaTours() {
        StringBuilder texto = new StringBuilder();
        texto.append("Guías cargados: ").append(guias.size()).append("\n");
        texto.append("Tours cargados: ").append(gestorTours.contarTours()).append("\n");
        texto.append("Clientes cargados: ").append(clientes.size()).append("\n\n");

        texto.append("--- LISTADO COMPLETO DE TOURS ---\n");
        texto.append(gestorTours.formatearTodos()).append("\n");

        texto.append("--- TOURS DE TIPO AVENTURA ---\n");
        texto.append(gestorTours.formatearPorTipo("Aventura")).append("\n");

        texto.append("--- TOURS CON CUPOS DISPONIBLES ---\n");
        texto.append(gestorTours.formatearConCupos()).append("\n");

        texto.append("--- TOURS DE HASTA $25.000 ---\n");
        texto.append(gestorTours.formatearPorPrecioMaximo(25000)).append("\n");

        texto.append("--- BÚSQUEDA: \"Tour Kayak Familiar\" ---\n");
        texto.append(gestorTours.formatearBusqueda("Tour Kayak Familiar")).append("\n\n");

        texto.append("--- CANTIDAD DE TOURS POR TIPO ---\n");
        for (Map.Entry<String, Integer> entrada : gestorTours.contarPorTipo().entrySet()) {
            texto.append(entrada.getKey()).append(": ").append(entrada.getValue()).append("\n");
        }
        texto.append("\n");

        texto.append("--- CLIENTES INSCRITOS POR TOUR ---\n");
        for (Tour tour : tours) {
            texto.append(tour.getNombre()).append(" (").append(tour.getInscritos().size())
                    .append(" inscritos):\n");
            if (tour.getInscritos().isEmpty()) {
                texto.append("   Sin inscritos.\n");
            } else {
                for (Cliente cliente : tour.getInscritos()) {
                    texto.append("   - ").append(cliente.getNombre()).append(" ").append(cliente.getApellido())
                            .append(" (").append(cliente.getNacionalidad()).append(")\n");
                }
            }
        }
        texto.append("\n");

        texto.append("--- GUÍAS TURÍSTICOS ---\n");
        for (GuiaTuristico guia : guias) {
            texto.append(guia).append("\n\n");
        }

        texto.append("--- CLIENTES REGISTRADOS ---\n");
        for (Cliente cliente : clientes) {
            texto.append(cliente).append("\n\n");
        }

        mostrarResultado("Sistema de Tours", texto.toString(), this::mostrarMenuPrincipal);
    }

    /** Carga la colección polimórfica de servicios turísticos y la muestra. */
    public void mostrarServiciosTuristicos() {
        GestorServicios gestorServicios = new GestorServicios();
        gestorServicios.cargarServicios();
        mostrarResultado("Servicios Turísticos", gestorServicios.formatearServicios(), this::mostrarMenuPrincipal);
    }

    public void mostrarEntidadesTodos() {
        mostrarResultado("Entidades registradas", gestorEntidades.formatearTodos(), this::mostrarMenuEntidades);
    }

    public void mostrarEntidadesDetallePorTipo() {
        mostrarResultado("Detalle por tipo", gestorEntidades.formatearDetallePorTipo(),
                this::mostrarMenuEntidades);
    }

    public void mostrarEntidadesConteoPorTipo() {
        StringBuilder texto = new StringBuilder();
        gestorEntidades.contarPorTipo().forEach((tipo, cantidad) ->
                texto.append(tipo).append(": ").append(cantidad).append("\n"));
        mostrarResultado("Conteo por tipo", texto.toString(), this::mostrarMenuEntidades);
    }

    private void mostrarResultado(String titulo, String contenido, Runnable alVolver) {
        panelResultado.actualizar(titulo, contenido, alVolver);
        cardLayout.show(contenedor, VISTA_RESULTADO);
    }
}
