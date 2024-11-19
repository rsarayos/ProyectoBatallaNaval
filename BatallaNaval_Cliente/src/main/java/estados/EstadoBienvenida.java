package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import vista.VistaBienvenida;

/**
 * Clase que representa el estado de bienvenida del juego.
 *
 * @author alex_
 */
public class EstadoBienvenida implements IEstadoJuego {
    
    /**
     * Referencia al juego principal.
     */
    private Juego juego;
    
    /**
     * Vista que representa la interfaz de bienvenida.
     */
    private VistaBienvenida vista;

    /**
     * Constructor que inicializa el estado de bienvenida con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoBienvenida(Juego juego) {
        this.juego = juego;
        this.vista = new VistaBienvenida(juego.getPanel(), juego);

    }

    /**
     * Sale del estado de bienvenida y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de bienvenida.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de bienvenida.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        // no se esperan mensajes en este estado al momento
    }
    
}
