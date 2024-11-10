/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import java.io.OutputStream;
import java.net.Socket;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

/**
 *
 * @author af_da
 */
public class MessageUtil {

    // Método para enviar mensajes a un cliente específico
    public static void enviarMensaje(Socket clientSocket, String mensaje) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packString(mensaje);
            packer.close();

            outputStream.write(packer.toByteArray());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
