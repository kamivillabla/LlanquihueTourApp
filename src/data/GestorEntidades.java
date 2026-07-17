package data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.ColaboradorExterno;
import model.GuiaTuristico;
import model.Registrable;
import model.Vehiculo;

/**
 * Gestiona la colección polimórfica de entidades registrables de la agencia
 * (guías turísticos, vehículos, colaboradores externos) en una única
 * List<Registrable>, y las recorre aplicando instanceof.
 */
public class GestorEntidades {

    private final List<Registrable> entidades = new ArrayList<>();

    public void agregar(Registrable entidad) {
        if (entidad == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        entidades.add(entidad);
    }

    /** Sobrecarga de {@link #agregar(Registrable)}: agrega varias entidades de una vez. */
    public void agregar(List<? extends Registrable> entidades) {
        if (entidades == null) {
            throw new IllegalArgumentException("La lista de entidades no puede ser nula.");
        }
        for (Registrable entidad : entidades) {
            agregar(entidad);
        }
    }

    /** @return el resumen de cada entidad, una por línea */
    public String formatearTodos() {
        StringBuilder texto = new StringBuilder();
        for (Registrable entidad : entidades) {
            texto.append(entidad.mostrarResumen()).append("\n");
        }
        return texto.toString();
    }

    /**
     * Recorre la colección y usa instanceof para describir a cada entidad
     * según su tipo real, con un comportamiento distinto por clase.
     * @return el detalle de cada entidad, diferenciado por tipo, una por línea
     */
    public String formatearDetallePorTipo() {
        StringBuilder texto = new StringBuilder();
        for (Registrable entidad : entidades) {
            if (entidad instanceof GuiaTuristico guia) {
                texto.append("[GUÍA] ").append(guia.getNombre())
                        .append(" domina ").append(guia.getIdiomas())
                        .append(" con ").append(guia.getAniosExperiencia())
                        .append(" años de experiencia.\n");
            } else if (entidad instanceof Vehiculo vehiculo) {
                texto.append("[VEHÍCULO] ").append(vehiculo.getNombre())
                        .append(" (").append(vehiculo.getTipo())
                        .append(") transporta hasta ").append(vehiculo.getCapacidad())
                        .append(" pasajeros.\n");
            } else if (entidad instanceof ColaboradorExterno colaborador) {
                texto.append("[COLABORADOR] ").append(colaborador.getNombre())
                        .append(" de ").append(colaborador.getEmpresa())
                        .append(" presta: ").append(colaborador.getServicioPrestado())
                        .append("\n");
            } else {
                texto.append("[ENTIDAD] ").append(entidad.mostrarResumen()).append("\n");
            }
        }
        return texto.toString();
    }

    /** @return cuántas entidades hay de cada clase real, usando instanceof */
    public Map<String, Integer> contarPorTipo() {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        conteo.put("Guías turísticos", 0);
        conteo.put("Vehículos", 0);
        conteo.put("Colaboradores externos", 0);

        for (Registrable entidad : entidades) {
            if (entidad instanceof GuiaTuristico) {
                conteo.merge("Guías turísticos", 1, Integer::sum);
            } else if (entidad instanceof Vehiculo) {
                conteo.merge("Vehículos", 1, Integer::sum);
            } else if (entidad instanceof ColaboradorExterno) {
                conteo.merge("Colaboradores externos", 1, Integer::sum);
            }
        }
        return conteo;
    }
}
