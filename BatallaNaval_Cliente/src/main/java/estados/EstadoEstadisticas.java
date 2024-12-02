package estados;

import comunicacion.IComando;
import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import presentador.PresentadorEstadisticas;
import vista.VistaEstadisticas;

/**
 *
 * @author alex_
 */
public class EstadoEstadisticas implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de juego.
     */
    private VistaEstadisticas vista;

    /**
     * Presentador asociado a la vista de juego.
     */
    private PresentadorEstadisticas presentador;

    /**
     * Mapa que contiene los comandos disponibles en el estado de juego.
     */
    private Map<String, IComando> comandos;

    /**
     * Mapa que contiene las estadisticas de los jugadores.
     */
    private Map<String, Object> estadisticas;

    public EstadoEstadisticas(Juego juego, Map<String, Object> estadisticas) {
        this.juego = juego;
        this.estadisticas = estadisticas;
        this.vista = new VistaEstadisticas(juego.getPanel(), estadisticas);
    }

    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        // pendiente
    }

}
