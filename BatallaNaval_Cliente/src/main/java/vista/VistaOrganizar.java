package vista;

import ivistas.IVistaOrganizar;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import presentador.Juego;
import presentador.PresentadorOrganizar;

/**
 * Clase que representa la vista de la fase de organización del tablero en el juego.
 * Los jugadores pueden organizar sus unidades y seleccionar el color de las naves antes de comenzar la partida.
 * 
 * @author alex_
 */
public class VistaOrganizar implements IVistasPanel, IVistaOrganizar {

    /**
     * Panel principal del juego donde se agregan los componentes visuales.
     */
    private PanelJuego panelJuego;
    
    /**
     * Vista del tablero donde el jugador organiza sus unidades.
     */
    private VistaTablero tablero;
    
    /**
     * Selector de color para las naves.
     */
    private JComboBox<String> colorSelector;
    
    /**
     * Botón para confirmar que el jugador está listo para comenzar el juego.
     */
    private JButton botonJugar;
    
    /**
     * Panel que representa visualmente el portaaviones.
     */
    private JPanel portaaviones;
    
    /**
     * Panel que representa visualmente el crucero.
     */
    private JPanel crucero;
    
    /**
     * Panel que representa visualmente el submarino.
     */
    private JPanel submarino;
    
    /**
     * Panel que representa visualmente el barco.
     */
    private JPanel barco;
    
    /**
     * Etiqueta que muestra un mensaje cuando el jugador está esperando.
     */
    private JLabel labelEsperando;
    
    /**
     * Presentador que maneja la lógica de organización de las naves.
     */
    private PresentadorOrganizar presentador;

    /**
     * Constructor de la clase VistaOrganizar.
     * Inicializa el panel de juego, crea los componentes y define sus acciones.
     * 
     * @param panelJuego Panel principal del juego.
     */
    public VistaOrganizar(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorOrganizar(this);
        crearComponentes();
        accionesComponentes();
    }

    /**
     * Dibuja la interfaz gráfica de la vista de organización del tablero.
     * 
     * @param g Objeto Graphics para dibujar la interfaz.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "ORGANIZAR TABLERO", 60, UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Ordena tus unidades dentro del tablero", 100, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Presiona click izquierdo y arrastra la nave a las posiciones disponibles", 120, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Presiona click derecho para rotar la nave (si hay espacio)", 140, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Una nave no puede estar adyacente o sobre otra nave", 160, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Selecciona el color para tus naves:", 200, UtilesVista.FUENTE_SUBTITULO);
        
        if (!panelJuego.isAncestorOf(tablero)) {
            tablero.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelJuego.agregarComponente(tablero, 100, 300, 300, 300);
        }
        if (!panelJuego.isAncestorOf(colorSelector)) {
            panelJuego.agregarComponente(colorSelector, (Juego.GAME_ANCHO - 200) / 2, 218, 200, 30);
        }
        if (!panelJuego.isAncestorOf(botonJugar)) {
            panelJuego.agregarComponente(botonJugar, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 80, 200, 30);
        }
        g.drawString("Portaaviones", 600, 320);
        if (!panelJuego.isAncestorOf(portaaviones)) {
            panelJuego.agregarComponente(portaaviones, 600, 330, (30 * 4), 30);
        }
        g.drawString("Crucero", 600, 390);
        if (!panelJuego.isAncestorOf(crucero)) {
            panelJuego.agregarComponente(crucero, 600, 400, (30 * 3), 30);
        }
        g.drawString("Submarino", 600, 460);
        if (!panelJuego.isAncestorOf(submarino)) {
            panelJuego.agregarComponente(submarino, 600, 470, (30 * 2), 30);
        }
        g.drawString("Barco", 600, 530);
        if (!panelJuego.isAncestorOf(barco)) {
            panelJuego.agregarComponente(barco, 600, 540, (30 * 1), 30);
        }
    }
    
    /**
     * Crea y configura los componentes gráficos de la vista de organización.
     */
    @Override
    public void crearComponentes() {
        this.tablero = new VistaTablero();
        this.tablero.setModo(ModoTablero.ORGANIZAR);
        this.colorSelector = UtilesVista.crearComboBox(UtilesVista.LISTA_COLORES_BARCO, 200, 30);
        this.colorSelector.setSelectedItem("Negro");
        this.botonJugar = UtilesVista.crearBoton("Jugar");
        this.portaaviones = UtilesVista.crearBarcoVista((30 * 4), 30, UtilesVista.BARCO_NEGRO);
        this.crucero = UtilesVista.crearBarcoVista((30 * 3), 30, UtilesVista.BARCO_NEGRO);
        this.submarino = UtilesVista.crearBarcoVista((30 * 2), 30, UtilesVista.BARCO_NEGRO);
        this.barco = UtilesVista.crearBarcoVista((30 * 1), 30, UtilesVista.BARCO_NEGRO);
    }

    /**
     * Define las acciones asociadas a los componentes de la vista.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonJugar.addActionListener(e -> {
            tablero.setModo(ModoTablero.JUGADOR);
            presentador.enviarUnidadesAlServidor();
        });
        // Agregar acción al selector
        colorSelector.addActionListener(e -> {
            String nombreColorSeleccionado = (String) colorSelector.getSelectedItem();
            presentador.cambiarColorNaves(nombreColorSeleccionado);
        });
    }
    
    /**
     * Quita los componentes gráficos del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        this.panelJuego.quitarComponente(botonJugar);
        this.panelJuego.quitarComponente(colorSelector);
        this.panelJuego.quitarComponente(barco);
        this.panelJuego.quitarComponente(submarino);
        this.panelJuego.quitarComponente(crucero);
        this.panelJuego.quitarComponente(portaaviones);
        this.panelJuego.quitarComponente(tablero);
        if (labelEsperando != null) {
            this.panelJuego.quitarComponente(labelEsperando);
        }
    }

    /**
     * Muestra un mensaje indicando que un jugador está esperando.
     * 
     * @param nombreJugador Nombre del jugador que está esperando.
     */
    @Override
    public void mostrarMensajeJugadorEsperando(String nombreJugador) {
        if (labelEsperando == null) {
            labelEsperando = new JLabel(nombreJugador + " está esperando...");
            labelEsperando.setForeground(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
            labelEsperando.setFont(UtilesVista.FUENTE_SUBTITULO);
            panelJuego.agregarComponente(labelEsperando, (Juego.GAME_ANCHO - 300) / 2, Juego.GAME_ALTO - 40, 300, 30);
        } else {
            labelEsperando.setText(nombreJugador + " está esperando...");
        }
    }

    /**
     * Obtiene la vista del tablero.
     * 
     * @return VistaTablero utilizada para organizar las unidades.
     */
    @Override
    public VistaTablero getTablero() {
        return tablero;
    }

    /**
     * Muestra un mensaje emergente con la información proporcionada.
     * 
     * @param mensaje Mensaje a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje);
    }

    /**
     * Bloquea la interfaz para evitar la interacción del jugador.
     */
    @Override
    public void bloquearInterfaz() {
        // Deshabilitar botones y componentes para evitar interacción
        botonJugar.setEnabled(false);
        colorSelector.setEnabled(false);
        tablero.setEnabled(false);
    }

    /**
     * Navega a la fase de juego, quitando los componentes actuales.
     */
    @Override
    public void navegarAJugar() {
        quitarComponentes();
        
    }

    /**
     * Actualiza el color de los paneles laterales que representan las naves.
     * 
     * @param nuevoColor Nuevo color a aplicar a los paneles.
     */
    @Override
    public void actualizarColorPanelesLaterales(Color nuevoColor) {
        portaaviones.setBackground(nuevoColor);
        crucero.setBackground(nuevoColor);
        submarino.setBackground(nuevoColor);
        barco.setBackground(nuevoColor);
    }

    /**
     * Obtiene el presentador asociado a la vista de organización.
     * 
     * @return PresentadorOrganizar asociado a la vista.
     */
    public PresentadorOrganizar getPresentador() {
        return presentador;
    }

}
