package vista;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaJuego implements EstadoJuego {
    
    private PanelJuego panelJuego;
    private VistaTablero tableroJugador;
    private VistaTablero tableroEnemigo;
    private JPanel unidadesJugador[];
    private JPanel unidadesEnemigo[];
    private JButton botonRendirse;

    public VistaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        g.drawString("Tablero de", 60, 100);
        // nombre del jugador de esta partida
        g.drawString("Jugador 1", 60, 90);
        g.drawString("Tablero de", 60, 400);
        // nombre del jugador enemigo conectado
        g.drawString("Jugador 2", 60, 400);
        UtilesVista.dibujarTextoCentrado(g, "", 60, UtilesVista.FUENTE_SUBTITULO);
        
        
    }

    @Override
    public void actualizar() {
        
    }

    @Override
    public void accionesComponentes() {
        
    }
    
    private void iniciarUnidades() {
    
    }
    
}
