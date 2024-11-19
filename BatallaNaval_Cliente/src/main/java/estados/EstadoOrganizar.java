package estados;

import java.awt.Graphics;
import java.util.Map;
import presentador.Juego;
import presentador.PresentadorOrganizar;
import vista.ModoTablero;
import vista.VistaOrganizar;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public class EstadoOrganizar implements IEstadoJuego {
    
    private Juego juego;
    private VistaOrganizar vista;
    private PresentadorOrganizar presentador;

    public EstadoOrganizar(Juego juego) {
        this.juego = juego;
        this.vista = new VistaOrganizar(juego.getPanel());
        this.presentador = vista.getPresentador();
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
            case "INICIAR_JUEGO":
                handleIniciarJuego(mensaje);
                break;
            case "JUGADOR_ESPERANDO":
                handleJugadorEsperando(mensaje);
                break;
            default:
                System.out.println("Acción desconocida en EstadoOrganizar: " + accion);
                break;
        }
    }

    private void handleIniciarJuego(Map<String, Object> mensaje) {
        boolean tuTurno = (Boolean) mensaje.get("tu_turno");
        String nombreOponente = (String) mensaje.get("nombre_oponente");
        // pasar el tablero del jugador a la vista de juego
        VistaTablero tableroJugador = vista.getTablero();
        tableroJugador.setModo(ModoTablero.JUGADOR);
        // Cambiar de estado
        juego.cambiarEstado(new EstadoJugar(juego, tableroJugador, tuTurno, nombreOponente));
    }

    private void handleJugadorEsperando(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        presentador.manejarJugadorEsperando(nombreJugador);

    }

}
