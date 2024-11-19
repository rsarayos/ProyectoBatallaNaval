package tablero;

import java.awt.event.MouseEvent;
import presentador.PresentadorTablero;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public class ModoOrganizarStrategy implements ModoTableroStrategy {
    
    private PresentadorTablero presentador;
    private VistaTablero vista;
    
    public ModoOrganizarStrategy(VistaTablero vista, PresentadorTablero presentador) {
        this.vista = vista;
        this.presentador = presentador;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        presentador.onMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        presentador.onMouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        presentador.onMouseDragged(e);
    }
    
}
