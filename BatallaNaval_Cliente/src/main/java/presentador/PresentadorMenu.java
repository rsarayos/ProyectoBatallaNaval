package presentador;

import comunicacion.ClientConnection;
import estados.EstadoBuscarPartida;
import estados.EstadoInstrucciones;
import estados.EstadoSalaEspera;
import ivistas.IVistaMenu;
import modelo.ModeloJugador;

/**
 * Clase que actúa como presentador de la vista del menú del juego, manejando la lógica de navegación y creación de partidas.
 *
 * @author alex_
 */
public class PresentadorMenu {

    /**
     * Vista del menú.
     */
    private IVistaMenu vista;
    
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
     * @param vista la vista del menú
     * @param juego la referencia al juego principal
     */
    public PresentadorMenu(IVistaMenu vista, Juego juego) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Crea una nueva partida y cambia al estado correspondiente.
     */
    public void crearPartida() {
        String nombreJugador = jugador.getNombre();
        if (nombreJugador == null || nombreJugador.isEmpty()) {
            vista.mostrarMensajeError("Nombre de jugador no válido");
            return;
        }
        clientConnection.crearPartida(nombreJugador);
        avanzarACrearPartida();
    }
    
    /**
     * Cambia al estado de creación de partida (sala de espera).
     */
    public void avanzarACrearPartida() {
        juego.cambiarEstado(new EstadoSalaEspera(juego));
    }
    
    /**
     * Cambia al estado de unión a una partida existente.
     */
    public void avanzarAUnirseAPartida() {
        juego.cambiarEstado(new EstadoBuscarPartida(juego));
    }
    
    /**
     * Cambia al estado de instrucciones del juego.
     */
    public void avanzarAInstrucciones() {
        juego.cambiarEstado(new EstadoInstrucciones(juego));
    }

}
