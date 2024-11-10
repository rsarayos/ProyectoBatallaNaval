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
    private int numNave;
    
    public VistaNave(Set<VistaCelda> celdasOcupadas, boolean direccion, int numNave) {
        this.celdasOcupadas = celdasOcupadas;
        this.direccion = direccion;
        this.numNave = numNave;
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

    public int getNumNave() {
        return numNave;
    }

    public void setNumNave(int numNave) {
        this.numNave = numNave;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.numNave;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VistaNave other = (VistaNave) obj;
        return this.numNave == other.numNave;
    }

    @Override
    public String toString() {
        return "VistaNave{" + numNave + '}';
    }
    
}
