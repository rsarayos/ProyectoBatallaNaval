package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelo.MUbicacionUnidad;
import modelo.ModeloCasilla;
import presentador.PresentadorTablero;
import tablero.ITableroObserver;
import tablero.ModoEnemigoStrategy;
import tablero.ModoJugadorStrategy;
import tablero.ModoOrganizarStrategy;
import tablero.ModoTableroStrategy;

/**
 * Clase que representa la vista del tablero en el juego de Batalla Naval.
 * Esta clase extiende JPanel y funciona como un componente visual que muestra
 * el estado del tablero, incluyendo las naves, los ataques y la interacción del jugador.
 * Implementa ITableroObserver para responder a los cambios en el modelo del tablero.
 *
 * @author alex_
 */
public class VistaTablero extends JPanel implements ITableroObserver  {

    /**
     * Presentador encargado de gestionar la lógica asociada a la vista del tablero.
     */
    private PresentadorTablero presentador;
    
    /**
     * Modo actual del tablero (ORGANIZAR, JUGADOR, ENEMIGO).
     */
    private ModoTablero modo;
    
    /**
     * Estrategia de comportamiento actual según el modo del tablero.
     */
    private ModoTableroStrategy estrategiaActual;
    
    /**
     * Listener del mouse para el modo ORGANIZAR.
     */
    private MouseListener mouseListenerOrganizar;
    
    /**
     * Listener del movimiento del mouse para el modo ORGANIZAR.
     */
    private MouseMotionListener mouseMotionListenerOrganizar;

    /**
     * Listener del mouse para el modo ENEMIGO.
     */
    private MouseListener mouseListenerEnemigo;
    
    /**
     * Indica si la interacción con el tablero enemigo está habilitada.
     */
    private boolean interaccionHabilitada = true; 
    
    /**
     * Dimensiones de cada celda en el tablero.
     */
    private Dimension tamañoCelda;
    
    /**
     * Imagen de fondo del tablero.
     */
    private BufferedImage fondo;
    
    /**
     * Indica si se está arrastrando una nave actualmente.
     */
    private boolean isDragging;
    
    /**
     * Unidad seleccionada que se está moviendo en el tablero.
     */
    private MUbicacionUnidad unidadSeleccionada;
    
    /**
     * Color de las naves en el tablero.
     */
    private Color colorNave = UtilesVista.BARCO_NEGRO;
    
    /**
     * Constructor de la clase VistaTablero.
     * Inicializa el presentador, establece el tamaño del tablero y carga las imágenes y listeners.
     */
    public VistaTablero() {
        this.presentador = new PresentadorTablero(this);
        setPreferredSize(new Dimension(300, 300)); // Tamaño del tablero
        tamañoCelda = new Dimension(30, 30); // Tamaño de cada celda
        
        cargarImagenes();
        
        inicializarListeners();

    }

    /**
     * Sobrescribe el método paintComponent para dibujar el tablero y su contenido.
     *
     * @param g Objeto Graphics utilizado para dibujar el tablero.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar fondo de tablero
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        int tamañoCelda = getTamañoCelda().width;

        ModeloCasilla[][] casillas = presentador.getModeloTablero().getCasillas();

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                ModeloCasilla casilla = casillas[i][j];
                int x = j * tamañoCelda;
                int y = i * tamañoCelda;

                // **Mostrar las naves propias en los modos ORGANIZAR y JUGADOR**
                if (modo == ModoTablero.ORGANIZAR || modo == ModoTablero.JUGADOR) {
                    if (casilla.getUnidadOcupante() != null) {
                        // Dibujar la nave
                        g.setColor(colorNave); // Usar el color seleccionado
                        g.fillRect(x, y, tamañoCelda, tamañoCelda);
                    }
                }

                // **En el modo ENEMIGO, no mostramos las naves**
                // **Mostrar celdas adyacentes y resaltadas solo en el modo ORGANIZAR**
                if (modo == ModoTablero.ORGANIZAR) {
                    // Dibujar celdas adyacentes solo durante el arrastre
                    if (isDragging) {
                        // Si la celda es adyacente a otra nave (no a la unidad seleccionada)
                        if (casilla.esAdyacentePorOtraNave(unidadSeleccionada)) {
                            g.setColor(UtilesVista.COLOR_CELDAS_INVALIDAS);
                            g.fillRect(x, y, tamañoCelda, tamañoCelda);
                        }
                    }

                    // Dibujar resaltado amarillo para las celdas resaltadas
                    if (casilla.isHighlighted()) {
                        g.setColor(UtilesVista.COLOR_VISTA_PREVIEW);
                        g.fillRect(x, y, tamañoCelda, tamañoCelda);
                    }
                }

                // **Mostrar ataques e impactos en todos los modos**
                if (casilla.isAtacado()) {
                    if (casilla.isImpacto()) {
                        // Dibujar un círculo rojo para impacto
                        g.setColor(Color.RED);
                    } else {
                        // Dibujar un círculo blanco para ataque fallido
                        g.setColor(Color.WHITE);
                    }
                    g.fillOval(x + tamañoCelda / 4, y + tamañoCelda / 4, tamañoCelda / 2, tamañoCelda / 2);
                }

                // **Dibujar bordes de las celdas**
                g.setColor(Color.BLACK);
                g.drawRect(x, y, tamañoCelda, tamañoCelda);
            }
        }
    }

    /**
     * Obtiene el tamaño de cada celda en el tablero.
     *
     * @return Dimensiones de la celda.
     */
    public Dimension getTamañoCelda() {
        return tamañoCelda;
    }

    /**
     * Actualiza la vista del tablero.
     */
    public void actualizarVista() {
        repaint();
    }

    /**
     * Obtiene el presentador asociado al tablero.
     *
     * @return PresentadorTablero.
     */
    public PresentadorTablero getPresentador() {
        return presentador;
    }

    /**
     * Carga las imágenes utilizadas en el tablero, como el fondo.
     */
    public void cargarImagenes() {
        this.fondo = UtilesVista.cargarImagen(UtilesVista.FONDO_TABLERO);
    }

    /**
     * Indica si actualmente se está arrastrando una unidad.
     *
     * @return true si se está arrastrando, false en caso contrario.
     */
    public boolean isIsDragging() {
        return isDragging;
    }

    /**
     * Establece si se está arrastrando una unidad en el tablero.
     *
     * @param isDragging true si se está arrastrando, false en caso contrario.
     */
    public void setIsDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    /**
     * Obtiene la unidad seleccionada actualmente.
     *
     * @return Unidad seleccionada.
     */
    public MUbicacionUnidad getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    /**
     * Establece la unidad seleccionada actualmente.
     *
     * @param unidadSeleccionada Unidad a seleccionar.
     */
    public void setUnidadSeleccionada(MUbicacionUnidad unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    /**
     * Establece el color de las naves en el tablero.
     *
     * @param colorNave Color de las naves.
     */
    public void setColorNave(Color colorNave) {
        this.colorNave = colorNave;
        repaint(); // Redibujar el tablero con el nuevo color
    }

    /**
     * Establece el modo del tablero (ORGANIZAR, JUGADOR, ENEMIGO).
     *
     * @param modo Modo del tablero.
     */
    public void setModo(ModoTablero modo) {
        this.modo = modo;
        switch (modo) {
            case ORGANIZAR:
                estrategiaActual = new ModoOrganizarStrategy(this, presentador);
                break;
            case ENEMIGO:
                estrategiaActual = new ModoEnemigoStrategy(this, presentador);
                break;
            case JUGADOR:
                estrategiaActual = new ModoJugadorStrategy();
                break;
            default:
                estrategiaActual = null;
                break;
        }
    }

    /**
     * Inicializa los listeners del mouse para interactuar con el tablero.
     */
    private void inicializarListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (estrategiaActual != null) {
                    estrategiaActual.mousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (estrategiaActual != null) {
                    estrategiaActual.mouseReleased(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (estrategiaActual != null) {
                    estrategiaActual.mouseDragged(e);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    /**
     * Maneja el clic en el tablero enemigo y realiza el ataque correspondiente.
     *
     * @param e Evento del mouse.
     */
    public void manejarClickEnemigo(MouseEvent e) {
        if (!interaccionHabilitada) {
            return; // Si no esta en su turno
        }
        int fila = e.getY() / tamañoCelda.height;
        int columna = e.getX() / tamañoCelda.width;

        ModeloCasilla casilla = presentador.getModeloTablero().getCasilla(fila, columna);

        if (casilla != null && !casilla.isAtacado()) {
            // Marcar la casilla como atacada
            casilla.setAtacado(true);
            // Enviar el ataque al servidor
            presentador.enviarAtaque(fila, columna);
            
            if (presentador.getAtaqueListener() != null) {
                presentador.getAtaqueListener().enAtaqueRealizado();
            }
            // Actualizar la vista
            repaint();
        }
    }

    /**
     * Habilita o deshabilita la interacción con el tablero enemigo.
     *
     * @param habilitar true para habilitar la interacción, false para deshabilitarla.
     */
    void habilitarInteraccion(boolean habilitar) {
        this.interaccionHabilitada = habilitar;
        if (habilitar) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Actualiza una casilla específica en el tablero después de un ataque.
     *
     * @param fila Fila de la casilla.
     * @param columna Columna de la casilla.
     * @param impacto true si el ataque fue un impacto, false en caso contrario.
     */
    void actualizarCasilla(int fila, int columna, boolean impacto) {
        ModeloCasilla casilla = presentador.getModeloTablero().getCasilla(fila, columna);
        if (casilla != null) {
            casilla.setAtacado(true);
            casilla.setImpacto(impacto);
            repaint();
        }
    }

    /**
     * Indica si la interacción está habilitada para el tablero enemigo.
     *
     * @return true si la interacción está habilitada, false en caso contrario.
     */
    public boolean isInteraccionHabilitada() {
        return interaccionHabilitada;
    }

    /**
     * Método llamado cuando el tablero es actualizado, para redibujar la vista.
     */
    @Override
    public void onTableroUpdated() {
        SwingUtilities.invokeLater(() -> {
            repaint();
        });
    }

}
