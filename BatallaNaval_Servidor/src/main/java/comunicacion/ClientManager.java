package comunicacion;

import dominio.Jugador;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Clase ClientManager que gestiona la conexión de los clientes.
 * Esta clase proporciona métodos para agregar, obtener y eliminar clientes, así como para asociar cada cliente con su respectivo jugador.
 * 
 * @author af_da
 */
public class ClientManager {

    /**
     * Mapa para almacenar el socket y su clientId.
     */
    private static Map<Socket, String> clientToIdMap = new ConcurrentHashMap<>();
    
    /**
     * Mapa para almacenar el clientId y su socket asociado.
     */
    private static Map<String, Socket> idToClientMap = new ConcurrentHashMap<>();
    
    /**
     * Mapa para almacenar el clientId y el objeto Jugador asociado.
     */
    private static Map<String, Jugador> clientIdToJugadorMap = new ConcurrentHashMap<>();

    /**
     * Método para agregar un cliente y asociar su clientId con un Jugador.
     * 
     * @param clientSocket Socket del cliente.
     * @param clientId Identificador del cliente.
     * @param jugador Objeto Jugador asociado al cliente.
     */
    public static void addClient(Socket clientSocket, String clientId, Jugador jugador) {
        // Verificar si clientSocket y clientId son no nulos antes de agregar
        if (clientSocket == null || clientId == null || jugador == null) {
            System.out.println("Error: Los valores de clientSocket, clientId o jugador no pueden ser nulos.");
            return;
        }

        clientToIdMap.put(clientSocket, clientId);  // Asociamos el socket con el clientId
        idToClientMap.put(clientId, clientSocket);  // Asociamos el clientId con el socket
        clientIdToJugadorMap.put(clientId, jugador); // Asociamos el clientId con el jugador

        System.out.println("Cliente agregado: " + clientId);
    }

    /**
     * Método para obtener el Jugador dado el clientId.
     * 
     * @param clientId Identificador del cliente.
     * @return Objeto Jugador asociado al clientId.
     */
    public static synchronized Jugador getJugadorByClientId(String clientId) {
        if (clientId == null) {
            System.out.println("Error: El clientId no puede ser nulo.");
            return null;
        }
        return clientIdToJugadorMap.get(clientId);
    }

    /**
     * Método para obtener el clientId dado el socket.
     * 
     * @param clientSocket Socket del cliente.
     * @return Identificador del cliente asociado al socket.
     */
    public static synchronized String getClientId(Socket clientSocket) {
        if (clientSocket == null) {
            System.out.println("Error: El clientSocket no puede ser nulo.");
            return null;
        }
        return clientToIdMap.get(clientSocket);
    }

    /**
     * Método para obtener el socket dado el clientId.
     * 
     * @param clientId Identificador del cliente.
     * @return Socket asociado al clientId.
     */
    public static synchronized Socket getClientSocket(String clientId) {
        if (clientId == null) {
            System.out.println("Error: El clientId no puede ser nulo.");
            return null;
        }
        return idToClientMap.get(clientId);
    }

    /**
     * Método para eliminar un cliente cuando se desconecta.
     * 
     * @param clientSocket Socket del cliente a eliminar.
     */
    public static synchronized void removeClient(Socket clientSocket) {
        if (clientSocket == null) {
            System.out.println("Error: El clientSocket no puede ser nulo al intentar eliminarlo.");
            return;
        }

        String clientId = clientToIdMap.remove(clientSocket);
        if (clientId != null) {
            idToClientMap.remove(clientId);
            clientIdToJugadorMap.remove(clientId);
            System.out.println("Cliente eliminado: " + clientId);
        } else {
            System.out.println("No se encontró el cliente asociado al socket.");
        }
    }

    /**
     * Método para obtener un jugador que no coincida con el clientId dado.
     * 
     * @param excludeClientId Identificador del cliente a excluir.
     * @return Objeto Jugador que no coincide con el clientId excluido, o null si no se encuentra.
     */
    public static synchronized Jugador getOtherPlayer(String excludeClientId) {
        if (excludeClientId == null) {
            System.out.println("Error: El excludeClientId no puede ser nulo.");
            return null;
        }

        for (Map.Entry<String, Jugador> entry : clientIdToJugadorMap.entrySet()) {
            if (!entry.getKey().equals(excludeClientId)) {
                return entry.getValue();
            }
        }

        System.out.println("No se encontró un jugador que no coincida con el clientId: " + excludeClientId);
        return null; // Si no se encuentra ningún jugador
    }

}
