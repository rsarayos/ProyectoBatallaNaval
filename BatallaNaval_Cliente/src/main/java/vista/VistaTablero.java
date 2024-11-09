package vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaTablero extends JPanel {

    private VistaCelda[][] celdas = new VistaCelda[10][10];
    private boolean isDragging = false;
    private VistaCelda celdaSeleccionada;
    private VistaCelda celdaOriginal;

    public VistaTablero() {
        
        setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                VistaCelda celda = new VistaCelda(i, j, this);
                celdas[i][j] = celda;
                add(celda);
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePresionado(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseSoltado(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseArrastrado(e);
            }
        });
    }

    private void mousePresionado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        VistaCelda celda = celdas[fila][columna];

        if (celda.getNave() != null) {
            isDragging = true;
            celdaOriginal = celda;
            celdaSeleccionada = celda;
            System.out.println("Se selecciona la nave en celda " + celdaSeleccionada.toString());
            celda.setHighlighted(true);
        }
    }

    private void mouseSoltado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        VistaCelda celdaDestino = celdas[fila][columna];

        if (isDragging && celdaSeleccionada != null) {
            if (verificarNave(celdaSeleccionada, celdaDestino)) {
                // se elimina la nave anterior
                celdas[celdaOriginal.getFila()][celdaOriginal.getColumna()].setNave(null);
                System.out.println("Se borro la nave en celda " + celdaOriginal.toString());
                // se crea la nueva nave
                Set<VistaCelda> celdasNave = new HashSet<>();
                celdasNave.add(celdaDestino);
                VistaNave naveNueva = new VistaNave(celdasNave);
                colocarNave(naveNueva);
                
            }
            celdaSeleccionada.setHighlighted(false);
            isDragging = false;
            celdaSeleccionada = null;
            repaint();
        }
    }

    private void mouseArrastrado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        VistaCelda celda = celdas[fila][columna];

        if (isDragging && celdaSeleccionada != null) {
            celdaSeleccionada.setHighlighted(false);
            celda.setHighlighted(true);
            celdaSeleccionada = celda;
            repaint();
        }
    }

    public boolean colocarNave(VistaNave nave) {

        VistaCelda primeraCelda = nave.getCeldasOcupadas().iterator().next();
        int filaInicial = primeraCelda.getFila();
        int columnaInicial = primeraCelda.getColumna();

        // Orientacion pendiente
        boolean horizontal = true;

        for (VistaCelda celda : nave.getCeldasOcupadas()) {
            int fila = filaInicial + (horizontal ? 0 : celda.getFila() - primeraCelda.getFila());
            int columna = columnaInicial + (horizontal ? celda.getColumna() - primeraCelda.getColumna() : 0);

            if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10 || celdas[fila][columna].getNave() != null) {
                return false; // No hay espacio
            }
        }

        for (VistaCelda celda : nave.getCeldasOcupadas()) {
            int fila = filaInicial + (horizontal ? 0 : celda.getFila() - primeraCelda.getFila());
            int columna = columnaInicial + (horizontal ? celda.getColumna() - primeraCelda.getColumna() : 0);
            celdas[fila][columna].setNave(nave);
        }

        return true;
    }

    private boolean verificarNave(VistaCelda celdaOrigen, VistaCelda celdaDestino) {
        if (celdaDestino.getNave() == null) {
            celdaDestino.setNave(celdaOrigen.getNave());
            return true;
        }
        return false;
    }

    public Dimension getTamañoCelda() {
        int celdaAncho = getWidth() / 10;
        int celdaAlto = getHeight() / 10;
        return new Dimension(celdaAncho, celdaAlto);
    }
}
