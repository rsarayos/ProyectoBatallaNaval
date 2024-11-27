package presentador;

import comunicacion.ClientConnection;
import ivistas.IVistaSalaEspera;
import java.util.List;
import modelo.ModeloJugador;

/**
 * Clase que actúa como presentador de la vista de sala de espera del juego, manejando la interacción de los jugadores y el inicio de la partida.
 *
 * @author alex_
 */
public class PresentadorSalaEspera {

    /**
     * Vista de sala de espera.
     */
    private IVistaSalaEspera vista;
    
    /**
     * Conexión con el servidor.
     */
    private ClientConnection clientConnection;
    
    /**
     * Modelo del jugador.
     */
    private ModeloJugador jugador;

    /**
     * Constructor que inicializa el presentador con la vista especificada.
     *
     * @param vista la vista de sala de espera
     */
    public PresentadorSalaEspera(IVistaSalaEspera vista) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
    }

    /**
     * Inicia la sala de espera, mostrando el código de acceso y la lista de jugadores.
     */
    public void iniciar() {
        // Obtener el código de acceso y el ID del jugador si es necesario        
//        vista.mostrarCodigoAcceso(codigoAcceso);
        // Inicializar lista de jugadores si es necesario
    }

    /**
     * Marca al jugador como listo y envía la notificación al servidor.
     */
    public void jugadorListo() {
        if (!vista.isEstoyListo()) {
            vista.setEstoyListo(true);
            vista.bloquearBotonContinuar();
            clientConnection.jugadorListo();
        }
    }

    /**
     * Sale de la sala de espera y regresa al menú principal.
     */
    public void salir() {
        vista.navegarAMenu();
    }

    /**
     * Agrega o actualiza un jugador en la lista de la sala de espera.
     *
     * @param nombreJugador el nombre del jugador
     * @param listo indica si el jugador está listo
     */
    public void agregarOActualizarJugador(String nombreJugador, boolean listo) {
        vista.agregarOActualizarJugador(nombreJugador, listo);
    }

    /**
     * Limpia la lista de jugadores en la vista de la sala de espera.
     */
    public void limpiarListaJugadores() {
        vista.limpiarListaJugadores();
    }

    /**
     * Inicia la organización de las unidades cambiando al estado correspondiente.
     */
    public void iniciarOrganizar() {
        vista.navegarAOrganizar();
    }
    
    /**
     * Actualiza la lista de jugadores en la vista de la sala de espera.
     *
     * @param nombresJugadores la lista de nombres de los jugadores
     */
    public void actualizarListaJugadores(List<String> nombresJugadores) {
        vista.limpiarListaJugadores();
        for (String nombreJugador : nombresJugadores) {
            vista.agregarOActualizarJugador(nombreJugador, false); // Suponiendo que no sabemos si están listos
        }
    }
    
    /**
     * Maneja la actualización del estado de "listo" de un jugador.
     *
     * @param nombreJugador el nombre del jugador
     * @param listo indica si el jugador está listo
     */
    public void manejarActualizarEstadoListo(String nombreJugador, boolean listo) {
        vista.agregarOActualizarJugador(nombreJugador, listo);
    }
    
    /**
     * Maneja el evento cuando todos los jugadores están listos e inicia la organización de las unidades.
     */
    public void manejarTodosListos() {
        vista.navegarAOrganizar();
    }

}
