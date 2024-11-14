package negocio;

import Convertidores.toJSON;
import dominio.Jugador;
import dominio.Partida;
import comunicacion.ClientManager;
import comunicacion.MessageUtil;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return toJSON.dataToJSON("accion", "CREAR_PARTIDA", "id", host.getId(), "codigo_acceso", codigoAcceso);
    }

    // Método para que un jugador se una a una partida
    public Map<String, Object> unirsePartida(Map<String, Object> data, String clientId) {
        String codigo_acceso = (String) data.get("codigo_acceso");
        System.out.println(codigo_acceso);
        if (!partida.getCodigoAcceso().equalsIgnoreCase(codigo_acceso)) {
            return toJSON.dataToJSON("accion", "UNIRSE_PARTIDA", "error", "El código de acceso no coincide");
        }
        Jugador jugador = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(jugador); // Agregar el jugador a la partida
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, jugador);
        partida.getJugadores().stream().forEach(p -> System.out.println("Jugador en la partida " + p.getId()));
        // Obtener la lista de nombres de jugadores
        List<String> nombresJugadores = partida.getJugadores().stream()
                .map(Jugador::getNombre)
                .collect(Collectors.toList());

        // Notificar a todos los jugadores que un nuevo jugador se ha unido
        notificarNuevoJugador(jugador);

        return toJSON.dataToJSON(
                "accion", "UNIRSE_PARTIDA",
                "id", jugador.getId(),
                "codigo_acceso", codigo_acceso,
                "nombres_jugadores", nombresJugadores
        );
    }

    private void notificarNuevoJugador(Jugador nuevoJugador) {
        for (Jugador jugadorExistente : partida.getJugadores()) {
            if (!jugadorExistente.getId().equals(nuevoJugador.getId())) {
                String clientIdExistente = jugadorExistente.getId();
                Socket socketJugador = ClientManager.getClientSocket(clientIdExistente);

                Map<String, Object> mensajeNotificacion = toJSON.dataToJSON(
                        "accion", "NUEVO_JUGADOR",
                        "nombre_jugador", nuevoJugador.getNombre()
                );

                MessageUtil.enviarMensaje(socketJugador, mensajeNotificacion);
            }
        }
    }

    public Jugador crearJugador(String id, String nombre) {
        return new Jugador(id, nombre); 
    }

    public Map<String, Object> jugadorListo(Map<String, Object> request, String clientId) {
        Jugador jugador = ClientManager.getJugadorByClientId(clientId);
        if (jugador != null) {
            jugador.setListo(true);

            // Notificar a todos los jugadores que este jugador está listo
            notificarEstadoListo(jugador);

            // Si todos los jugadores están listos, notificar para avanzar
            if (partida.todosLosJugadoresListos()) {
                notificarTodosListos();
            }
        }
        return null;
    }

    private void notificarEstadoListo(Jugador jugadorListo) {
        for (Jugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "ACTUALIZAR_ESTADO_LISTO",
                    "id_jugador", jugadorListo.getId(),
                    "nombre_jugador", jugadorListo.getNombre(),
                    "listo", jugadorListo.isListo()
            );
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    private void notificarTodosListos() {
        for (Jugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "TODOS_LISTOS"
            );
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }
}
