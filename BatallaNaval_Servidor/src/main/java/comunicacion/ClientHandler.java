package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Jugador;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.MessagePack;
import java.util.concurrent.atomic.AtomicInteger;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private HandlerActions handler;
    private int id;

    public ClientHandler(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.handler = HandlerActions.getInstance();
        this.id = id;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream(); MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream)) {

            // Obtener el clientId asociado al socket (se obtiene desde ClientManager)
//            String clientId = ClientManager.getClientId(clientSocket);
            String clientId = String.valueOf(this.id);
            ClientManager.addClient(clientSocket, clientId, new Jugador());
            // Continuar leyendo mientras el cliente esté conectado
            while (!clientSocket.isClosed()) {
                if (unpacker.hasNext()) {
                    String json = unpacker.unpackString();
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> data = objectMapper.readValue(json, Map.class);
                    data.put("clientId", clientId);  // Agregar clientId a la solicitud
                    this.handler.handlerAction(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                // Eliminar cliente de ClientManager cuando se desconecte
                ClientManager.removeClient(clientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
