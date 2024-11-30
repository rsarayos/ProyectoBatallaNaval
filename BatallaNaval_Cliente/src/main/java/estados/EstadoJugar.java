package estados;

import comunicacion.AtacarComando;
import comunicacion.IComando;
import comunicacion.RendirseComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import presentador.Juego;
import presentador.PresentadorJuego;
import vista.VistaJuego;
import vista.VistaTablero;

/**
 * Clase que representa el estado de juego donde los jugadores atacan.
 *
 * @author alex_
 */
public class EstadoJugar implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de juego.
     */
    private VistaJuego vista;

    /**
     * Presentador asociado a la vista de juego.
     */
    private PresentadorJuego presentador;

    /**
     * Mapa que contiene los comandos disponibles en el estado de juego.
     */
    private Map<String, IComando> comandos;

    /**
     * Constructor que inicializa el estado de juego con el tablero del jugador,
     * su turno y el nombre del oponente.
     *
     * @param juego la referencia al juego principal
     * @param tableroJugador la vista del tablero del jugador
     * @param tuTurno indica si es el turno del jugador
     * @param nombreOponente el nombre del oponente
     */
    public EstadoJugar(Juego juego, VistaTablero tableroJugador, boolean tuTurno, String nombreOponente) {
        this.juego = juego;
        this.vista = new VistaJuego(juego.getPanel());
        this.presentador = vista.getPresentador();

        // variables del juego
        vista.setTableroJugador(tableroJugador);
        presentador.inicializarJuego(nombreOponente, tuTurno);
        inicializarComandos();

    }

    /**
     * Inicializa los comandos disponibles para manejar los mensajes en este
     * estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("ATACAR", new AtacarComando(this));
        comandos.put("RENDIRSE", new RendirseComando(this));
    }

    /**
     * Sale del estado de juego y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de juego.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        if (mensaje == null) {
            return;
        }
        String accion = (String) mensaje.getOrDefault("accion", "default");
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

    /**
     * Maneja la respuesta de un ataque realizado durante el juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleAtacarResponse(Map<String, Object> mensaje) {
        presentador.manejarAtaqueResponse(mensaje);

    }

    /**
     * Maneja la respuesta cuando un jugador se rinde durante el juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleRendirseResponse(Map<String, Object> mensaje) {
        String ganador = (String) mensaje.get("ganador");
        // Notificar al presentador que el juego ha terminado
        presentador.finalizarJuegoPorRendicion(ganador);
    }

}
