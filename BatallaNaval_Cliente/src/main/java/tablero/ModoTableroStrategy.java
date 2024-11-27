package tablero;

import java.awt.event.MouseEvent;

/**
 * Interfaz para las estrategias de interacción con el tablero, que define los eventos del ratón que se manejan.
 *
 * @author alex_
 */
public interface ModoTableroStrategy {

    /**
     * Maneja el evento cuando se presiona el ratón.
     *
     * @param e el evento de ratón
     */
    void mousePressed(MouseEvent e);

    /**
     * Maneja el evento cuando se suelta el ratón.
     *
     * @param e el evento de ratón
     */
    void mouseReleased(MouseEvent e);

    /**
     * Maneja el evento cuando se arrastra el ratón.
     *
     * @param e el evento de ratón
     */
    void mouseDragged(MouseEvent e);

}
