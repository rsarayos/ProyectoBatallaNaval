/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dominio.Casilla;
import dominio.Coordenada;
import dominio.UbicacionUnidad;
import dominio.Unidad;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author af_da
 */
public class UnidadBO {

    public UbicacionUnidad crearUbicacionUnidad(Map<String, Object> data) {
        String tipo_nave = (String) data.get("tipo_nave");
        String orientacion = (String) data.get("orientacion");
        Unidad unidad = UnidadFactory.crearUnidad(tipo_nave, orientacion);
        //Aqui podriamos quitar tanto el tipo como la orientacion del mapa una vez obtenidas las variables del mapa como está arriba
        //para evitar recorrer mucho en el for del metodo obtenerCoordenadas
        //De hecho lo mas correcto seria crear un mapa para mandarlo para acá solo con los campos necesarios.
        Map<Casilla, Boolean> casillas = this.obtenerCoordenadas(data);
        return new UbicacionUnidad(unidad, casillas);
    }

    public Map<Casilla, Boolean> obtenerCoordenadas(Map<String, Object> data) {
        Map<Casilla, Boolean> casillas = new HashMap();
        for (int i = 1; i < data.size() + 1; i++) {
            String numero_coordenada = String.valueOf(i);
            Integer coordenada_x = (Integer) data.getOrDefault("x" + numero_coordenada, null);
            Integer coordenada_y = (Integer) data.getOrDefault("y" + numero_coordenada, null);
            casillas.put(new Casilla(new Coordenada(coordenada_x, coordenada_y)), Boolean.TRUE);
            if (coordenada_x == null && coordenada_y == null) {
                break;
            }
        }
        return casillas;
    }
}

