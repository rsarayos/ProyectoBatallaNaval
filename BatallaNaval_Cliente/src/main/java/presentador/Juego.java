package presentador;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
}
