package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import modelo.ModeloCasilla;
import presentador.PresentadorTablero;

/**
 *
 * @author alex_
 */
public class VistaTablero extends JPanel {

    private PresentadorTablero presentador;
    private Dimension tamañoCelda;

    public VistaTablero() {
        this.presentador = new PresentadorTablero(this);
        setPreferredSize(new Dimension(300, 300)); // Tamaño del tablero
        tamañoCelda = new Dimension(30, 30); // Tamaño de cada celda

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
        int tamañoCelda = getTamañoCelda().width;

        ModeloCasilla[][] casillas = presentador.getModeloTablero().getCasillas();

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                ModeloCasilla casilla = casillas[i][j];
                int x = j * tamañoCelda;
                int y = i * tamañoCelda;

                // Seleccionar color según el estado de la casilla
                if (casilla.isHighlighted()) {
                    g.setColor(Color.YELLOW); // Color de resaltado
                } else if (casilla.getUnidadOcupante() != null) {
                    g.setColor(Color.BLUE); // Color de la nave
                } else if (casilla.esAdyacentePorOtraNave(null)) {
                    // Si hay alguna nave que considera esta casilla adyacente
                    g.setColor(Color.LIGHT_GRAY); // Color de adyacencia
                } else {
                    g.setColor(Color.WHITE); // Color de fondo
                }
                g.fillRect(x, y, tamañoCelda, tamañoCelda);

                // Dibujar bordes
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

}
