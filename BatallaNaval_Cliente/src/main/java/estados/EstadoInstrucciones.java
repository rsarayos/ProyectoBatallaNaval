package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import vista.VistaInstrucciones;

/**
 * Clase que representa el estado de instrucciones del juego.
 *
 * @author alex_
 */
public class EstadoInstrucciones implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;
    
    /**
     * Vista que representa la interfaz de instrucciones.
     */
    private VistaInstrucciones vista;

    /**
     * Constructor que inicializa el estado de instrucciones con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoInstrucciones(Juego juego) {
        this.juego = juego;
        this.vista = new VistaInstrucciones(juego.getPanel(), juego);
    }
    
    /**
     * Sale del estado de instrucciones y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de instrucciones.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de instrucciones.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        // no se esperan mensajes en este estado al momento
    }
    
}
