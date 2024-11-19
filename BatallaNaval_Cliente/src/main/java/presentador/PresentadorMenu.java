package presentador;

import comunicacion.ClientConnection;
import estados.EstadoBuscarPartida;
import estados.EstadoInstrucciones;
import estados.EstadoSalaEspera;
import ivistas.IVistaMenu;
import modelo.ModeloJugador;
import vista.EstadosJuego;

/**
 *
 * @author alex_
 */
public class PresentadorMenu {

    private IVistaMenu vista;
    private ClientConnection clientConnection;
    private ModeloJugador jugador;
    private Juego juego;

    public PresentadorMenu(IVistaMenu vista, Juego juego) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    public void crearPartida() {
        String nombreJugador = jugador.getNombre();
        if (nombreJugador == null || nombreJugador.isEmpty()) {
            vista.mostrarMensajeError("Nombre de jugador no válido");
            return;
        }
        clientConnection.crearPartida(nombreJugador);
        avanzarACrearPartida();
    }
    
    public void avanzarACrearPartida() {
        juego.cambiarEstado(new EstadoSalaEspera(juego));
    }
    
    public void avanzarAUnirseAPartida() {
        juego.cambiarEstado(new EstadoBuscarPartida(juego));
    }
    
    public void avanzarAInstrucciones() {
        juego.cambiarEstado(new EstadoInstrucciones(juego));
    }

}
