package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import vista.VistaMenu;

/**
 * Clase que representa el estado del menú del juego.
 *
 * @author alex_
 */
public class EstadoMenu implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;
    
    /**
     * Vista que representa la interfaz del menú.
     */
    private VistaMenu vista;

    /**
     * Constructor que inicializa el estado del menú con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoMenu(Juego juego) {
        this.juego = juego;
        this.vista = new VistaMenu(juego.getPanel(), juego);
    }

    /**
     * Sale del estado del menú y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista del menú.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado del menú.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        // no se esperan mensajes en este estado al momento
    }
    
}
