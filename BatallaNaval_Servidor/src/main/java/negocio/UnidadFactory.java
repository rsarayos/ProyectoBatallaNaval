/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dominio.TipoUnidad;
import dominio.Unidad;
import enums.Orientacion;
import java.util.Map;

/**
 *
 * @author af_da
 */
class UnidadFactory {

//    private static final Map<String, Map<String, Object>> UNIDADES_INFO = Map.of(
//            "barco", Map.of("nombre", "Barco", "vida", 1, "tamano", 1),
//            "submarino", Map.of("nombre", "Submarino", "vida", 2, "tamano", 2),
//            "crucero", Map.of("nombre", "Crucero", "vida", 3, "tamano", 3),
//            "portaaviones", Map.of("nombre", "Portaaviones", "vida", 4, "tamano", 4)
//    );
//
//    public static Unidad crearUnidad(String tipo, String orientacion) {
//
//        Map<String, Object> atributosUnidad = UNIDADES_INFO.get(tipo);
//        if (atributosUnidad == null) {
//            throw new IllegalArgumentException("Tipo de unidad desconocido: " + tipo);
//        }
//
//        String nombre = (String) atributosUnidad.get("nombre");
//        int vida = (int) atributosUnidad.get("vida");
//        int tamano = (int) atributosUnidad.get("tamano");
//        Orientacion orientacionEnum = Orientacion.valueOf(orientacion.toUpperCase());
//
//        return new Unidad(nombre, vida, tamano, orientacionEnum);
//    }
    
    public static Unidad crearUnidad(String tipo) {
        if (TipoUnidad.BARCO.NOMBRE.equals(tipo)) {
            return new Unidad(TipoUnidad.BARCO.NOMBRE, TipoUnidad.BARCO.VIDA);
        } else if (TipoUnidad.SUBMARINO.NOMBRE.equals(tipo)) {
            return new Unidad(TipoUnidad.SUBMARINO.NOMBRE, TipoUnidad.SUBMARINO.VIDA);
        } else if (TipoUnidad.CRUCERO.NOMBRE.equals(tipo)) {
            return new Unidad(TipoUnidad.CRUCERO.NOMBRE, TipoUnidad.CRUCERO.VIDA);
        } else if (TipoUnidad.PORTAAVIONES.NOMBRE.equals(tipo)) {
            return new Unidad(TipoUnidad.PORTAAVIONES.NOMBRE, TipoUnidad.PORTAAVIONES.VIDA);
        }
        // No se encontro el tipo
        return null;
    }

}
