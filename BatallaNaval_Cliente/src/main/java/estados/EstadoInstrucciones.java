package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import vista.VistaInstrucciones;

/**
 *
 * @author alex_
 */
public class EstadoInstrucciones implements IEstadoJuego {

    private Juego juego;
    private VistaInstrucciones vista;

    public EstadoInstrucciones(Juego juego) {
        this.juego = juego;
        this.vista = new VistaInstrucciones(juego.getPanel(), juego);
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
        // no se esperan mensajes en este estado al momento
    }
    
}
