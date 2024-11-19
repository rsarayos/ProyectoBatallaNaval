package estados;

import comunicacion.ActualizarEstadoListoComando;
import comunicacion.CrearPartidaComando;
import comunicacion.IComando;
import comunicacion.IniciarOrganizarComando;
import comunicacion.NuevoJugadorComando;
import comunicacion.TodosListosComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ModeloJugador;
import presentador.Juego;
import presentador.PresentadorSalaEspera;
import vista.VistaSalaEspera;

/**
 * Clase que representa el estado de sala de espera del juego.
 *
 * @author alex_
 */
public class EstadoSalaEspera implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;
    
    /**
     * Vista que representa la interfaz de sala de espera.
     */
    private VistaSalaEspera vista;
    
    /**
     * Presentador asociado a la vista de sala de espera.
     */
    private PresentadorSalaEspera presentador;
    
    /**
     * Mapa que contiene los comandos disponibles en el estado de sala de espera.
     */
    private Map<String, IComando> comandos;

    /**
     * Constructor que inicializa el estado de sala de espera con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoSalaEspera(Juego juego) {
        this.juego = juego;
        this.vista = new VistaSalaEspera(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
        inicializarComandos();
    }
    
    /**
     * Constructor que inicializa el estado de sala de espera con el juego especificado y los datos de la partida.
     *
     * @param juego la referencia al juego principal
     * @param codigoAcceso el código de acceso de la partida
     * @param nombresJugadores la lista de nombres de los jugadores en la partida
     */
    public EstadoSalaEspera(Juego juego, String codigoAcceso, List<String> nombresJugadores) {
        this.juego = juego;
        this.vista = new VistaSalaEspera(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
        this.vista.setCodigoAcceso(codigoAcceso);
        this.presentador.actualizarListaJugadores(nombresJugadores);
        inicializarComandos();
    }
    
    /**
     * Inicializa los comandos disponibles para manejar los mensajes en este estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("CREAR_PARTIDA", new CrearPartidaComando(this));
        comandos.put("NUEVO_JUGADOR", new NuevoJugadorComando(this));
        comandos.put("ACTUALIZAR_ESTADO_LISTO", new ActualizarEstadoListoComando(this));
        comandos.put("TODOS_LISTOS", new TodosListosComando(this));
        comandos.put("INICIAR_ORGANIZAR", new IniciarOrganizarComando(this));
    }
    
    /**
     * Sale del estado de sala de espera y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de sala de espera.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de sala de espera.
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
     * Maneja la respuesta de creación de partida.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleCrearPartidaResponse(Map<String, Object> mensaje) {
        String codigoAcceso = (String) mensaje.get("codigo_acceso");
        String idJugador = (String) mensaje.get("id");
        // Guardar el id del jugador en ModeloJugador
        ModeloJugador jugador = ModeloJugador.getInstance();
        jugador.setId(idJugador);
        // Configurar la vista de sala de espera
        vista.setCodigoAcceso(codigoAcceso);
        // Actualizar la lista de jugadores en la sala de espera
        vista.agregarJugador(jugador.getNombre());
    }

    /**
     * Maneja la llegada de un nuevo jugador a la sala de espera.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleNuevoJugador(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        vista.agregarJugador(nombreJugador);
    }

    /**
     * Maneja la actualización del estado de "listo" de un jugador.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleActualizarEstadoListo(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        boolean listo = (Boolean) mensaje.get("listo");
        presentador.manejarActualizarEstadoListo(nombreJugador, listo);
    }

    /**
     * Maneja el evento cuando todos los jugadores están listos en la sala de espera.
     */
    public void handleTodosListos() {
        presentador.manejarTodosListos();
    }

    /**
     * Maneja la transición para iniciar la organización de la partida.
     */
    public void handleIniciarOrganizar() {
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }
    
}
