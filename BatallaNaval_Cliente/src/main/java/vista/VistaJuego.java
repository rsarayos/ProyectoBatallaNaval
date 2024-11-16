package vista;

import java.awt.Cursor;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import modelo.ModeloJugador;
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
        g.drawString(ModeloJugador.getInstance().getNombre(), 100, 60);
        if (!panelJuego.isAncestorOf(tableroJugador)) {
            panelJuego.agregarComponente(tableroJugador, 100, 90, 300, 300);
        }
        g.drawString("Estado de la flota", 100, 420);
        g.drawString("Portaaviones", 100, 450);
        g.drawString("Cruceros    ", 100, 510);
        g.drawString("Submarinos  ", 100, 570);
        g.drawString("Barcos      ", 100, 630);
        
        // INFORMACION DEL OPONENTE
        g.drawString("Tablero de", 500, 40);
        // nombre del jugador enemigo conectado
        g.drawString("Jugador 2", 500, 60);
        if (!panelJuego.isAncestorOf(tableroEnemigo)) {
            tableroEnemigo.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            panelJuego.agregarComponente(tableroEnemigo, 500, 90, 300, 300);
        }
        g.drawString("Estado de la flota", 630, 420);
        g.drawString("Portaaviones", 670, 450);
        g.drawString("    Cruceros", 681, 510);
        g.drawString("  Submarinos", 672, 570);
        g.drawString("      Barcos", 690, 630);
        
        // colocar representacion de las 
        colocarEstadoFlota();
        
        // Mostrar jugador en turno
        UtilesVista.dibujarTextoCentrado(g, "Turno del jugador", 430, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "JUGADOR", 450, UtilesVista.FUENTE_SUBTITULO);
        
        // Mostrar el resultado del ataque
        UtilesVista.dibujarTextoCentrado(g, "Resultado ultimo disparo", 480, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "El impacto falló", 480, UtilesVista.FUENTE_SUBTITULO); // ejemplo
        
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
            VistaNave naveJugador = new VistaNave(numNave, TipoUnidad.PORTAAVIONES.TAMANO);
            VistaNave naveEnemigo = new VistaNave(numNave, TipoUnidad.PORTAAVIONES.TAMANO);
            unidadesJugador.add(naveJugador);
            unidadesEnemigo.add(naveEnemigo);
            numNave++;
        }
        // se crean los cruceros
        while (numNave <= 4) {            
            VistaNave naveJugador = new VistaNave(numNave, TipoUnidad.CRUCERO.TAMANO);
            VistaNave naveEnemigo = new VistaNave(numNave, TipoUnidad.CRUCERO.TAMANO);
            unidadesJugador.add(naveJugador);
            unidadesEnemigo.add(naveEnemigo);
            numNave++;
        }
        // se crean los submarinos
        while (numNave <= 8) {            
            VistaNave naveJugador = new VistaNave(numNave, TipoUnidad.SUBMARINO.TAMANO);
            VistaNave naveEnemigo = new VistaNave(numNave, TipoUnidad.SUBMARINO.TAMANO);
            unidadesJugador.add(naveJugador);
            unidadesEnemigo.add(naveEnemigo);
            numNave++;
        }
        // se crean los barcos
        while (numNave <= 11) {            
            VistaNave naveJugador = new VistaNave(numNave, TipoUnidad.BARCO.TAMANO);
            VistaNave naveEnemigo = new VistaNave(numNave, TipoUnidad.BARCO.TAMANO);
            unidadesJugador.add(naveJugador);
            unidadesEnemigo.add(naveEnemigo);
            numNave++;
        }
    }
    
    private void colocarEstadoFlota() {
        int numNave = 0;
        int xJugador = 70;
        int xEnemigo = 800;
        int n = 30;
        // Portaaviones
        while (numNave < 2) {
            if (!panelJuego.isAncestorOf(unidadesJugador.get(numNave))) {
                panelJuego.agregarComponente(unidadesJugador.get(numNave), xJugador + n, 460, 30, 30);
            }
            if (!panelJuego.isAncestorOf(unidadesEnemigo.get(numNave))) {
                panelJuego.agregarComponente(unidadesEnemigo.get(numNave), xEnemigo - n, 460, 30, 30);
            }            
            n += 40;
            numNave++;
        }
        
        n = 30;
        // cruceros
        while (numNave < 4) {
            if (!panelJuego.isAncestorOf(unidadesJugador.get(numNave))) {
                panelJuego.agregarComponente(unidadesJugador.get(numNave), xJugador + n, 520, 30, 30);
            }
            if (!panelJuego.isAncestorOf(unidadesEnemigo.get(numNave))) {
                panelJuego.agregarComponente(unidadesEnemigo.get(numNave), xEnemigo - n, 520, 30, 30);
            }            
            n += 40;
            numNave++;
        }
        
        n = 30;
        // submarinos
        while (numNave < 8) {
            if (!panelJuego.isAncestorOf(unidadesJugador.get(numNave))) {
                panelJuego.agregarComponente(unidadesJugador.get(numNave), xJugador + n, 580, 30, 30);
            }
            if (!panelJuego.isAncestorOf(unidadesEnemigo.get(numNave))) {
                panelJuego.agregarComponente(unidadesEnemigo.get(numNave), xEnemigo - n, 580, 30, 30);
            }            
            n += 40;
            numNave++;
        }
        
        n = 30;
        // Barcos
        while (numNave < 11) {
            if (!panelJuego.isAncestorOf(unidadesJugador.get(numNave))) {
                panelJuego.agregarComponente(unidadesJugador.get(numNave), xJugador + n, 640, 30, 30);
            }
            if (!panelJuego.isAncestorOf(unidadesEnemigo.get(numNave))) {
                panelJuego.agregarComponente(unidadesEnemigo.get(numNave), xEnemigo - n, 640, 30, 30);
            }            
            n += 40;
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

    public List<VistaNave> getUnidadesJugador() {
        return unidadesJugador;
    }

    public List<VistaNave> getUnidadesEnemigo() {
        return unidadesEnemigo;
    }  
    
}
