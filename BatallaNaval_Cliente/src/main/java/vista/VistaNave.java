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
    // true = horizontal, false = vertical
    private boolean direccion;
    
    public VistaNave(Set<VistaCelda> celdasOcupadas, boolean direccion) {
        this.celdasOcupadas = celdasOcupadas;
        this.direccion = direccion;
    }
    
    protected void paintComponent(Graphics g, int x, int y, int width, int height) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height); 
    }

    public void setCeldasOcupadas(Set<VistaCelda> celdasOcupadas) {
        this.celdasOcupadas = celdasOcupadas;
    }
    
    

    public Set<VistaCelda> getCeldasOcupadas() {
        return celdasOcupadas;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "VistaNave{" + "celdasOcupadas=" + celdasOcupadas + '}';
    } 
    
}
