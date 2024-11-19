package presentador;

import comunicacion.ClientConnection;
import estados.EstadoMenu;
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
    private Juego juego;

    public PresentadorBuscarPartida(IVistaBuscarPartida vista, Juego juego) {
        this.vista = vista;
        this.clientConnection = ClientConnection.getInstance();
        this.jugador = ModeloJugador.getInstance();
        this.juego = juego;
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
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    public void manejarRespuestaUnirsePartida(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            vista.mostrarMensaje(error);
        } 
    }

}
