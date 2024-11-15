package vista;

import comunicacion.ClientConnection;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.MUbicacionUnidad;
import modelo.ModeloCasilla;
import modelo.ModeloTablero;
import modelo.ModeloUnidad;
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
    private JLabel labelEsperando;    

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
        g.drawString("Submarino", 600, 460);
        if (!panelJuego.isAncestorOf(submarino)) {
            panelJuego.agregarComponente(submarino, 600, 470, (30 * 2), 30);
        }
        g.drawString("Barco", 600, 530);
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
            // Obtener el modelo del tablero
            ModeloTablero modeloTablero = tablero.getPresentador().getModeloTablero();

            // Obtener las unidades
            Set<MUbicacionUnidad> unidades = modeloTablero.getUnidades();

            // Serializar las unidades
            List<Map<String, Object>> unidadesData = new ArrayList<>();

            for (MUbicacionUnidad ubicacionUnidad : unidades) {
                Map<String, Object> unidadData = new HashMap<>();
                ModeloUnidad unidad = ubicacionUnidad.getUnidad();

                unidadData.put("numNave", unidad.getNumNave());

                // Obtener las coordenadas
                List<Map<String, Integer>> coordenadas = new ArrayList<>();
                for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
                    Map<String, Integer> coordenada = new HashMap<>();
                    coordenada.put("x", casilla.getCoordenada().getX());
                    coordenada.put("y", casilla.getCoordenada().getY());
                    coordenadas.add(coordenada);
                }
                unidadData.put("coordenadas", coordenadas);

                unidadesData.add(unidadData);
            }

            // Enviar las unidades al servidor
            ClientConnection.getInstance().enviarUnidades(unidadesData);
            
            // Se espera en el handler la respuesta para saber si se continua
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

    public void limpiarComponentes() {
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

    public void mostrarMensajeJugadorEsperando(String nombreJugador) {
        // Puedes usar un JLabel para mostrar el mensaje en la interfaz
        labelEsperando = new JLabel(nombreJugador + " está esperando...");
        labelEsperando.setForeground(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        labelEsperando.setFont(UtilesVista.FUENTE_SUBTITULO);

        // Agregar el JLabel al panel si no está ya
        if (!panelJuego.isAncestorOf(labelEsperando)) {
            panelJuego.agregarComponente(labelEsperando, (Juego.GAME_ANCHO - 300) / 2, Juego.GAME_ALTO - 40, 300, 30);
        }
    }
    
}
