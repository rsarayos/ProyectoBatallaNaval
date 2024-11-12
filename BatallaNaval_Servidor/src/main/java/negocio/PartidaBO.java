package negocio;

import Convertidores.toJSON;
import dominio.Jugador;
import dominio.Partida;
import comunicacion.ClientManager;
import java.util.Map;
import java.util.UUID;

public class PartidaBO {

    private Partida partida;

    public PartidaBO() {
        this.partida = Partida.getInstance();
    }

    // Método para crear una partida
    public Map<String, Object> crearPartida(Map<String, Object> data, String clientId) {
        // Verifica si el cliente ya está registrado
        if (ClientManager.getClientSocket(clientId) == null) {
            throw new IllegalStateException("El cliente no está conectado.");
        }

        String codigoAcceso = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        partida.setCodigoAcceso(codigoAcceso); // Asignar código de acceso a la partida
        System.out.println("El codigo de acceso es: " + codigoAcceso);
        Jugador host = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(host); // Agregar el jugador a la partida

        // Asocia al cliente y su socket
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, host);
        return toJSON.dataToJSON("id", host.getId());
    }

    // Método para que un jugador se una a una partida
    public Map<String, Object> unirsePartida(Map<String, Object> data, String clientId) {
        String codigo_acceso = (String) data.get("codigo_acceso");
        System.out.println(codigo_acceso);
        if (!partida.getCodigoAcceso().equalsIgnoreCase(codigo_acceso)) {
            return toJSON.dataToJSON("error", "El codigo de acceso no coincide");
        }
        Jugador jugador = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(jugador); // Agregar el jugador a la partida
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, jugador);
        partida.getJugadores().stream().forEach(p -> System.out.println("Jugador en la partida " + p.getId()));
        return toJSON.dataToJSON("id", jugador.getId());
    }

    // Método para crear un nuevo jugador a partir de los datos proporcionados
    public Jugador crearJugador(String id, String nombre) {
        return new Jugador(id, nombre); // Crear jugador sin necesidad de usar un contador externo
    }
}
