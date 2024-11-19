package estados;

import comunicacion.IComando;
import comunicacion.UnirsePartidaComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.ModeloJugador;
import presentador.Juego;
import presentador.PresentadorBuscarPartida;
import vista.VistaBuscarPartida;

/**
 * Clase que representa el estado de búsqueda de partida del juego.
 *
 * @author alex_
 */
public class EstadoBuscarPartida implements IEstadoJuego {
    
    /**
     * Referencia al juego principal.
     */
    private Juego juego;
    
    /**
     * Vista que representa la interfaz de búsqueda de partida.
     */
    private VistaBuscarPartida vista;
    
    /**
     * Presentador asociado a la vista de búsqueda de partida.
     */
    private PresentadorBuscarPartida presentador;
    
    /**
     * Mapa que contiene los comandos disponibles en el estado de búsqueda de partida.
     */
    private Map<String, IComando> comandos;

    /**
     * Constructor que inicializa el estado de búsqueda de partida con el juego especificado.
     *
     * @param juego la referencia al juego principal
     */
    public EstadoBuscarPartida(Juego juego) {
        this.juego = juego;
        this.vista = new VistaBuscarPartida(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
        inicializarComandos();
    }
    
    /**
     * Inicializa los comandos disponibles para manejar los mensajes en este estado.
     */
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("UNIRSE_PARTIDA", new UnirsePartidaComando(this));
    }

    /**
     * Sale del estado de búsqueda de partida y quita los componentes de la vista.
     */
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    /**
     * Renderiza la vista de búsqueda de partida.
     *
     * @param g el objeto Graphics utilizado para dibujar la vista
     */
    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    /**
     * Maneja un mensaje recibido en el estado de búsqueda de partida.
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
            System.out.println("Acción desconocida en EstadoBuscarPartida: " + accion);
        }
    }

    /**
     * Maneja la respuesta para unirse a una partida.
     *
     * @param mensaje un mapa que contiene los datos del mensaje recibido
     */
    public void handleUnirsePartidaResponse(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            // Mostrar el mensaje de error en la vista actual
            vista.mostrarMensaje(error);
        } else {
            String idJugador = (String) mensaje.get("id");
            String codigoAcceso = (String) mensaje.get("codigo_acceso");
            List<String> nombresJugadores = (List<String>) mensaje.get("nombres_jugadores");

            // Guardar el id del jugador en ModeloJugador
            ModeloJugador jugador = ModeloJugador.getInstance();
            jugador.setId(idJugador);

            // Limpiar la vista actual
            vista.quitarComponentes();

            // Cambiar al estado de sala de espera, pasando los datos necesarios
            juego.cambiarEstado(new EstadoSalaEspera(juego, codigoAcceso, nombresJugadores));
        }
    }

}
