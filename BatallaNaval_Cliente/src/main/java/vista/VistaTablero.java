package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import modelo.MUbicacionUnidad;
import modelo.ModeloCasilla;
import presentador.PresentadorTablero;
import tablero.ModoEnemigoStrategy;
import tablero.ModoJugadorStrategy;
import tablero.ModoOrganizarStrategy;
import tablero.ModoTableroStrategy;

/**
 *
 * @author alex_
 */
public class VistaTablero extends JPanel {

    private PresentadorTablero presentador;
    
    // MODO DEL TABLERO
    private ModoTablero modo;
    private ModoTableroStrategy estrategiaActual;
    
    // Listeners para el modo organizar
    private MouseListener mouseListenerOrganizar;
    private MouseMotionListener mouseMotionListenerOrganizar;

    // Listeners para el modo enemigo
    private MouseListener mouseListenerEnemigo;
    // interaccion para tablero enemigo
    private boolean interaccionHabilitada = true; 
    
    private Dimension tamañoCelda;
    private BufferedImage fondo;
    private boolean isDragging;
    private MUbicacionUnidad unidadSeleccionada;
    private Color colorNave = UtilesVista.BARCO_NEGRO;
    
    public VistaTablero() {
        this.presentador = new PresentadorTablero(this);
        setPreferredSize(new Dimension(300, 300)); // Tamaño del tablero
        tamañoCelda = new Dimension(30, 30); // Tamaño de cada celda
        
        cargarImagenes();
        
        inicializarListeners();

    }

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

    public Dimension getTamañoCelda() {
        return tamañoCelda;
    }

    public void actualizarVista() {
        repaint();
    }

    public PresentadorTablero getPresentador() {
        return presentador;
    }

    public void cargarImagenes() {
        this.fondo = UtilesVista.cargarImagen(UtilesVista.FONDO_TABLERO);
    }

    public boolean isIsDragging() {
        return isDragging;
    }

    public void setIsDragging(boolean isDragging) {
        this.isDragging = isDragging;
    }

    public MUbicacionUnidad getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(MUbicacionUnidad unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public void setColorNave(Color colorNave) {
        this.colorNave = colorNave;
        repaint(); // Redibujar el tablero con el nuevo color
    }

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

    // Añadir un único listener que delegue en la estrategia actual
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
            // Actualizar la vista
            repaint();
        }
    }

    void habilitarInteraccion(boolean habilitar) {
        this.interaccionHabilitada = habilitar;
        if (habilitar) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    void actualizarCasilla(int fila, int columna, boolean impacto) {
        ModeloCasilla casilla = presentador.getModeloTablero().getCasilla(fila, columna);
        if (casilla != null) {
            casilla.setAtacado(true);
            casilla.setImpacto(impacto);
            repaint();
        }
    }

    public boolean isInteraccionHabilitada() {
        return interaccionHabilitada;
    }

}
