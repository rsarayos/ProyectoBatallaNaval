package vista;

import ivistas.IVistaJuego;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import modelo.ModeloJugador;
import modelo.TipoUnidad;
import presentador.Juego;
import presentador.PresentadorJuego;

/**
 * Clase que representa la vista del juego de Batalla Naval.
 * Proporciona la interfaz gráfica durante la fase de juego, permitiendo al jugador interactuar con su propio tablero y el tablero del oponente.
 * Implementa las interfaces {@link IVistasPanel} e {@link IVistaJuego}.
 *
 * @author alex_
 */
public class VistaJuego implements IVistasPanel, IVistaJuego {

    /**
     * El panel de juego principal donde se agregarán los componentes de la vista.
     */
    private PanelJuego panelJuego;
    
    /**
     * Indica si es el turno del jugador actual.
     */
    private boolean esMiTurno;
    
    /**
     * El tablero del jugador.
     */
    private VistaTablero tableroJugador;
    
    /**
     * El tablero del enemigo.
     */
    private VistaTablero tableroEnemigo;
    
    /**
     * Lista de unidades del jugador.
     */
    private List<VistaNave> unidadesJugador;
    
    /**
     * Lista de unidades del enemigo.
     */
    private List<VistaNave> unidadesEnemigo;
    
    /**
     * Etiqueta que muestra el estado del turno.
     */
    private JLabel lblTurno;
    
    /**
     * Etiqueta que muestra el tiempo restante del turno.
     */
    private JLabel lblTemporizador;
    
    /**
     * Último mensaje mostrado.
     */
    private String ultimoMensaje;
    
    /**
     * Nombre del oponente.
     */
    private String nombreOponente;
    
    /**
     * Etiqueta que muestra el último mensaje relacionado con el estado del juego.
     */
    private JLabel lblUltimoMensaje;
    
    /**
     * Temporizador que controla el tiempo restante para cada turno.
     */
    private Timer temporizador;
    
    /**
     * Tiempo restante del turno en segundos.
     */
    private int tiempoRestante = 10;
    
    /**
     * Botón para rendirse durante el juego.
     */
    private JButton btnRendirse;
    
    /**
     * El presentador asociado a la vista de juego.
     */
    private PresentadorJuego presentador;
    
    /**
     * Indica si el juego ha terminado.
     */
    private boolean juegoTerminado = false;
    
    /**
     * Indica si el jugador es el ganador.
     */
    private boolean esGanador = false;

    /**
     * Constructor de la clase VistaJuego.
     * Inicializa el presentador y los componentes de la vista.
     *
     * @param panelJuego El panel de juego principal donde se agregarán los componentes.
     */
    public VistaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorJuego(this);
        crearComponentes();
        accionesComponentes();
    }

    /**
     * Crea los componentes necesarios para la vista del juego, como los tableros, las unidades y las etiquetas de estado.
     */
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
            panelJuego.agregarComponente(lblTurno, 0, 570, labelWidth, labelHeight);
        }

        lblTemporizador = new JLabel("Tiempo restante: 10", SwingConstants.CENTER);
        lblTemporizador.setFont(UtilesVista.FUENTE_SUBTITULO);
        lblTemporizador.setForeground(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        
        if (!panelJuego.isAncestorOf(lblTemporizador)) {
            panelJuego.agregarComponente(lblTemporizador, 0, 600, labelWidth, labelHeight);
        }
        
        tableroEnemigo.getPresentador().setAtaqueListener(presentador);

        btnRendirse = UtilesVista.crearBoton("Rendirse");
        
        panelJuego.revalidate();
        panelJuego.repaint();
    }

    /**
     * Define las acciones para los componentes de la vista, como el botón de rendición.
     */
    @Override
    public void accionesComponentes() {

        btnRendirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(panelJuego, "¿Seguro que desea rendirse?", "Confirmar rendición", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    presentador.enviarRendicion();
                }
            }
        });

    }

    /**
     * Dibuja la vista del juego en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        // Mostrar la superposición y el mensaje si el juego ha terminado
        if (juegoTerminado) {
            // quitar componentes existentes
            quitarComponentes();
            // Crear una superposición semitransparente
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(0, 0, 0, 210)); // Negro con transparencia
            g2d.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

            // Configurar el texto
            String mensaje = esGanador ? "¡VICTORIA!" : "DERROTA";
            
            g2d.setFont(UtilesVista.FUENTE_RESULTADO);
            
            if (esGanador) {
                g2d.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
            } else {
                g2d.setColor(UtilesVista.COLOR_UNIDAD_DESTRUIDA);
            }

            // Medir el ancho del mensaje para centrarlo
            FontMetrics fm = g2d.getFontMetrics();
            int mensajeAncho = fm.stringWidth(mensaje);
            int x = (Juego.GAME_ANCHO - mensajeAncho) / 2;
            int y = Juego.GAME_ALTO / 2;

            // Dibujar el mensaje
            g2d.drawString(mensaje, x, y);

            g2d.dispose();
        } else {

            g.setColor(UtilesVista.COLOR_FONDO);
            g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

            if (!panelJuego.isAncestorOf(btnRendirse)) {
                panelJuego.agregarComponente(btnRendirse, (Juego.GAME_ANCHO - 200) / 2, 660, 200, 30);
            }
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

    }

    /**
     * Actualiza la interfaz de juego según el turno actual.
     *
     * @param esMiTurno Indica si es el turno del jugador actual.
     */
    @Override
    public void actualizarInterfazTurno(boolean esMiTurno) {
        this.esMiTurno = esMiTurno;
        if (esMiTurno) {
            tableroEnemigo.habilitarInteraccion(true);
            lblTurno.setText("Es tu turno");
            iniciarTemporizador(); // Iniciar el temporizador
        } else {
            tableroEnemigo.habilitarInteraccion(false);
            lblTurno.setText("Es el turno del oponente");
            detenerTemporizador(); // Detener el temporizador
        }
    }
    
    /**
     * Inicia el temporizador del turno.
     */
    private void iniciarTemporizador() {
        tiempoRestante = 10; // Reiniciar el tiempo
        lblTemporizador.setText("Tiempo restante: " + tiempoRestante);

        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--;
                lblTemporizador.setText("Tiempo restante: " + tiempoRestante);
                if (tiempoRestante <= 0) {
                    temporizador.stop();
                    enviarAtaqueVacio();
                }
            }
        });
        temporizador.start();
    }

    /**
     * Detiene el temporizador del turno.
     */
    @Override
    public void detenerTemporizador() {
        if (temporizador != null && temporizador.isRunning()) {
            temporizador.stop();
        }
    }
    
    /**
     * Envia un ataque luego de que se termino el tiempo sin ataque
     */
    private void enviarAtaqueVacio() {
        presentador.enviarAtaqueVacio();
        bloquearInteraccion();
        lblTurno.setText("Se acabó tu tiempo. Turno del oponente");
    }

    /**
     * Actualiza el tablero del enemigo tras un ataque.
     *
     * @param x La coordenada X del ataque.
     * @param y La coordenada Y del ataque.
     * @param impacto Indica si el ataque fue un impacto.
     */
    @Override
    public void actualizarTableroEnemigo(int x, int y, boolean impacto) {
        tableroEnemigo.actualizarCasilla(x, y, impacto);
    }

    /**
     * Actualiza el estado de la flota del enemigo tras recibir un ataque.
     *
     * @param numeroNave El número de la nave atacada.
     * @param vidaNave La vida restante de la nave.
     */
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

    /**
     * Actualiza el tablero del jugador tras recibir un ataque.
     *
     * @param x La coordenada X del ataque recibido.
     * @param y La coordenada Y del ataque recibido.
     * @param impacto Indica si el ataque fue un impacto.
     */
    @Override
    public void actualizarTableroJugador(int x, int y, boolean impacto) {
        tableroJugador.actualizarCasilla(x, y, impacto);
    }

    /**
     * Actualiza el estado de la flota del jugador tras recibir un ataque.
     *
     * @param numeroNave El número de la nave atacada.
     * @param vidaNave La vida restante de la nave.
     */
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

    /**
     * Muestra el último mensaje relacionado con el estado del juego.
     *
     * @param mensaje El mensaje a mostrar.
     */
    @Override
    public void mostrarUltimoMensaje(String mensaje) {
        this.ultimoMensaje = mensaje;
        lblUltimoMensaje.setText(mensaje);
        panelJuego.repaint();
        lblUltimoMensaje.repaint();
    }

    /**
     * Bloquea la interacción del jugador con el tablero y otros componentes.
     */
    @Override
    public void bloquearInteraccion() {
        tableroEnemigo.habilitarInteraccion(false);
        btnRendirse.setEnabled(false);
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje.
     *
     * @param mensaje El mensaje a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje);
    }

    /**
     * Finaliza el juego e indica si el jugador es el ganador.
     *
     * @param ganador El nombre del jugador que ganó la partida.
     */
    @Override
    public void finalizarJuego(String ganador) {
        bloquearInteraccion();
        juegoTerminado = true;
        esGanador = ModeloJugador.getInstance().getNombre().equals(ganador);
        panelJuego.repaint();
    }

    /**
     * Obtiene el tablero del jugador.
     *
     * @return El tablero del jugador.
     */
    @Override
    public VistaTablero getTableroJugador() {
        return tableroJugador;
    }

    /**
     * Obtiene el tablero del enemigo.
     *
     * @return El tablero del enemigo.
     */
    @Override
    public VistaTablero getTableroEnemigo() {
        return tableroEnemigo;
    }

    /**
     * Inicia las unidades graficas de las flotas de los jugadores
     */
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

    /**
     * Coloca las unidades graficas de las flotas de los jugadores en el panel
     */
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

    /**
     * Obtener si es el turno del jugador.
     *
     * @return true si es el turno del jugador, false en caso contrario
     */
    public boolean isEsMiTurno() {
        return esMiTurno;
    }

    /**
     * Establece el tablero del jugador.
     *
     * @param tableroJugador El nombre del oponente.
     */
    public void setTableroJugador(VistaTablero tableroJugador) {
        this.tableroJugador = tableroJugador;
    }

    /**
     * Obtener las unidades del jugador.
     *
     * @return lista con las unidades del jugador
     */
    public List<VistaNave> getUnidadesJugador() {
        return unidadesJugador;
    }

    /**
     * Obtener las unidades del enemigo.
     *
     * @return lista con las unidades del enemigo
     */
    public List<VistaNave> getUnidadesEnemigo() {
        return unidadesEnemigo;
    }

    /**
     * Establece el nombre del oponente.
     *
     * @param nombreOponente El nombre del oponente.
     */
    @Override
    public void setNombreOponente(String nombreOponente) {
        this.nombreOponente = nombreOponente;
    }

    /**
     * Quita los componentes de la vista del juego del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(tableroJugador);
        panelJuego.quitarComponente(tableroEnemigo);
        for (VistaNave vistaNave : unidadesJugador) {
            panelJuego.quitarComponente(vistaNave);
        }
        for (VistaNave vistaNave : unidadesEnemigo) {
            panelJuego.quitarComponente(vistaNave);
        }
        panelJuego.quitarComponente(lblTurno);
        panelJuego.quitarComponente(lblTemporizador);
        panelJuego.quitarComponente(lblUltimoMensaje);
        panelJuego.quitarComponente(btnRendirse);
    
    }

    /**
     * Obtiene el presentador asociado a la vista de juego.
     *
     * @return El presentador del juego.
     */
    public PresentadorJuego getPresentador() {
        return this.presentador;
    }

    /**
     * Finaliza el juego debido a una rendición, indicando si el jugador es el ganador.
     *
     * @param ganador El nombre del jugador que ganó la partida.
     */
    @Override
    public void finalizarJuegoPorRendicion(String ganador) {
        bloquearInteraccion();
        juegoTerminado = true;
        esGanador = ModeloJugador.getInstance().getNombre().equals(ganador);
        panelJuego.repaint();
    }

}
