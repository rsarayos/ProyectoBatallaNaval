package presentador;

import comunicacion.ClientConnection;
import enums.ControlPartida;
import estados.EstadoBienvenida;
import estados.IEstadoJuego;
import ivistas.IVistaBuscarPartida;
import ivistas.IVistaJuego;
import ivistas.IVistaOrganizar;
import ivistas.IVistaSalaEspera;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import modelo.ModeloJugador;
import vista.EstadosJuego;
import vista.ModoTablero;
import vista.PanelJuego;
import vista.VentanaJuego;
import vista.VistaBienvenida;
import vista.VistaBuscarPartida;
import vista.VistaInstrucciones;
import vista.VistaJuego;
import vista.VistaMenu;
import vista.VistaOrganizar;
import vista.VistaSalaEspera;
import vista.VistaTablero;

/**
 * Clase principal que representa el juego. Implementa la interfaz Runnable para
 * ejecutarse en un hilo separado.
 *
 * @author Raul Alejandro Sauceda Rayos
 */
public class Juego implements Runnable {

    private VentanaJuego venta;
    protected PanelJuego panel;
    private Thread hiloJuego;
    
    private IEstadoJuego estadoActual;
    // FPS deseados 
    protected static final int FPS_SET = 60;
    // UPS deseados 
    protected static final int UPS_SET = 150;
    public final static int GAME_ANCHO = 900;
    public final static int GAME_ALTO = 720;

    /**
     * Constructor de la clase Juego.
     *
     * @param usuario Usuario actual que juega.
     * @param usuarios Lista de usuarios en el juego.
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
     * Bucle principal del juego. Controla la lógica de actualización y
     * renderizado del juego a una velocidad específica, y muestra estadísticas
     * de FPS (cuadros por segundo) y UPS (actualizaciones por segundo).
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
    
    private void onMensajeRecibido(Map<String, Object> mensaje) {
        SwingUtilities.invokeLater(() -> {
            if (estadoActual!= null) {
                estadoActual.handleMessage(mensaje);
            }
        });
    }
    
    public void cambiarEstado(IEstadoJuego estadoNuevo) {
        if (estadoActual != null) {
            estadoActual.salir();
        }
        estadoActual = estadoNuevo;
    }

    public PanelJuego getPanel() {
        return panel;
    }

}
