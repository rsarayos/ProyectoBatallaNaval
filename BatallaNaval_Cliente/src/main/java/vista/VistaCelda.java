package vista;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaCelda extends JPanel {

    private int fila, columna;
    private VistaNave nave;

    public void setNave(VistaNave nave) {
        this.nave = nave;
        repaint();
    }

    public VistaNave getNave() {
        return nave;
    }

    public VistaCelda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1); 
        if (nave != null) {
            nave.paintComponent(g, 0, 0, getWidth(), getHeight());
        }
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
