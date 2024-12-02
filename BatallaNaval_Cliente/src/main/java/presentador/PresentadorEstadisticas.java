package presentador;

import comunicacion.ClientConnection;
import estados.EstadoMenu;
import ivistas.IVistaEstadisticas;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class PresentadorEstadisticas {
    
    private IVistaEstadisticas vista;
    private Juego juego;

    public PresentadorEstadisticas(IVistaEstadisticas vista, Juego juego) {
        this.vista = vista;
        this.juego = juego;
    }

    public void volverAJugar() {
        // Enviar solicitud al servidor para volver a jugar
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "VOLVER_A_JUGAR");

        ClientConnection.getInstance().sendMessage(mensaje);
    }

    public void salirAlMenu() {
        // Enviar notificación al servidor de que el jugador quiere salir
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("accion", "SALIR");

        ClientConnection.getInstance().sendMessage(mensaje);

        // Transicionar al estado del menú
        juego.cambiarEstado(new EstadoMenu(juego));
    }

}
