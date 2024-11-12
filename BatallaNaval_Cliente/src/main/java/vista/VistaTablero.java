package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import modelo.MUbicacionUnidad;
import modelo.ModeloCasilla;
import presentador.PresentadorTablero;

/**
 *
 * @author alex_
 */
public class VistaTablero extends JPanel {

    private PresentadorTablero presentador;
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

        // Añadir listeners de mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                presentador.onMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                presentador.onMouseReleased(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                presentador.onMouseDragged(e);
            }
        });
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

                // Dibujar fondo transparente para celdas no ocupadas
                if (casilla.getUnidadOcupante() == null) {
                    // No dibujamos nada 
                } else {
                    // Dibujar la nave
                    g.setColor(colorNave); // Usar el color seleccionado
                    g.fillRect(x, y, tamañoCelda, tamañoCelda);
                }

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

                // Dibujar bordes solo si la celda no está ocupada
                if (casilla.getUnidadOcupante() == null) {
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, tamañoCelda, tamañoCelda);
                }
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

}
