package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import vista.VistaBienvenida;

/**
 *
 * @author alex_
 */
public class EstadoBienvenida implements IEstadoJuego {
    
    private Juego juego;
    private VistaBienvenida vista;

    public EstadoBienvenida(Juego juego) {
        this.juego = juego;
        this.vista = new VistaBienvenida(juego.getPanel(), juego);

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
