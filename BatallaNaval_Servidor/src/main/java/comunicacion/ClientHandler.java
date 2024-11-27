package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import dominio.Jugador;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.MessagePack;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Clase que representa un manejador de clientes para el servidor.
 * Implementa la interfaz Runnable para manejar las solicitudes de un cliente en un hilo separado.
 * Esta clase se encarga de recibir mensajes del cliente y delegar acciones a la instancia de HandlerActions.
 *
 * @author af_da
 */
public class ClientHandler implements Runnable {

    /**
     * Socket del cliente conectado.
     */
    private final Socket clientSocket;
    
    /**
     * Instancia del manejador de acciones que gestiona las solicitudes de los clientes.
     */
    private HandlerActions handler;
    
    /**
     * Identificador del cliente.
     */
    private int id;

    /**
     * Constructor de la clase ClientHandler.
     *
     * @param clientSocket Socket del cliente.
     * @param id Identificador del cliente.
     */
    public ClientHandler(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.handler = HandlerActions.getInstance();
        this.id = id;
    }

    /**
     * Metodo run que se ejecuta cuando el hilo del cliente inicia.
     * Lee los mensajes del cliente y los procesa a través del HandlerActions.
     */
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
