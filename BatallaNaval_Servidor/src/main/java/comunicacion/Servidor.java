package comunicacion;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase Servidor que acepta múltiples conexiones de clientes y crea un nuevo hilo para cada conexión entrante.
 * Escucha conexiones en el puerto 5000 y asigna un identificador único a cada cliente conectado.
 * Utiliza un AtomicInteger para gestionar los IDs de los clientes de forma segura entre hilos.
 *
 * @author af_da
 */
public class Servidor {

    /**
     * Contador atómico para asignar un identificador único a cada cliente conectado.
     */
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    /**
     * Método principal que inicia el servidor y gestiona las conexiones de los clientes.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
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
