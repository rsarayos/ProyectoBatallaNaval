package vista;

import ivistas.IVistaBienvenida;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import presentador.Juego;
import presentador.PresentadorBienvenida;

/**
 * Clase que representa la vista de bienvenida del juego. Implementa las interfaces IVistasPanel e IVistaBienvenida.
 * Proporciona la interfaz gráfica donde el jugador puede ingresar su nombre antes de iniciar el juego.
 *
 * @author alex_
 */
public class VistaBienvenida implements IVistasPanel, IVistaBienvenida {

    /**
     * El panel principal del juego donde se agregarán los componentes de la vista.
     */
    private PanelJuego panelJuego;
    
    /**
     * Campo de texto donde el jugador ingresa su nombre.
     */
    private JTextField campoNombre;
    
    /**
     * Botón para iniciar el juego.
     */
    private JButton botonIniciar;
    
    /**
     * Imagen de portada utilizada en la vista de bienvenida.
     */
    private BufferedImage portada;
    
    /**
     * Presentador para gestionar la lógica de la vista de bienvenida.
     */
    private PresentadorBienvenida presentador;

    /**
     * Constructor de la clase VistaBienvenida.
     *
     * @param panelJuego El panel principal del juego donde se agregarán los componentes de la vista.
     * @param juego La instancia del juego actual.
     */
    public VistaBienvenida(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        presentador = new PresentadorBienvenida(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();

    }

    /**
     * Dibuja la vista de bienvenida en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.drawImage(portada, 250, 100, null);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "Bienvenido a Batalla Naval", 60, UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Por favor, ingrese su nombre de usuario", 550, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "El nombre puede ser de hasta 15 caracteres y estar compuesto por letras y numeros", 580, UtilesVista.FUENTE_SUBTITULO);

        if (!panelJuego.isAncestorOf(campoNombre)) {
            panelJuego.agregarComponente(campoNombre, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 120, 200, 30);
        }
        if (!panelJuego.isAncestorOf(botonIniciar)) {
            panelJuego.agregarComponente(botonIniciar, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 80, 200, 40);
        }

    }    

    /**
     * Crea los componentes necesarios para la vista de bienvenida, como el campo de texto y el botón de iniciar.
     */
    @Override
    public void crearComponentes() {
        campoNombre = UtilesVista.crearCampoTexto(20);
        botonIniciar = UtilesVista.crearBoton("Iniciar Juego");
    }
    
    /**
     * Define las acciones para los componentes de la vista, como el botón de iniciar juego.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonIniciar.addActionListener(e -> {
            presentador.iniciarJuego();
        });
    }
    
    /**
     * Quita los componentes de la vista de bienvenida del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(campoNombre);
        panelJuego.quitarComponente(botonIniciar);
    }

    /**
     * Carga las imágenes necesarias para la vista de bienvenida.
     */
    public void cargarImagenes() {
        this.portada = UtilesVista.cargarImagen(UtilesVista.PORTADA);
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

    /**
     * Obtiene el nombre del jugador ingresado en el campo de texto.
     *
     * @return El nombre del jugador ingresado.
     */
    @Override
    public String obtenerNombreJugador() {
        return campoNombre.getText();
    }

}
