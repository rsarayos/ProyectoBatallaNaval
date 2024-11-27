package tablero;

import java.awt.event.MouseEvent;

/**
 * Estrategia para el modo jugador del tablero. En este modo, el jugador no interactúa directamente con el tablero.
 *
 * @author alex_
 */
public class ModoJugadorStrategy implements ModoTableroStrategy {

    /**
     * Maneja el evento cuando se presiona el ratón. No tiene comportamiento en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se suelta el ratón. No tiene comportamiento en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // No hace nada en este modo
    }

    /**
     * Maneja el evento cuando se arrastra el ratón. No tiene comportamiento en este modo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // No hace nada en este modo
    }
    
}
