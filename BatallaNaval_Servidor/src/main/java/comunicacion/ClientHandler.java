package comunicacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.MessagePack;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final Socket otherClientSocket;
    private final String clientName;

    public ClientHandler(Socket clientSocket, Socket otherClientSocket, String clientName) {
        this.clientSocket = clientSocket;
        this.otherClientSocket = otherClientSocket;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream(); MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream)) {

            // Continuar leyendo mientras el cliente esté conectado
            while (!clientSocket.isClosed()) {
                if (unpacker.hasNext()) {
                    // Leer la cadena JSON empaquetada con MessagePack
                    String json = unpacker.unpackString();

                    // Usar Jackson para convertir la cadena JSON a un mapa
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> data = objectMapper.readValue(json, Map.class);

//                    // Extraer la acción y los parámetros
//                    String accion = (String) data.get("accion");
//                    Integer x = (Integer) data.get("x");
//                    Integer y = (Integer) data.get("y");
//
//                    // Mostrar la acción recibida y los parámetros
//                    System.out.println("Acción recibida: " + accion + ", x: " + x + ", y: " + y);
                    // Procesar la acción recibida
                    HandlerActions handler = new HandlerActions();
                    System.out.println("Apartir de aqui es la clase encargada de recibir cada peticion");
                    handler.handlerAction(data);  // Llamamos al método que maneja la acción
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
