package presentador;

import comunicacion.ClientConnection;
import ivistas.IVistaBuscarPartida;
import ivistas.IVistaSalaEspera;
import java.util.List;
import java.util.Map;
import modelo.ModeloJugador;
import vista.VistaSalaEspera;

/**
 *
 * @author alex_
 */
public class PresentadorBuscarPartida {

    private IVistaBuscarPartida vista;
    private ClientConnection clientConnection;
    private ModeloJugador jugador;

    public PresentadorBuscarPartida(IVistaBuscarPartida vista) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
    }

    public void unirseAPartida() {
        String codigoAcceso = vista.obtenerCodigoAcceso();
        if (codigoAcceso.isEmpty()) {
            vista.mostrarMensaje("Por favor, ingresa el código de la sala.");
            return;
        }
        String nombreJugador = jugador.getNombre();
        clientConnection.unirsePartida(codigoAcceso, nombreJugador);
    }

    public void regresarAlMenu() {
        vista.navegarAMenu();
    }

    public void manejarRespuestaUnirsePartida(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            vista.mostrarMensaje(error);
        } 
    }

}
