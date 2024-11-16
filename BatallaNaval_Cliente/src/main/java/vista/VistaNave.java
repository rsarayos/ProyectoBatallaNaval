package vista;

import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author alex_
 */
public class VistaNave extends JLabel{
    
    private final int numNave;
    private final int vidaMaxima;
    private int vida;

    public VistaNave(int numNave, int vidaMaxima) {
        this.numNave = numNave;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        
    }

    public int getNumNave() {
        return numNave;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.vida == vidaMaxima) {
            g.setColor(UtilesVista.COLOR_UNIDAD_SIN_DANO);
        } else if (this.vida < vidaMaxima && this.vida > 0) {
            g.setColor(UtilesVista.COLOR_UNIDAD_DANADA);
        } else if (this.vida == 0) {
            g.setColor(UtilesVista.COLOR_UNIDAD_DESTRUIDA);
        }
        g.fillRect(0, 0, getWidth(), getHeight());

    }

}
