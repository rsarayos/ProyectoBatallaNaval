package vista;

import ivistas.IVistaMenu;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import presentador.Juego;
import presentador.PresentadorMenu;

/**
 * Clase que representa la vista del menú principal del juego de Batalla Naval.
 * Proporciona la interfaz gráfica donde el jugador puede elegir entre crear una partida, unirse a una partida existente o ver las instrucciones del juego.
 * Implementa las interfaces {@link IVistasPanel} e {@link IVistaMenu}.
 *
 * @author alex_
 */
public class VistaMenu implements IVistasPanel, IVistaMenu {

    /**
     * El panel de juego principal donde se agregarán los componentes de la vista.
     */
    private PanelJuego panelJuego;
    
    /**
     * Botón para crear una partida nueva.
     */
    private JButton botonCrearPartia;
    
    /**
     * Botón para unirse a una partida existente.
     */
    private JButton botonUnirsePartida;
    
    /**
     * Botón para acceder a las instrucciones del juego.
     */
    private JButton botonInstrucciones;
    
    /**
     * Presentador asociado a la vista del menú.
     */
    private PresentadorMenu presentador;

    /**
     * Constructor de la clase VistaMenu.
     * Inicializa el presentador y los componentes de la vista.
     *
     * @param panelJuego El panel de juego principal donde se agregarán los componentes.
     * @param juego La instancia del juego actual.
     */
    public VistaMenu(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorMenu(this, juego);
        crearComponentes();
        accionesComponentes();
    }

    /**
     * Dibuja la vista del menú en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "MENU", 60, UtilesVista.FUENTE_TITULO);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonCrearPartia)) {
            panelJuego.agregarComponente(botonCrearPartia, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 500, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonUnirsePartida)) {
            panelJuego.agregarComponente(botonUnirsePartida, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 400, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonInstrucciones)) {
            panelJuego.agregarComponente(botonInstrucciones, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 300, 200, 40);
        }

    }

    /**
     * Crea los componentes necesarios para la vista del menú, como los botones de crear partida, unirse a partida e instrucciones.
     */
    @Override
    public void crearComponentes() {
        this.botonCrearPartia = UtilesVista.crearBoton("Crear partida");
        this.botonUnirsePartida = UtilesVista.crearBoton("Unirse a partida");
        this.botonInstrucciones = UtilesVista.crearBoton("Instrucciones");
    }

    /**
     * Define las acciones para los componentes de la vista, como los botones del menú.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonCrearPartia.addActionListener(e -> {
            presentador.crearPartida();
        });
        // Agregar acción al botón
        botonInstrucciones.addActionListener(e -> {
            presentador.avanzarAInstrucciones();
        });
        // Agregar acción al botón
        botonUnirsePartida.addActionListener(e -> {
            presentador.avanzarAUnirseAPartida();
        });
    }

    /**
     * Quita los componentes de la vista del menú del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonCrearPartia);
        panelJuego.quitarComponente(botonUnirsePartida);
        panelJuego.quitarComponente(botonInstrucciones);
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    @Override
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
