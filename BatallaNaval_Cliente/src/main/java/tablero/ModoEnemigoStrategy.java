package tablero;

import java.awt.event.MouseEvent;
import presentador.PresentadorTablero;
import vista.VistaTablero;

/**
 * Estrategia para el modo enemigo del tablero. En este modo, se maneja la interacción con el tablero enemigo.
 *
 * @author alex_
 */
public class ModoEnemigoStrategy implements ModoTableroStrategy {

    /**
     * Presentador del tablero asociado a esta estrategia.
     */
    private PresentadorTablero presentador;
    
    /**
     * Vista del tablero asociado a esta estrategia.
     */
    private VistaTablero vista;

    /**
     * Constructor que inicializa la estrategia con la vista y el presentador especificados.
     *
     * @param vista la vista del tablero
     * @param presentador el presentador del tablero
     */
    public ModoEnemigoStrategy(VistaTablero vista, PresentadorTablero presentador) {
        this.vista = vista;
        this.presentador = presentador;
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero enemigo.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!vista.isInteraccionHabilitada()) {
            return;
        }
        vista.manejarClickEnemigo(e);
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
