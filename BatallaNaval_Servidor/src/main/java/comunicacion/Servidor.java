/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package comunicacion;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author af_da
 */
public class Servidor {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor esperando conexiones en el puerto 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");
                int id = atomicInteger.getAndIncrement();
                System.out.println("id del cliente " + id);
                // Crear y ejecutar un hilo para manejar al cliente
                new Thread(new ClientHandler(clientSocket, id)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
