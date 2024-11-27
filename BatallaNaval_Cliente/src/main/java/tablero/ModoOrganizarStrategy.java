package tablero;

import java.awt.event.MouseEvent;
import presentador.PresentadorTablero;
import vista.VistaTablero;

/**
 * Estrategia para el modo organizar del tablero. En este modo, se permite al jugador organizar las unidades en su tablero.
 *
 * @author alex_
 */
public class ModoOrganizarStrategy implements ModoTableroStrategy {
    
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
    public ModoOrganizarStrategy(VistaTablero vista, PresentadorTablero presentador) {
        this.vista = vista;
        this.presentador = presentador;
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero para organizar las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mousePressed(MouseEvent e) {
        presentador.onMousePressed(e);
    }

    /**
     * Maneja el evento cuando se suelta el ratón en el tablero para organizar las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        presentador.onMouseReleased(e);
    }

    /**
     * Maneja el evento cuando se arrastra el ratón en el tablero para organizar las unidades.
     *
     * @param e el evento de ratón
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        presentador.onMouseDragged(e);
    }
    
}
