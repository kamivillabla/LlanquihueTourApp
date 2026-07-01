package data;

import java.util.ArrayList;
import java.util.List;
import model.ExcursionCultural;
import model.PaseoLacustre;
import model.RutaGastronomica;
import model.ServicioTuristico;

/**
 * Gestiona la colección polimórfica de servicios turísticos: guarda objetos de
 * distintas subclases en una misma {@code List<ServicioTuristico>} y los recorre.
 */
public class GestorServicios {

    private final List<ServicioTuristico> servicios = new ArrayList<>();

    /** Carga en la lista objetos de las distintas subclases. */
    public void cargarServicios() {
        servicios.add(new RutaGastronomica("Sabores del Lago", 4, 5));
        servicios.add(new RutaGastronomica("Ruta del Queso Artesanal", 3, 4));

        servicios.add(new PaseoLacustre("Navegación Lago Llanquihue", 2, "Lancha turística"));
        servicios.add(new PaseoLacustre("Atardecer Lacustre", 3, "Catamarán"));

        servicios.add(new ExcursionCultural("Historia de Frutillar", 3, "Teatro del Lago"));
        servicios.add(new ExcursionCultural("Patrimonio de Puerto Varas", 4, "Iglesia del Sagrado Corazón"));
    }

    /** Recorre la colección y llama a {@code mostrarInformacion()} de cada servicio (polimorfismo). */
    public void mostrarServicios() {
        for (ServicioTuristico servicio : servicios) {
            servicio.mostrarInformacion();
        }
    }

    /** @return la colección de servicios turísticos */
    public List<ServicioTuristico> getServicios() {
        return servicios;
    }
}
