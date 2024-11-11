package presentador;

import com.fasterxml.jackson.databind.ObjectMapper;
import enums.AccionesJugador;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

/**
 * Clase para ejecutar
 *
 * @author alex_
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Juego juego = new Juego();
        juego.run();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el puerto al que desea conectarse (5000/5001): ");
        int port = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        try {
            // Iniciar el bucle infinito
            try (Socket socket = new Socket("localhost", port); OutputStream outputStream = socket.getOutputStream()) {

                while (true) {
                    System.out.print("Escriba 'enviar' para enviar el mensaje, o 'salir' para salir: ");
                    String command = scanner.nextLine();

                    if ("salir".equalsIgnoreCase(command)) {
                        System.out.println("Saliendo del cliente...");
                        break;  // Salir del bucle si el usuario escribe "salir"
                    }

                    if ("enviar".equalsIgnoreCase(command)) {

                        // Crear un mapa de datos como si fuera un JSON
                        Map<String, Object> data = new HashMap<>();
                        data.put("accion", AccionesJugador.ORDENAR.name());
                        data.put("tipo_nave","portaaviones");
                        data.put("x1", 200);
                        data.put("y1", 150);
                        data.put("x2", 150);
                        data.put("y2", 150);
                        data.put("x3", 150);
                        data.put("y3", 150);
                        data.put("x4", 150);
                        data.put("y4", 150);
                        // Convertir el mapa a una cadena JSON usando Jackson
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = objectMapper.writeValueAsString(data);

                        // Empaquetar la cadena JSON con MessagePack
                        MessagePacker packer = MessagePack.newDefaultPacker(outputStream);
                        packer.packString(json); // Empacar como una cadena JSON
                        packer.flush();

                        System.out.println("Mensaje enviado como MessagePack con datos tipo JSON.");

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

}
