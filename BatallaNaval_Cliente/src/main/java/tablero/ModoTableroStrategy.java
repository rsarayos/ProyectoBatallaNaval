package tablero;

import java.awt.event.MouseEvent;

/**
 *
 * @author alex_
 */
public interface ModoTableroStrategy {

    void mousePressed(MouseEvent e);

    void mouseReleased(MouseEvent e);

    void mouseDragged(MouseEvent e);

}
