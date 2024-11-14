package negocio;

import dominio.Casilla;
import dominio.Coordenada;
import dominio.Jugador;
import dominio.UbicacionUnidad;
import dominio.Unidad;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author af_da
 */
public class UnidadBO {

//    public UbicacionUnidad crearUbicacionUnidad(Map<String, Object> data) {
//        String tipo_nave = (String) data.get("tipo_nave");
//        String orientacion = (String) data.get("orientacion");
//        Unidad unidad = UnidadFactory.crearUnidad(tipo_nave, orientacion);
//        //Aqui podriamos quitar tanto el tipo como la orientacion del mapa una vez obtenidas las variables del mapa como está arriba
//        //para evitar recorrer mucho en el for del metodo obtenerCoordenadas
//        //De hecho lo mas correcto seria crear un mapa para mandarlo para acá solo con los campos necesarios.
//        Map<Casilla, Boolean> casillas = this.obtenerCoordenadas(data);
//        return new UbicacionUnidad(unidad, casillas);
//    }

    public Map<Casilla, Boolean> obtenerCoordenadas(Map<String, Object> data) {
        Map<Casilla, Boolean> casillas = new HashMap<>();

        // Suponiendo que las coordenadas se envían como una lista de mapas
        // donde cada mapa tiene las claves "x" y "y"
        List<Map<String, Integer>> coordenadas = (List<Map<String, Integer>>) data.get("coordenadas");

        for (Map<String, Integer> coord : coordenadas) {
            int x = coord.get("x");
            int y = coord.get("y");
            Casilla casilla = new Casilla(new Coordenada(x, y));
            casillas.put(casilla, Boolean.TRUE);
        }

        return casillas;
    }

    Unidad obtenerUnidadPorNumNave(Jugador jugador, int numNave) {
        for (Unidad unidad : jugador.getUnidades()) {
            if (unidad.getNumNave() == numNave) {
                return unidad;
            }
        }
        return null; // Si no se encuentra la unidad
    }
}

