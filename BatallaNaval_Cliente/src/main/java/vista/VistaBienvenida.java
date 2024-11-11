package vista;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JTextField;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaBienvenida implements EstadoJuego {

    private PanelJuego panelJuego;
    private JTextField campoNombre;
    private JButton botonIniciar;
    private BufferedImage portada;

    public VistaBienvenida(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        campoNombre = UtilesVista.crearCampoTexto(20);
        botonIniciar = UtilesVista.crearBoton("Iniciar Juego");
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
        UtilesVista.dibujarTextoCentrado(g, "El nombre puede de ser de hasta 15 caracteres y estar compuesto por letras y numeros", 580, UtilesVista.FUENTE_SUBTITULO);

        if (!panelJuego.isAncestorOf(campoNombre)) {
            panelJuego.agregarComponente(campoNombre, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 120, 200, 30);
        }
        if (!panelJuego.isAncestorOf(botonIniciar)) {
            panelJuego.agregarComponente(botonIniciar, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 80, 200, 40);
        }

    }

    @Override
    public void actualizar() {

    }

    public void accionesComponentes() {
        // Agregar acción al botón
        botonIniciar.addActionListener(e -> {
            panelJuego.quitarComponente(campoNombre);
            panelJuego.quitarComponente(botonIniciar);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
        });
    }
    
    public void cargarImagenes() {
        this.portada = UtilesVista.cargarImagen(UtilesVista.PORTADA);
    }

}
