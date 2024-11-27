package presentador;

import comunicacion.ClientConnection;
import estados.EstadoMenu;
import ivistas.IVistaBuscarPartida;
import java.util.Map;
import modelo.ModeloJugador;

/**
 * Clase que actúa como presentador de la vista de búsqueda de partida del juego.
 *
 * @author alex_
 */
public class PresentadorBuscarPartida {

    /**
     * Vista de búsqueda de partida.
     */
    private IVistaBuscarPartida vista;
    
    /**
     * Conexión con el servidor.
     */
    private ClientConnection clientConnection;
    
    /**
     * Modelo del jugador.
     */
    private ModeloJugador jugador;
    
    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el presentador con la vista y el juego especificados.
     *
     * @param vista la vista de búsqueda de partida
     * @param juego la referencia al juego principal
     */
    public PresentadorBuscarPartida(IVistaBuscarPartida vista, Juego juego) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Intenta unirse a una partida utilizando el código de acceso proporcionado.
     */
    public void unirseAPartida() {
        String codigoAcceso = vista.obtenerCodigoAcceso();
        if (codigoAcceso.isEmpty()) {
            vista.mostrarMensaje("Por favor, ingresa el código de la sala.");
            return;
        }
        String nombreJugador = jugador.getNombre();
        clientConnection.unirsePartida(codigoAcceso, nombreJugador);
    }

    /**
     * Regresa al menú principal.
     */
    public void regresarAlMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Maneja la respuesta recibida al intentar unirse a una partida.
     *
     * @param mensaje el mensaje recibido del servidor
     */
    public void manejarRespuestaUnirsePartida(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            vista.mostrarMensaje(error);
        } 
    }

}
