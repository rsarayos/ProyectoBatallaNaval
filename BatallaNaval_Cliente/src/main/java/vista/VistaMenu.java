package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaMenu implements EstadoJuego {

    private PanelJuego panelJuego;
    private JButton botonCrearPartia;
    private JButton botonUnirsePartida;
    private JButton botonInstrucciones;
    

    public VistaMenu(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.botonCrearPartia = UtilesVista.crearBoton("Crear partida");
        this.botonUnirsePartida = UtilesVista.crearBoton("Unirse a partida");
        this.botonInstrucciones = UtilesVista.crearBoton("Instrucciones");
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
    public void actualizar() {
        
    }

    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonCrearPartia.addActionListener(e -> {
            panelJuego.quitarComponente(botonCrearPartia);
            panelJuego.quitarComponente(botonUnirsePartida);
            panelJuego.quitarComponente(botonInstrucciones);
            EstadosJuego.estado = EstadosJuego.SALA_ESPERA; // Cambiar el estado
        });
        // Agregar acción al botón
        botonInstrucciones.addActionListener(e -> {
            panelJuego.quitarComponente(botonCrearPartia);
            panelJuego.quitarComponente(botonUnirsePartida);
            panelJuego.quitarComponente(botonInstrucciones);
            EstadosJuego.estado = EstadosJuego.INSTRUCCIONES; // Cambiar el estado
        });
        // Agregar acción al botón
        botonUnirsePartida.addActionListener(e -> {
            panelJuego.quitarComponente(botonCrearPartia);
            panelJuego.quitarComponente(botonUnirsePartida);
            panelJuego.quitarComponente(botonInstrucciones);
            EstadosJuego.estado = EstadosJuego.BUSCAR_PARTIDA; // Cambiar el estado
        });
    }
    
}
