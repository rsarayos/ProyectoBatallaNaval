package vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaNave extends JPanel {

    private VistaCelda ubicacion[];
    
    public VistaNave(VistaCelda ubicacion[]) {
        this.ubicacion = ubicacion;
    }
    
    protected void paintComponent(Graphics g, int x, int y, int width, int height) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height); 
    }

    public VistaCelda[] getUbicacion() {
        return ubicacion;
    }
    
}
