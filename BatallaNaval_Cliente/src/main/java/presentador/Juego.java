package presentador;

import comunicacion.ClientConnection;
import estados.EstadoBienvenida;
import estados.IEstadoJuego;
import java.awt.Graphics;
import java.util.Map;
import javax.swing.SwingUtilities;
import vista.PanelJuego;
import vista.VentanaJuego;

/**
 * Clase principal que representa el juego. Implementa la interfaz Runnable para ejecutarse en un hilo separado.
 *
 * @author Raul Alejandro Sauceda Rayos
 */
public class Juego implements Runnable {

    /**
     * Ventana del juego.
     */
    private VentanaJuego venta;
     
    /**
     * Panel del juego donde se realiza el renderizado.
     */
    protected PanelJuego panel;
    
    /**
     * Hilo principal del juego.
     */
    private Thread hiloJuego;
    
    /**
     * Estado actual del juego.
     */
    private IEstadoJuego estadoActual;
    
    /**
     * Cuadros por segundo deseados.
     */
    protected static final int FPS_SET = 60;
    
    /**
     * Actualizaciones por segundo deseadas.
     */
    protected static final int UPS_SET = 150;
    
    /**
     * Ancho de la ventana del juego.
     */
    public final static int GAME_ANCHO = 900;
    
    /**
     * Alto de la ventana del juego.
     */
    public final static int GAME_ALTO = 720;

    /**
     * Constructor de la clase Juego.
     */
    public Juego() {
        // Crea una instancia de PanelJuego y VentanaJuego
        panel = new PanelJuego(this);
        venta = new VentanaJuego(panel);
        panel.setFocusable(true);
        panel.requestFocus();

        // Inicia la conexión con el servidor
        iniciarConexion();

        // Inicia el bucle principal del juego
        this.inicioJuegoLoop();
        
        // Establece el estado inicial
        estadoActual = new EstadoBienvenida(this);

    }

    /**
     * Inicia el bucle principal del juego en un nuevo hilo.
     */
    private void inicioJuegoLoop() {
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    /**
     * Método para renderizar (dibujar) el juego en pantalla.
     *
     * @param g Objeto Graphics para dibujar.
     */
    public void renderizar(Graphics g) {

        if (estadoActual != null) {
            estadoActual.renderizar(g);
        }

    }

    /**
     * Bucle principal del juego. Controla la lógica de actualización y renderizado del juego a una velocidad específica,
     * y muestra estadísticas de FPS (cuadros por segundo) y UPS (actualizaciones por segundo).
     */
    @Override
    public void run() {
        final int FPS = 60;
        final int UPS = 60;

        final double timePerUpdate = 1000000000.0 / UPS;
        final double timePerFrame = 1000000000.0 / FPS;

        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();
            double elapsedTime = currentTime - previousTime;
            previousTime = currentTime;

            deltaU += elapsedTime / timePerUpdate;
            deltaF += elapsedTime / timePerFrame;

            // Actualización
            if (deltaU >= 1) {
//                update();
                deltaU--;
            }

            // Renderizado
            if (deltaF >= 1) {
                panel.repaint();
                deltaF--;
            }

            // Control del hilo
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Inicia la conexión con el servidor y configura el listener de mensajes.
     */
    private void iniciarConexion() {
        ClientConnection clientConnection = ClientConnection.getInstance();
        boolean conectado = clientConnection.connect("localhost", 5000);
        if (conectado) {
            System.out.println("Conectado al servidor.");
            // Configurar el listener de mensajes
            clientConnection.setMessageListener(this::onMensajeRecibido);
        } else {
            System.out.println("No se pudo conectar al servidor.");
            // Manejar el error de conexión, por ejemplo, mostrar un mensaje al usuario
        }
    }
    
    /**
     * Maneja los mensajes recibidos del servidor y los pasa al estado actual del juego.
     *
     * @param mensaje el mensaje recibido del servidor
     */
    private void onMensajeRecibido(Map<String, Object> mensaje) {
        SwingUtilities.invokeLater(() -> {
            if (estadoActual!= null) {
                estadoActual.handleMessage(mensaje);
            }
        });
    }
    
    /**
     * Cambia el estado actual del juego al estado nuevo especificado.
     *
     * @param estadoNuevo el nuevo estado del juego
     */
    public void cambiarEstado(IEstadoJuego estadoNuevo) {
        if (estadoActual != null) {
            estadoActual.salir();
        }
        estadoActual = estadoNuevo;
    }

    /**
     * Obtiene el panel del juego donde se realiza el renderizado.
     *
     * @return el panel del juego
     */
    public PanelJuego getPanel() {
        return panel;
    }

}
