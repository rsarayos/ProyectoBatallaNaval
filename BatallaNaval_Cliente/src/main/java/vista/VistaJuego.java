package vista;

import ivistas.IVistaJuego;
import java.awt.Cursor;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.ModeloJugador;
import modelo.TipoUnidad;
import presentador.Juego;
import presentador.PresentadorJuego;

/**
 *
 * @author alex_
 */
public class VistaJuego implements EstadoJuego, IVistaJuego {

    private PanelJuego panelJuego;
    private boolean esMiTurno;
    private VistaTablero tableroJugador;
    private VistaTablero tableroEnemigo;
    private List<VistaNave> unidadesJugador;
    private List<VistaNave> unidadesEnemigo;
    private JLabel lblTurno;
    private String ultimoMensaje;
    private String nombreOponente;
    private JLabel lblUltimoMensaje;
    private PresentadorJuego presentador;

    public VistaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorJuego(this);
        crearComponentes();
        accionesComponentes();
    }

    @Override
    public void crearComponentes() {
        tableroEnemigo = new VistaTablero();
        tableroEnemigo.setModo(ModoTablero.ENEMIGO);
        tableroEnemigo.habilitarInteraccion(false);
        unidadesJugador = new ArrayList<>();
        unidadesEnemigo = new ArrayList<>();
        iniciarUnidades();

        lblTurno = new JLabel("", SwingConstants.CENTER);
        lblTurno.setFont(UtilesVista.FUENTE_SUBTITULO);
        lblTurno.setForeground(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);

        lblUltimoMensaje = new JLabel("", SwingConstants.CENTER);
        lblUltimoMensaje.setFont(UtilesVista.FUENTE_SUBTITULO);
        lblUltimoMensaje.setForeground(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);

        int labelWidth = Juego.GAME_ANCHO; // Ancho del juego
        int labelHeight = 30; // Altura fija para los labels

        if (!panelJuego.isAncestorOf(lblTurno)) {
            panelJuego.agregarComponente(lblTurno, 0, 600, labelWidth, labelHeight);
        }
        panelJuego.revalidate();
        panelJuego.repaint();
    }

    @Override
    public void accionesComponentes() {

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
        g.drawString(nombreOponente != null ? nombreOponente : "Oponente", 500, 60);
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

        g.setFont(UtilesVista.FUENTE_SUBTITULO);
        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "Resultado del último disparo", 480, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, ultimoMensaje != null ? ultimoMensaje : "", 500, UtilesVista.FUENTE_SUBTITULO);

    }

    @Override
    public void actualizarInterfazTurno(boolean esMiTurno) {
        this.esMiTurno = esMiTurno;
        if (esMiTurno) {
            tableroEnemigo.habilitarInteraccion(true);
            lblTurno.setText("Es tu turno");
        } else {
            tableroEnemigo.habilitarInteraccion(false);
            lblTurno.setText("Es el turno del oponente");
        }
    }

    @Override
    public void actualizarTableroEnemigo(int x, int y, boolean impacto) {
        tableroEnemigo.actualizarCasilla(x, y, impacto);
    }

    @Override
    public void actualizarEstadoFlotaEnemigo(int numeroNave, int vidaNave) {
        VistaNave nave = unidadesEnemigo.stream()
                .filter(n -> n.getNumNave() == numeroNave)
                .findFirst()
                .orElse(null);
        if (nave != null) {
            nave.setVida(vidaNave);
            nave.repaint();
        }
    }

    @Override
    public void actualizarTableroJugador(int x, int y, boolean impacto) {
        tableroJugador.actualizarCasilla(x, y, impacto);
    }

    @Override
    public void actualizarEstadoFlotaJugador(int numeroNave, int vidaNave) {
        VistaNave nave = unidadesJugador.stream()
                .filter(n -> n.getNumNave() == numeroNave)
                .findFirst()
                .orElse(null);
        if (nave != null) {
            nave.setVida(vidaNave);
            nave.repaint();
        }
    }

    @Override
    public void mostrarUltimoMensaje(String mensaje) {
        this.ultimoMensaje = mensaje;
        lblUltimoMensaje.setText(mensaje);
        panelJuego.repaint();
        lblUltimoMensaje.repaint();
    }

    @Override
    public void bloquearInteraccion() {
        tableroEnemigo.habilitarInteraccion(false);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje);
    }

    @Override
    public void finalizarJuego(String ganador) {
        mostrarMensaje("¡El juego ha terminado! El ganador es: " + ganador);
        bloquearInteraccion();
    }

    @Override
    public VistaTablero getTableroJugador() {
        return tableroJugador;
    }

    @Override
    public VistaTablero getTableroEnemigo() {
        return tableroEnemigo;
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

    public void setTableroJugador(VistaTablero tableroJugador) {
        this.tableroJugador = tableroJugador;
    }

    public List<VistaNave> getUnidadesJugador() {
        return unidadesJugador;
    }

    public List<VistaNave> getUnidadesEnemigo() {
        return unidadesEnemigo;
    }

    @Override
    public void setNombreOponente(String nombreOponente) {
        this.nombreOponente = nombreOponente;
    }

    @Override
    public void quitarComponentes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public PresentadorJuego getPresentador() {
        return this.presentador;
    }

}
