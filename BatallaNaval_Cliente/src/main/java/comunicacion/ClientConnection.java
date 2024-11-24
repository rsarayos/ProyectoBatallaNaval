package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/**
 * Representa una conexión de cliente singleton a un servidor. 
 * Esta clase se encarga de establecer la conexión, enviar y recibir mensajes, 
 * y notificar a los oyentes de los mensajes entrantes.
 * 
 * @author alex_
 */
public class ClientConnection {

    /**
     * La instancia singleton de ClientConnection.
     */
    private static ClientConnection instance = null;

    /**
     * El socket utilizado para la conexión.
     */
    private Socket socket;
    
    /**
     * El flujo de salida para enviar datos al servidor.
     */
    private OutputStream outputStream;
    
    /**
     * El flujo de entrada para recibir datos del servidor.
     */
    private InputStream inputStream;
    
    /**
     * El hilo utilizado para escuchar mensajes entrantes.
     */
    private Thread listeningThread;
    
    /**
     * Indicador booleano que indica si la conexión está activa.
     */
    private boolean running = false;

    /**
     * ObjectMapper para la serialización y deserialización de JSON.
     */
    private ObjectMapper objectMapper;

    /**
     * Oyente para los mensajes entrantes.
     */
    private MessageListener messageListener;

    /**
     * Constructor privado para el patrón singleton.
     * Inicializa el ObjectMapper para el procesamiento de JSON.
     */
    private ClientConnection() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Devuelve la instancia singleton de ClientConnection.
     * Si no existe ninguna instancia, crea una.
     * 
     * @return la instancia singleton de ClientConnection
     */
    public static synchronized ClientConnection getInstance() {
        if (instance == null) {
            instance = new ClientConnection();
        }
        return instance;
    }

    /**
     * Establece una conexión con el servidor con el host y puerto especificados.
     * 
     * @param host el nombre del host del servidor
     * @param port el número de puerto del servidor
     * @return true si la conexión se estableció correctamente, false en caso contrario
     */
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            startListening();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexión con el servidor y detiene el hilo de escucha.
     */
    public void disconnect() {
        running = false;
        if (listeningThread != null) {
            listeningThread.interrupt();
        }
        try {
            if (socket != null) {
                socket.close();
            }
            socket = null;
            outputStream = null;
            inputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia un hilo para escuchar los mensajes entrantes del servidor.
     */
    private void startListening() {
        running = true;
        listeningThread = new Thread(() -> {
            try {
                MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream);
                while (running) {
                    if (unpacker.hasNext()) {
                        String jsonMessage = unpacker.unpackString();
                        // Deserialize the JSON message to a Map
                        Map<String, Object> message = objectMapper.readValue(jsonMessage, Map.class);
                        // Notify the listener on the Event Dispatch Thread
                        if (messageListener != null) {
                            SwingUtilities.invokeLater(() -> {
                                messageListener.onMessageReceived(message);
                            });
                        }
                    } else {
                        // Sleep briefly to avoid busy waiting
                        Thread.sleep(100);
                    }
                }
                unpacker.close();
            } catch (IOException e) {
                if (running) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                // Thread interrupted, exit gracefully
            }
        });
        listeningThread.start();
    }

    /**
     * Envía un mensaje al servidor.
     * 
     * @param data los datos del mensaje a enviar, representados como un Map
     */
    public synchronized void sendMessage(Map<String, Object> data) {
        if (socket == null || outputStream == null) {
            System.out.println("Not connected to server.");
            return;
        }
        try {
            // Serialize the data to JSON
            String json = objectMapper.writeValueAsString(data);
            // Pack the JSON string with MessagePack
            MessagePacker packer = MessagePack.newDefaultPacker(outputStream);
            packer.packString(json);
            packer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envía una solicitud para crear una nueva partida con el nombre del jugador especificado.
     * 
     * @param nombreJugador el nombre del jugador que crea la partida
     */
    public void crearPartida(String nombreJugador) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "CREAR_PARTIDA");
        data.put("nombre", nombreJugador);
        sendMessage(data);
    }

    /**
     * Envía una solicitud para unirse a una partida existente con el código de acceso y el nombre del jugador especificados.
     * 
     * @param codigoAcceso el código de acceso de la partida a la que unirse
     * @param nombreJugador el nombre del jugador que se une a la partida
     */
    public void unirsePartida(String codigoAcceso, String nombreJugador) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "UNIRSE_PARTIDA");
        data.put("codigo_acceso", codigoAcceso);
        data.put("nombre", nombreJugador);
        sendMessage(data);
    }

    /**
     * Envía un comando de ataque con las coordenadas especificadas.
     * 
     * @param x la coordenada x del ataque
     * @param y la coordenada y del ataque
     */
    public void atacar(int x, int y) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "ATACAR");
        data.put("x", x);
        data.put("y", y);
        sendMessage(data);
    }

    /**
     * Establece el oyente de mensajes para los mensajes entrantes del servidor.
     * 
     * @param listener el oyente que manejará los mensajes entrantes
     */
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    /**
     * Envía una notificación de que el jugador está listo.
     */
    public void jugadorListo() {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "JUGADOR_LISTO");
        sendMessage(data);
    }

    /**
     * Envía la información de ubicación de las unidades al servidor.
     * 
     * @param unidadesData una lista que contiene los datos de cada unidad a ubicar
     */
    public void enviarUnidades(List<Map<String, Object>> unidadesData) {
        Map<String, Object> data = new HashMap<>();
        data.put("accion", "ORDENAR");
        data.put("unidades", unidadesData);
        sendMessage(data);
    }

    public void enviarRendicion(Map<String, Object> datos) {
        sendMessage(datos);
    }

    /**
     * Interfaz que representa un oyente para los mensajes entrantes del servidor.
     */
    public interface MessageListener {

        /**
         * Se llama cuando se recibe un mensaje del servidor.
         * 
         * @param message el mensaje recibido, representado como un Map
         */
        void onMessageReceived(Map<String, Object> message);
    }
}
