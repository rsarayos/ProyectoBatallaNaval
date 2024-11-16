package vista;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import modelo.TipoUnidad;
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
    private List<VistaNave> unidadesJugador;
    private List<VistaNave> unidadesEnemigo;
    private JButton botonRendirse;

    public VistaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.tableroEnemigo = new VistaTablero();
        this.tableroEnemigo.setModo(ModoTablero.ENEMIGO);
        this.unidadesJugador = new ArrayList<>();
        this.unidadesEnemigo = new ArrayList<>();
        this.iniciarUnidades();
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        // INFORMACION DEL JUGADOR
        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        g.setFont(UtilesVista.FUENTE_SUBTITULO);
        g.drawString("Tablero de", 100, 40);
        // nombre del jugador de esta partida
        g.drawString("Jugador 1", 100, 60);
        if (!panelJuego.isAncestorOf(tableroJugador)) {
            panelJuego.agregarComponente(tableroJugador, 100, 90, 300, 300);
        }
        g.drawString("Estado de la flota", 100, 420);
        g.drawString("Portaaviones:", 100, 440);
        int numNave = 0;
        int x = 70;
        int n = 30;
        while (numNave < 2) {            
            
            if (!panelJuego.isAncestorOf(unidadesJugador.get(numNave))) {
                panelJuego.agregarComponente(unidadesJugador.get(numNave), x + n, 460, 30, 30);
            }
            n += 40;
            numNave++;
        }
        
        // INFORMACION DEL OPONENTE
        g.drawString("Tablero de", 500, 40);
        // nombre del jugador enemigo conectado
        g.drawString("Jugador 2", 500, 60);
        if (!panelJuego.isAncestorOf(tableroEnemigo)) {
            panelJuego.agregarComponente(tableroEnemigo, 500, 90, 300, 300);
        }
        
        g.drawString("", 100, 40);
        
        
    }

    @Override
    public void actualizar() {
        
    }

    @Override
    public void accionesComponentes() {
        
    }
    
    private void iniciarUnidades() {
        // 2 portaaviones
        int numNave = 1;
        // se crean los portaaviones
        while (numNave <= 2) {            
            VistaNave nave = new VistaNave(numNave, TipoUnidad.PORTAAVIONES.TAMANO);
            unidadesJugador.add(nave);
            unidadesEnemigo.add(nave);
            numNave++;
        }
        // se crean los cruceros
        while (numNave <= 4) {            
            VistaNave nave = new VistaNave(numNave, TipoUnidad.CRUCERO.TAMANO);
            unidadesJugador.add(nave);
            unidadesEnemigo.add(nave);
            numNave++;
        }
        // se crean los submarinos
        while (numNave <= 6) {            
            VistaNave nave = new VistaNave(numNave, TipoUnidad.SUBMARINO.TAMANO);
            unidadesJugador.add(nave);
            unidadesEnemigo.add(nave);
            numNave++;
        }
        // se crean los barcos
        while (numNave <= 9) {            
            VistaNave nave = new VistaNave(numNave, TipoUnidad.BARCO.TAMANO);
            unidadesJugador.add(nave);
            unidadesEnemigo.add(nave);
            numNave++;
        }
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
