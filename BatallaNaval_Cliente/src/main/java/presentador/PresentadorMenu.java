package presentador;

import comunicacion.ClientConnection;
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

    public PresentadorMenu(IVistaMenu vista) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
    }

    public void crearPartida() {
        String nombreJugador = jugador.getNombre();
        if (nombreJugador == null || nombreJugador.isEmpty()) {
            vista.mostrarMensajeError("Nombre de jugador no válido");
            return;
        }
        clientConnection.crearPartida(nombreJugador);
        vista.navegarASalaDeEspera();
    }

    public void unirseAPartida() {
        vista.navegarABuscarPartida();
    }

    public void verInstrucciones() {
        vista.navegarAInstrucciones();
    }
    
    public void avanzarACrearPartida() {
        EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
    }
    
    public void avanzarAUnirseAPartida() {
        EstadosJuego.estado = EstadosJuego.BUSCAR_PARTIDA;
    }
    
    public void avanzarAInstrucciones() {
        EstadosJuego.estado = EstadosJuego.INSTRUCCIONES;
    }

}
