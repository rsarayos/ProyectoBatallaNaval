package presentador;

import comunicacion.ClientConnection;
import ivistas.IVistaSalaEspera;
import java.util.List;
import modelo.ModeloJugador;

/**
 *
 * @author alex_
 */
public class PresentadorSalaEspera {

    private IVistaSalaEspera vista;
    private ClientConnection clientConnection;
    private ModeloJugador jugador;

    public PresentadorSalaEspera(IVistaSalaEspera vista) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
    }

    public void iniciar() {
        // Obtener el código de acceso y el ID del jugador si es necesario        
//        vista.mostrarCodigoAcceso(codigoAcceso);
        // Inicializar lista de jugadores si es necesario
    }

    public void jugadorListo() {
        if (!vista.isEstoyListo()) {
            vista.setEstoyListo(true);
            vista.bloquearBotonContinuar();
            clientConnection.jugadorListo();
        }
    }

    public void salir() {
        vista.navegarAMenu();
    }

    public void agregarOActualizarJugador(String nombreJugador, boolean listo) {
        vista.agregarOActualizarJugador(nombreJugador, listo);
    }

    public void limpiarListaJugadores() {
        vista.limpiarListaJugadores();
    }

    public void iniciarOrganizar() {
        vista.navegarAOrganizar();
    }
    
    public void actualizarListaJugadores(List<String> nombresJugadores) {
        vista.limpiarListaJugadores();
        for (String nombreJugador : nombresJugadores) {
            vista.agregarOActualizarJugador(nombreJugador, false); // Suponiendo que no sabemos si están listos
        }
    }
    
    public void manejarActualizarEstadoListo(String nombreJugador, boolean listo) {
        vista.agregarOActualizarJugador(nombreJugador, listo);
    }
    
    public void manejarTodosListos() {
        vista.navegarAOrganizar();
    }

}
