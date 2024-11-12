package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaOrganizar implements EstadoJuego {

    private PanelJuego panelJuego;
    private VistaTablero tablero;
    private JComboBox<String> colorSelector;
    private JButton botonJugar;
    private JPanel portaaviones;
    private JPanel crucero;
    private JPanel submarino;
    private JPanel barco;    

    public VistaOrganizar(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.tablero = new VistaTablero();
        this.colorSelector = UtilesVista.crearComboBox(UtilesVista.LISTA_COLORES_BARCO, 200, 30);
        this.colorSelector.setSelectedItem("Negro");
        this.botonJugar = UtilesVista.crearBoton("Jugar");
        this.portaaviones = UtilesVista.crearBarcoVista((30 * 4), 30, UtilesVista.BARCO_NEGRO);
        this.crucero = UtilesVista.crearBarcoVista((30 * 3), 30, UtilesVista.BARCO_NEGRO);
        this.submarino = UtilesVista.crearBarcoVista((30 * 2), 30, UtilesVista.BARCO_NEGRO);
        this.barco = UtilesVista.crearBarcoVista((30 * 1), 30, UtilesVista.BARCO_NEGRO);
        accionesComponentes();
    }

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
        g.drawString("Portaaviones", 600, 460);
        if (!panelJuego.isAncestorOf(submarino)) {
            panelJuego.agregarComponente(submarino, 600, 470, (30 * 2), 30);
        }
        g.drawString("Portaaviones", 600, 530);
        if (!panelJuego.isAncestorOf(barco)) {
            panelJuego.agregarComponente(barco, 600, 540, (30 * 1), 30);
        }
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonJugar.addActionListener(e -> {
            panelJuego.quitarComponente(botonJugar);
            panelJuego.quitarComponente(colorSelector);
            panelJuego.quitarComponente(portaaviones);
            panelJuego.quitarComponente(crucero);
            panelJuego.quitarComponente(submarino);
            panelJuego.quitarComponente(barco);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
        });
        // Agregar acción al selector
        colorSelector.addActionListener(e -> {
            
            String nombreColorSeleccionado = (String) colorSelector.getSelectedItem();
            Color nuevoColorNave = UtilesVista.obtenerColorBarco(nombreColorSeleccionado);

            // Actualizar el color de las naves en el tablero
            tablero.setColorNave(nuevoColorNave);

            // Actualizar el color de los paneles laterales
            portaaviones.setBackground(nuevoColorNave);
            crucero.setBackground(nuevoColorNave);
            submarino.setBackground(nuevoColorNave);
            barco.setBackground(nuevoColorNave);
        });
    }

}
