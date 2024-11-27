package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Utilidad para enviar mensajes a través de un Socket.
 * Esta clase proporciona un método para convertir un mapa de datos en formato JSON
 * y enviarlo al cliente utilizando MessagePack para la serialización.
 * @author af_da
 */
public class MessageUtil {

    /**
     * Método para enviar un mapa de datos como mensaje a un cliente específico a través de un socket.
     * Convierte el mapa a una cadena JSON y lo envía utilizando MessagePack.
     *
     * @param clientSocket El socket del cliente al cual se enviará el mensaje.
     * @param mensajeMap   El mapa que contiene los datos del mensaje a enviar.
     */
    public static void enviarMensaje(Socket clientSocket, Map<String, Object> mensajeMap) {
        try {
            // Convertir el Map a una cadena JSON usando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMensaje = objectMapper.writeValueAsString(mensajeMap);

            // Crear un MessageBufferPacker para empacar el JSON como una cadena
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packString(jsonMensaje);  // Empacar el JSON como una cadena

            // Cerrar el packer para finalizar la escritura
            packer.close();

            // Enviar los datos empaquetados al cliente a través del socket
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(packer.toByteArray());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
