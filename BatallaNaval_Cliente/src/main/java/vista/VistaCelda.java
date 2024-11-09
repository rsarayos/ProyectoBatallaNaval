package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaCelda extends JPanel {

    private int fila, columna;
    private VistaNave nave;
    private boolean isHighlighted = false;
    private VistaTablero tablero;

    public VistaCelda(int fila, int columna, VistaTablero tablero) {
        this.fila = fila;
        this.columna = columna;
        this.tablero = tablero;         
    }
    
    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
        // Repaint para actualizar el color
        repaint(); 
    }

    public void setNave(VistaNave nave) {
        this.nave = nave;
        repaint();
    }

    public VistaNave getNave() {
        return nave;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Cambiar el color del borde si la celda está resaltada
        if (isHighlighted) {
            // Color para resaltar
            g.setColor(Color.YELLOW); 
        } else {
            // Color normal
            g.setColor(Color.BLACK); 
        }
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
        // Dibujar la nave solo si existe
        if (nave != null) {
            Dimension tamañoCelda = tablero.getTamañoCelda();
            int celdaAncho = tamañoCelda.width;
            int celdaAlto = tamañoCelda.height;
            nave.paintComponent(g, 0, 0, celdaAncho, celdaAlto);
        }
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "VistaCelda{" + "fila=" + fila + ", columna=" + columna + '}';
    }
    
}
