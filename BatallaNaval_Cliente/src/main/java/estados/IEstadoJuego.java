package estados;

import java.awt.Graphics;
import java.util.Map;

/**
 * Interfaz que representa un estado del juego.
 *
 * @author alex_
 */
public interface IEstadoJuego {

    /**
     * Salir del estado actual del juego.
     */
    void salir();

    /**
     * Renderiza el estado actual del juego.
     *
     * @param g el objeto Graphics utilizado para dibujar el estado
     */
    void renderizar(Graphics g); 

    /**
     * Maneja un mensaje recibido en el estado actual del juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    void handleMessage(Map<String, Object> mensaje);

}
