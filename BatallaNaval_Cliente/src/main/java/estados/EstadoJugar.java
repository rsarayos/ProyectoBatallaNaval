package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import presentador.PresentadorJuego;
import vista.VistaJuego;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public class EstadoJugar implements IEstadoJuego {

    private Juego juego;
    private VistaJuego vista;
    private PresentadorJuego presentador;

    public EstadoJugar(Juego juego, VistaTablero tableroJugador, boolean tuTurno, String nombreOponente) {
        this.juego = juego;
        this.vista = new VistaJuego(juego.getPanel());
        this.presentador = vista.getPresentador();
        
        // variables del juego
        vista.setTableroJugador(tableroJugador);
        presentador.inicializarJuego(nombreOponente, tuTurno);
        
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
        String accion = (String) mensaje.get("accion");
        if (accion == null) {
            System.err.println("Mensaje sin 'accion': " + mensaje);
            return;
        }

        switch (accion) {
            case "ATACAR":
                handleAtacarResponse(mensaje);
                break;
            default:
                System.out.println("Acción desconocida en EstadoJugar: " + accion);
                break;
        }
    }

    private void handleAtacarResponse(Map<String, Object> mensaje) {
        presentador.manejarAtaqueResponse(mensaje);

    }

}
