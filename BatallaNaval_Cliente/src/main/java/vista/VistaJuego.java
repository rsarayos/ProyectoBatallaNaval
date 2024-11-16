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
    private boolean esMiTurno;
    private VistaTablero tableroJugador;
    private VistaTablero tableroEnemigo;
    private JPanel unidadesJugador[];
    private JPanel unidadesEnemigo[];
    private JButton botonRendirse;

    public VistaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.tableroEnemigo = new VistaTablero();
        this.tableroEnemigo.setModo(ModoTablero.ENEMIGO);
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        g.setFont(UtilesVista.FUENTE_SUBTITULO);
        g.drawString("Tablero de", 100, 40);
        // nombre del jugador de esta partida
        g.drawString("Jugador 1", 100, 60);
        if (!panelJuego.isAncestorOf(tableroJugador)) {
            panelJuego.agregarComponente(tableroJugador, 100, 90, 300, 300);
        }
        g.drawString("Tablero de", 500, 40);
        // nombre del jugador enemigo conectado
        g.drawString("Jugador 2", 500, 60);
        if (!panelJuego.isAncestorOf(tableroEnemigo)) {
            panelJuego.agregarComponente(tableroEnemigo, 500, 90, 300, 300);
        }
        
    }

    @Override
    public void actualizar() {
        
    }

    @Override
    public void accionesComponentes() {
        
    }
    
    private void iniciarUnidades() {
    
    }

    public boolean isEsMiTurno() {
        return esMiTurno;
    }

    public void setEsMiTurno(boolean esMiTurno) {
        this.esMiTurno = esMiTurno;
    }

    public void setTableroJugador(VistaTablero tableroJugador) {
        this.tableroJugador = tableroJugador;
    }
    
}
