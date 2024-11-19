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
 * Clase que representa el estado de organización de las naves del juego.
 *
 * @author alex_
 */
public class EstadoOrganizar implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;
    
    /**
     * Vista que representa la interfaz de organización de naves.
     */
    private VistaOrganizar vista;
    
    /**
     * Presentador asociado a la vista de organización de naves.
     */
    private PresentadorOrganizar presentador;
    
    /**
     * Mapa que contiene los comandos disponibles en el estado de organización.
     */
    private Map<String, IComando> comandos;

    /**
     * Constructor que inicializa el estado de organización con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoOrganizar(Juego juego) {
        this.juego = juego;
        this.vista = new VistaOrganizar(juego.getPanel());
        this.presentador = vista.getPresentador();
        inicializarComandos();
    }

    /**
     * Inicializa los comandos disponibles para manejar los mensajes en este estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("INICIAR_JUEGO", new IniciarJuegoComando(this));
        comandos.put("JUGADOR_ESPERANDO", new JugadorEsperandoComando(this));
    }

    /**
     * Sale del estado de organización y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de organización de naves.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de organización.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
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

    /**
     * Maneja la transición para iniciar el juego.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleIniciarJuego(Map<String, Object> mensaje) {
        boolean tuTurno = (Boolean) mensaje.get("tu_turno");
        String nombreOponente = (String) mensaje.get("nombre_oponente");
        // pasar el tablero del jugador a la vista de juego
        VistaTablero tableroJugador = vista.getTablero();
        tableroJugador.setModo(ModoTablero.JUGADOR);
        // Cambiar de estado
        juego.cambiarEstado(new EstadoJugar(juego, tableroJugador, tuTurno, nombreOponente));
    }

    /**
     * Maneja el evento cuando un jugador está esperando.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleJugadorEsperando(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        presentador.manejarJugadorEsperando(nombreJugador);

    }

}
