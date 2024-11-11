package comunicacion;

import Convertidores.PruebaDeserializer;
import dto.PruebaDTO;
import enums.AccionesJugador;
import static enums.AccionesJugador.ATACAR;
import static enums.AccionesJugador.ORDENAR;
import java.io.IOException;
import java.util.Map;
import org.msgpack.core.MessageUnpacker;

public class HandlerActions {

    public void handlerAction(Map<String, Object> data) throws IOException {

        if (AccionesJugador.ORDENAR.toString().equalsIgnoreCase(String.valueOf(data.get("accion")))) {
            PruebaDTO prueba = new PruebaDTO((Integer) data.get("x"), (Integer) data.get("y"), AccionesJugador.ORDENAR);
            System.out.println("Este es un ejemplo del handlerAction para " + prueba);
        } else {

        }
    }
}

//        public void handlerAction(String action) {
//        Accion accion = getAccion(action);  // Obtener la acción correspondiente
//        
//        if (accion != null) {
//            accion.ejecutar();  // Ejecutar la acción
//        } else {
//            System.out.println("Acción no reconocida: " + action);
//        }
//    }
//
//    private Accion getAccion(String action) {
//        // Aquí se mapea la acción a su clase correspondiente
//        switch (AccionesJugador.valueOf(action.toUpperCase())) {
//            case ORDENAR:
//                return new AccionOrdenar();
//            case ATACAR:
//                return new AccionAtacar();
//            case MOVER:
//                return new AccionMover();
//            default:
//                return null;
//        }
//    }
