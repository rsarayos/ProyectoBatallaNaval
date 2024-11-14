package presentador;

import com.fasterxml.jackson.databind.ObjectMapper;
import enums.AccionesJugador;
import java.io.BufferedReader;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        // TODO code application logic here

        Juego juego = new Juego();
        juego.run();

//        Scanner scanner = new Scanner(System.in);
//
//        try (Socket socket = new Socket("localhost", 5000); OutputStream outputStream = socket.getOutputStream(); InputStream inputStream = socket.getInputStream()) {
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            while (true) {
//                System.out.println("Ingrese el tipo de accion");
//                String command = scanner.nextLine();
//
//                if ("salir".equalsIgnoreCase(command)) {
//                    System.out.println("Saliendo del cliente...");
//                    break;  // Salir del bucle si el usuario escribe "salir"
//                }
//
//                if ("unirse".equalsIgnoreCase(command)) {
//                    // Crear un mapa de datos para enviar
//                    System.out.println("Ingrese el codigo de acceso");
//                    String codigo_acceso = scanner.nextLine();
//                    Map<String, Object> data = new HashMap<>();
//
//                    data.put("accion", AccionesJugador.UNIRSE_PARTIDA.name());
//                    data.put("codigo_acceso", codigo_acceso);
//
//                    // Convertir el mapa a una cadena JSON usando Jackson
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    String json = objectMapper.writeValueAsString(data);
//
//                    // Empaquetar la cadena JSON con MessagePack
//                    MessagePacker packer = MessagePack.newDefaultPacker(outputStream);
//                    packer.packString(json);  // Empacar como una cadena JSON
//                    packer.flush();  // Enviar los datos
//
//                    // Desempaquetar la respuesta del servidor
//                    MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream);
//                    String serverResponseJson = unpacker.unpackString();  // Leer la respuesta JSON como cadena
//
//                    // Convertir la respuesta JSON a un Map
//                    Map<String, Object> serverResponse = objectMapper.readValue(serverResponseJson, Map.class);
//                    System.out.println("Respuesta del servidor: " + serverResponse);
//                }
//
//                if ("crear".equalsIgnoreCase(command)) {
//                    // Crear un mapa de datos para enviar
//                    Map<String, Object> data = new HashMap<>();
//                    data.put("accion", AccionesJugador.CREAR_PARTIDA.name());
//                    data.put("tipo_nave", "portaaviones");
//
//                    // Convertir el mapa a una cadena JSON usando Jackson
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    String json = objectMapper.writeValueAsString(data);
//
//                    // Empaquetar la cadena JSON con MessagePack
//                    MessagePacker packer = MessagePack.newDefaultPacker(outputStream);
//                    packer.packString(json);  // Empacar como una cadena JSON
//                    packer.flush();  // Enviar los datos
//
//                    // Desempaquetar la respuesta del servidor
//                    MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream);
//                    String serverResponseJson = unpacker.unpackString();  // Leer la respuesta JSON como cadena
//
//                    // Convertir la respuesta JSON a un Map
//                    Map<String, Object> serverResponse = objectMapper.readValue(serverResponseJson, Map.class);
//                    System.out.println("Respuesta del servidor: " + serverResponse);
//                }
//                
//                    if ("crear".equalsIgnoreCase(command)) {
//                    // Crear un mapa de datos para enviar
//                    Map<String, Object> data = new HashMap<>();
//                    data.put("accion", AccionesJugador.ORDENAR.name());
//                    data.put("tipo_nave", "portaaviones");
//                    data.put("orientacion", "horizontal");
//                    data.put("x1", 100);
//                    data.put("y1", 200);
//                    data.put("x2", 300);
//                    data.put("y2", 400);
//                    data.put("x3", 500);
//                    data.put("y3", 600);
//                    // Convertir el mapa a una cadena JSON usando Jackson
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    String json = objectMapper.writeValueAsString(data);
//
//                    // Empaquetar la cadena JSON con MessagePack
//                    MessagePacker packer = MessagePack.newDefaultPacker(outputStream);
//                    packer.packString(json);  // Empacar como una cadena JSON
//                    packer.flush();  // Enviar los datos
//
//                    // Desempaquetar la respuesta del servidor
//                    MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(inputStream);
//                    String serverResponseJson = unpacker.unpackString();  // Leer la respuesta JSON como cadena
//
//                    // Convertir la respuesta JSON a un Map
//                    Map<String, Object> serverResponse = objectMapper.readValue(serverResponseJson, Map.class);
//                    System.out.println("Respuesta del servidor: " + serverResponse);
//                }
//                
//            }
//        } catch (Exception e) {
//            System.out.println("Error al enviar o recibir datos: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            scanner.close();
//        }
    }
}
