package comunicacion;

import Convertidores.PruebaDeserializer;
import dto.PruebaDTO;
import enums.AccionesJugador;
import static enums.AccionesJugador.ATACAR;
import static enums.AccionesJugador.ORDENAR;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import negocio.PartidaBO;
import org.msgpack.core.MessageUnpacker;

public class HandlerActions {

    private PartidaBO partidaBO;
    private static HandlerActions instance = null;

    private HandlerActions() {
        this.partidaBO = new PartidaBO();
    }

    // Método estático para obtener la única instancia de HandlerActions
    public static synchronized HandlerActions getInstance() {
        if (instance == null) {
            instance = new HandlerActions();
        }
        return instance;
    }

    public void handlerAction(Map<String, Object> request) throws IOException {
        String accion = (String) request.get("accion");
        String clientId = (String) request.get("clientId"); // Puedes pasarlo desde la request

        Socket clientSocket = ClientManager.getClientSocket(clientId);
        clientId = ClientManager.getClientId(clientSocket); // Y obtener el clientId del socket

        if (AccionesJugador.CREAR_PARTIDA.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.crearPartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);

        } else if (AccionesJugador.UNIRSE_PARTIDA.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.unirsePartida(request, clientId);
            MessageUtil.enviarMensaje(clientSocket, response);

        } else if (AccionesJugador.JUGADOR_LISTO.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.jugadorListo(request, clientId);
            // No es necesario responder al jugador, ya que notificaremos a todos
        } else if (AccionesJugador.ORDENAR.toString().equalsIgnoreCase(accion)) {
            partidaBO.colocarUnidadTablero(request, clientId);
            // No es necesario responder al jugador, ya que notificaremos a todos
        } else if (AccionesJugador.ATACAR.toString().equalsIgnoreCase(accion)) {
            // Aqui manejar el ataque
            System.out.println("Se recibio ataque");
        } else {
            // Otras acciones como ATACAR o ORDENAR
            if (ATACAR.toString().equalsIgnoreCase(accion)) {
                // Implementar lógica para "atacar"
            } else if (ORDENAR.toString().equalsIgnoreCase(accion)) {
                // Implementar lógica para "ordenar"
            }
        }
    }
}
