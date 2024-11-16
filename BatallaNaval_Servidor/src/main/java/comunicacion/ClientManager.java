package comunicacion;

import dominio.Jugador;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

    // Mapa para almacenar el socket y su clientId
    private static Map<Socket, String> clientToIdMap = new ConcurrentHashMap<>();
    private static Map<String, Socket> idToClientMap = new ConcurrentHashMap<>();
    private static Map<String, Jugador> clientIdToJugadorMap = new ConcurrentHashMap<>();

    // Método para agregar un cliente y asociar su clientId con un Jugador
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

    // Método para obtener el Jugador dado el clientId
    public static synchronized Jugador getJugadorByClientId(String clientId) {
        if (clientId == null) {
            System.out.println("Error: El clientId no puede ser nulo.");
            return null;
        }
        return clientIdToJugadorMap.get(clientId);
    }

    // Método para obtener el clientId dado el socket
    public static synchronized String getClientId(Socket clientSocket) {
        if (clientSocket == null) {
            System.out.println("Error: El clientSocket no puede ser nulo.");
            return null;
        }
        return clientToIdMap.get(clientSocket);
    }

    // Método para obtener el socket dado el clientId
    public static synchronized Socket getClientSocket(String clientId) {
        if (clientId == null) {
            System.out.println("Error: El clientId no puede ser nulo.");
            return null;
        }
        return idToClientMap.get(clientId);
    }

    // Método para eliminar un cliente cuando se desconecta
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
