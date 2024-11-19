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
 *
 * @author alex_
 */
public class VistaBienvenida implements IVistasPanel, IVistaBienvenida {

    private PanelJuego panelJuego;
    private JTextField campoNombre;
    private JButton botonIniciar;
    private BufferedImage portada;
    private PresentadorBienvenida presentador;

    public VistaBienvenida(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        presentador = new PresentadorBienvenida(this, juego);
        crearComponentes();
        accionesComponentes();
        cargarImagenes();

    }

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

    @Override
    public void crearComponentes() {
        campoNombre = UtilesVista.crearCampoTexto(20);
        botonIniciar = UtilesVista.crearBoton("Iniciar Juego");
    }
    
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonIniciar.addActionListener(e -> {
            presentador.iniciarJuego();
        });
    }
    
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(campoNombre);
        panelJuego.quitarComponente(botonIniciar);
    }

    public void cargarImagenes() {
        this.portada = UtilesVista.cargarImagen(UtilesVista.PORTADA);
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String obtenerNombreJugador() {
        return campoNombre.getText();
    }

}
