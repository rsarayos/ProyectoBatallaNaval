/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.MessagePack;
import java.io.InputStream;
import java.net.Socket;

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
        try (InputStream inputStream = clientSocket.getInputStream();
             MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream)) {

            // Continuar leyendo mensajes mientras el cliente esté conectado
            while (!clientSocket.isClosed()) {
                if (unpacker.hasNext()) {
                    String mensaje = unpacker.unpackString();
                    System.out.println("Mensaje recibido de " + clientName + ": " + mensaje);

                    // Reenviar el mensaje al otro cliente
                    MessageUtil.enviarMensaje(otherClientSocket, clientName + ": " + mensaje);
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
