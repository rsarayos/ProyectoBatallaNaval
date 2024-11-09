package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaNave extends JPanel {

    private Set<VistaCelda> celdasOcupadas;
    private boolean direccion;
    
    public VistaNave(Set<VistaCelda> celdasOcupadas) {
        this.celdasOcupadas = celdasOcupadas;
    }
    
    protected void paintComponent(Graphics g, int x, int y, int width, int height) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height); 
    }

    public Set<VistaCelda> getCeldasOcupadas() {
        return celdasOcupadas;
    }

    
    
}
