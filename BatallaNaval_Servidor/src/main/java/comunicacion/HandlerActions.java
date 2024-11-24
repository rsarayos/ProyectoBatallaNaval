package comunicacion;

import Convertidores.PruebaDeserializer;
import dominio.Jugador;
import dto.PruebaDTO;
import enums.AccionesJugador;
import static enums.AccionesJugador.ATACAR;
import static enums.AccionesJugador.ORDENAR;
import enums.ControlPartida;
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
            System.out.println("Se recibio ataque");
            Jugador otherClient = ClientManager.getOtherPlayer(clientId);
            Map<String, Object> response = partidaBO.ubicarAtaque(request, clientId);
            String resultado = (String) response.get("resultado");
            //Aqui saco el resultado para ver si es de partida finalizada sino se envia el mensaje normal
            if (resultado != null && resultado.equalsIgnoreCase(ControlPartida.PARTIDA_FINALIZADA.name())) {
                MessageUtil.enviarMensaje(clientSocket, response);
                MessageUtil.enviarMensaje(ClientManager.getClientSocket(otherClient.getId()), response);
            }
            Map<String, Object> clienteAtacanteResponse = (Map<String, Object>) response.get(clientId);
            Map<String, Object> clienteAtacadoResponse = (Map<String, Object>) response.get(otherClient.getId());

            MessageUtil.enviarMensaje(clientSocket, clienteAtacanteResponse);
            MessageUtil.enviarMensaje(ClientManager.getClientSocket(otherClient.getId()), clienteAtacadoResponse);
        } else if (AccionesJugador.RENDIRSE.toString().equalsIgnoreCase(accion)) {
            Map<String, Object> response = partidaBO.rendirse(request, clientId);
            // Enviar respuestas a los jugadores involucrados
            Jugador jugador = ClientManager.getJugadorByClientId(clientId);
            Jugador otroJugador = ClientManager.getOtherPlayer(clientId);

            if (jugador != null && otroJugador != null) {
                Socket jugadorSocket = ClientManager.getClientSocket(jugador.getId());
                Socket otroJugadorSocket = ClientManager.getClientSocket(otroJugador.getId());

                // Enviar la respuesta a ambos jugadores
                MessageUtil.enviarMensaje(jugadorSocket, response);
                MessageUtil.enviarMensaje(otroJugadorSocket, response);
            }
        } else {
        }
    }
}
