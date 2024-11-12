package vista;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTable;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaSalaEspera implements EstadoJuego {
    
    private PanelJuego panelJuego;
    private JButton botonContinuar;
    private JButton botonSalir;
    private JTable listaJugadores;
    
    public VistaSalaEspera(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.botonContinuar = UtilesVista.crearBoton("Continuar");
        this.botonSalir = UtilesVista.crearBoton("Regresar");
        Object[][] datos = {
            {"Jugador 1"},
            {"Jugador 2"}
        };
        String[] columnas = {"Nombre de Jugador"};
        this.listaJugadores = UtilesVista.crearTabla(datos, columnas, 400, 60);
        accionesComponentes();
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "SALA DE ESPERA", 60, UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Proporciona el codigo que se muestra debajo a otro jugador", 150, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "para que se pueda unir a esta sala", 180, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Codigo de la sala:", 250, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "XXXX", 280, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Lista de Jugadores en la sala", 410, UtilesVista.FUENTE_SUBTITULO);
        
        
        
        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonContinuar)) {
            panelJuego.agregarComponente(botonContinuar, (Juego.GAME_ANCHO - 500) / 2, Juego.GAME_ALTO - 150, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonSalir)) {
            panelJuego.agregarComponente(botonSalir, (Juego.GAME_ANCHO + 150) / 2, Juego.GAME_ALTO - 150, 200, 40);
        }
        if (!panelJuego.isAncestorOf(listaJugadores)) {
            panelJuego.agregarComponente(listaJugadores, (Juego.GAME_ANCHO - 400) / 2, Juego.GAME_ALTO - 300, 400, 60);
        }
        
        
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            panelJuego.quitarComponente(botonContinuar);
            panelJuego.quitarComponente(botonSalir);
            panelJuego.quitarComponente(listaJugadores);
            EstadosJuego.estado = EstadosJuego.ORGANIZAR; // Cambiar el estado
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            panelJuego.quitarComponente(botonContinuar);
            panelJuego.quitarComponente(botonSalir);
            panelJuego.quitarComponente(listaJugadores);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
        });
    }
    
}
