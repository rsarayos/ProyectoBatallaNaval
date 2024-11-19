package vista;

import comunicacion.ClientConnection;
import ivistas.IVistaMenu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelo.ModeloJugador;
import presentador.Juego;
import presentador.PresentadorMenu;

/**
 *
 * @author alex_
 */
public class VistaMenu implements IVistasPanel, IVistaMenu {

    private PanelJuego panelJuego;
    private JButton botonCrearPartia;
    private JButton botonUnirsePartida;
    private JButton botonInstrucciones;
    private PresentadorMenu presentador;

    public VistaMenu(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorMenu(this, juego);
        crearComponentes();
        accionesComponentes();
    }

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

    @Override
    public void crearComponentes() {
        this.botonCrearPartia = UtilesVista.crearBoton("Crear partida");
        this.botonUnirsePartida = UtilesVista.crearBoton("Unirse a partida");
        this.botonInstrucciones = UtilesVista.crearBoton("Instrucciones");
    }

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

    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonCrearPartia);
        panelJuego.quitarComponente(botonUnirsePartida);
        panelJuego.quitarComponente(botonInstrucciones);
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
