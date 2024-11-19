package estados;

import comunicacion.IComando;
import comunicacion.IniciarJuegoComando;
import comunicacion.JugadorEsperandoComando;
import java.awt.Graphics;
import java.util.HashMap;
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
    private Map<String, IComando> comandos;

    public EstadoOrganizar(Juego juego) {
        this.juego = juego;
        this.vista = new VistaOrganizar(juego.getPanel());
        this.presentador = vista.getPresentador();
        inicializarComandos();
    }

    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("INICIAR_JUEGO", new IniciarJuegoComando(this));
        comandos.put("JUGADOR_ESPERANDO", new JugadorEsperandoComando(this));
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

        IComando comando = comandos.get(accion);
        if (comando != null) {
            comando.execute(mensaje);
        } else {
            System.out.println("Acción desconocida en EstadoSalaEspera: " + accion);
        }
    }

    public void handleIniciarJuego(Map<String, Object> mensaje) {
        boolean tuTurno = (Boolean) mensaje.get("tu_turno");
        String nombreOponente = (String) mensaje.get("nombre_oponente");
        // pasar el tablero del jugador a la vista de juego
        VistaTablero tableroJugador = vista.getTablero();
        tableroJugador.setModo(ModoTablero.JUGADOR);
        // Cambiar de estado
        juego.cambiarEstado(new EstadoJugar(juego, tableroJugador, tuTurno, nombreOponente));
    }

    public void handleJugadorEsperando(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        presentador.manejarJugadorEsperando(nombreJugador);

    }

}
