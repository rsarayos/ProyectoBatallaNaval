package presentador;

import comunicacion.ClientConnection;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import modelo.ModeloJugador;
import vista.EstadosJuego;
import vista.PanelJuego;
import vista.VentanaJuego;
import vista.VistaBienvenida;
import vista.VistaBuscarPartida;
import vista.VistaInstrucciones;
import vista.VistaMenu;
import vista.VistaOrganizar;
import vista.VistaSalaEspera;

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
    // FPS deseados 
    protected static final int FPS_SET = 60;
    // UPS deseados 
    protected static final int UPS_SET = 150;
    public final static int GAME_ANCHO = 900;
    public final static int GAME_ALTO = 720;
    
    private VistaBienvenida vBienvenida;
    private VistaMenu vMenu;
    private VistaOrganizar vOrganizar;
    private VistaInstrucciones vInstrucciones;
    private VistaSalaEspera vSalaEspera;
    private VistaBuscarPartida vBuscarPartida;
    
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

        // Inicializa las vistas
        vBienvenida = new VistaBienvenida(panel);
        vMenu = new VistaMenu(panel);
        vOrganizar = new VistaOrganizar(panel);
        vInstrucciones = new VistaInstrucciones(panel);
        vSalaEspera = new VistaSalaEspera(panel);
        vBuscarPartida = new VistaBuscarPartida(panel);
        
        // Establece el estado inicial
        EstadosJuego.estado = EstadosJuego.BIENVENIDA;

        // Inicia el bucle principal del juego
        this.inicioJuegoLoop();

    }

    /**
     * Inicia el bucle principal del juego en un nuevo hilo.
     */
    private void inicioJuegoLoop() {
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    /**
     * Método para actualizar el estado del juego.
     */
    public void update() {
        
        
    }

    /**
     * Método para renderizar (dibujar) el juego en pantalla.
     *
     * @param g Objeto Graphics para dibujar.
     */
    public void renderizar(Graphics g) {
        
        switch (EstadosJuego.estado) {
            case BIENVENIDA:
                if(vBienvenida != null)
                vBienvenida.dibujar(g);
                break;
            case MENU:
                vMenu.dibujar(g);
                break;
            case ORGANIZAR:
                vOrganizar.dibujar(g);
                break;
            case INSTRUCCIONES:
                vInstrucciones.dibujar(g);
                break;
            case SALA_ESPERA:
                vSalaEspera.dibujar(g);
                break;
            case BUSCAR_PARTIDA:
                vBuscarPartida.dibujar(g);
                break;
            default:
                throw new AssertionError();
        }

    }

    /**
     * Bucle principal del juego. Controla la lógica de actualización y
     * renderizado del juego a una velocidad específica, y muestra estadísticas
     * de FPS (cuadros por segundo) y UPS (actualizaciones por segundo).
     */
    @Override
    public void run() {

        HiloActualizacion updateThread = new HiloActualizacion(this);
        HiloRenderizado renderThread = new HiloRenderizado(this);

        updateThread.start();
        renderThread.start();

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
            String accion = (String) mensaje.get("accion");
            switch (accion) {
                case "CREAR_PARTIDA":
                    handleCrearPartidaResponse(mensaje);
                    break;
                case "NUEVO_JUGADOR":
                    handleNuevoJugador(mensaje);
                    break;
                case "UNIRSE_PARTIDA":
                    handleUnirsePartidaResponse(mensaje);
                    break;
                case "ACTUALIZAR_ESTADO_LISTO":
                    handleActualizarEstadoListo(mensaje);
                    break;
                case "TODOS_LISTOS":
                    handleTodosListos();
                    break;
                case "ATACAR":
//                    handleAtacarResponse(mensaje);
                    break;
                // Manejar otras acciones
                default:
                    System.out.println("Acción desconocida recibida: " + accion);
                    break;
            }
        });
    }

    private void handleCrearPartidaResponse(Map<String, Object> mensaje) {
        String codigoAcceso = (String) mensaje.get("codigo_acceso");
        String idJugador = (String) mensaje.get("id");

        // Guardar el id del jugador en ModeloJugador
        ModeloJugador jugador = ModeloJugador.getInstance();
        jugador.setId(idJugador);

        // Configurar la vista de sala de espera
        vSalaEspera.setCodigoAcceso(codigoAcceso);
        vSalaEspera.setIdJugador(idJugador);

        // Actualizar la lista de jugadores en la sala de espera
        vSalaEspera.agregarJugador(jugador.getNombre());

        // Si no se ha cambiado el estado
        // EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
    }
    
    private void handleNuevoJugador(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        vSalaEspera.agregarJugador(nombreJugador);
    }
    
    private void handleUnirsePartidaResponse(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            // Mostrar mensaje de error al usuario
            String error = (String) mensaje.get("error");
            JOptionPane.showMessageDialog(panel, error, "Error", JOptionPane.ERROR_MESSAGE);

            // Volver al estado anterior si es necesario
            EstadosJuego.estado = EstadosJuego.BUSCAR_PARTIDA;
        } else {
            String idJugador = (String) mensaje.get("id");
            String codigoAcceso = (String) mensaje.get("codigo_acceso");
            List<String> nombresJugadores = (List<String>) mensaje.get("nombres_jugadores");

            // Guardar el id del jugador en ModeloJugador
            ModeloJugador jugador = ModeloJugador.getInstance();
            jugador.setId(idJugador);

            // Configurar la vista de sala de espera
            vSalaEspera.setCodigoAcceso(codigoAcceso);
            vSalaEspera.setIdJugador(idJugador);

            // Limpiar y actualizar la lista de jugadores
            vSalaEspera.limpiarListaJugadores();
            for (String nombreJugador : nombresJugadores) {
                vSalaEspera.agregarJugador(nombreJugador);
            }

            // Cambiar al estado de sala de espera
            vBuscarPartida.quitarComponentes();
            EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
        }
    }

    private void handleActualizarEstadoListo(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        boolean listo = (Boolean) mensaje.get("listo");

        vSalaEspera.agregarOActualizarJugador(nombreJugador, listo);
    }

    private void handleTodosListos() {
        // Cambiar al estado de organizar el tablero
        EstadosJuego.estado = EstadosJuego.ORGANIZAR;
        // Limpiar componentes de la sala de espera si es necesario
        vSalaEspera.limpiarComponentes();
    }

}
