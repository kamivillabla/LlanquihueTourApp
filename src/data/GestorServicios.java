package data;

import java.util.ArrayList;
import model.ExcursionCultural;
import model.PaseoLacustre;
import model.RutaGastronomica;
import model.ServicioTuristico;

/**
 * Arma los servicios turísticos de prueba que muestra el programa.
 */
public class GestorServicios {

    /** Crea dos servicios de cada tipo y los devuelve en una sola lista. */
    public ArrayList<ServicioTuristico> crearServicios() {
        ArrayList<ServicioTuristico> servicios = new ArrayList<>();

        servicios.add(new RutaGastronomica("Sabores del Lago", 4, 5));
        servicios.add(new RutaGastronomica("Ruta del Queso Artesanal", 3, 4));

        servicios.add(new PaseoLacustre("Navegación Lago Llanquihue", 2, "Lancha turística"));
        servicios.add(new PaseoLacustre("Atardecer Lacustre", 3, "Catamarán"));

        servicios.add(new ExcursionCultural("Historia de Frutillar", 3, "Teatro del Lago"));
        servicios.add(new ExcursionCultural("Patrimonio de Puerto Varas", 4, "Iglesia del Sagrado Corazón"));

        return servicios;
    }
}
