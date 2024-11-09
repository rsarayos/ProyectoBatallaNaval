/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package comunicacion;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author af_da
 */
public class Servidor {
    private static Socket clientSocket1;
    private static Socket clientSocket2;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket1 = new ServerSocket(5000); ServerSocket serverSocket2 = new ServerSocket(5001)) {

            System.out.println("Servidor esperando conexiones en los puertos 5000 y 5001...");

            // Aceptar ambas conexiones de clientes
            System.out.println("Esperando conexión del cliente en el puerto 5000...");
            clientSocket1 = serverSocket1.accept();
            System.out.println("Cliente 1 conectado desde: " + clientSocket1.getInetAddress());

            System.out.println("Esperando conexión del cliente en el puerto 5001...");
            clientSocket2 = serverSocket2.accept();
            System.out.println("Cliente 2 conectado desde: " + clientSocket2.getInetAddress());

            // Informar a ambos clientes que están conectados
            MessageUtil.enviarMensaje(clientSocket1, "Ambos clientes están conectados. Puedes continuar.");
            MessageUtil.enviarMensaje(clientSocket2, "Ambos clientes están conectados. Puedes continuar.");

            // Iniciar hilos para manejar ambos clientes
            new Thread(new ClientHandler(clientSocket1, clientSocket2, "Cliente 1")).start();
            new Thread(new ClientHandler(clientSocket2, clientSocket1, "Cliente 2")).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
