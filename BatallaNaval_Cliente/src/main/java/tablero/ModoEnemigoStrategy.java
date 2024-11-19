package tablero;

import java.awt.event.MouseEvent;
import presentador.PresentadorTablero;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public class ModoEnemigoStrategy implements ModoTableroStrategy {

    private PresentadorTablero presentador;
    private VistaTablero vista;

    public ModoEnemigoStrategy(VistaTablero vista, PresentadorTablero presentador) {
        this.vista = vista;
        this.presentador = presentador;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!vista.isInteraccionHabilitada()) {
            return;
        }
        vista.manejarClickEnemigo(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No hace nada en este modo
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // No hace nada en este modo
    }
}
