package vista;

import java.awt.Graphics;
import javax.swing.JLabel;

/**
 * Clase que representa la vista de una nave en el juego. Extiende JLabel para ser usada como un componente visual.
 * Esta clase muestra visualmente el estado de la nave (sin daño, dañada o destruida) según su vida actual.
 *
 * @author alex_
 */
public class VistaNave extends JLabel{
    
    /**
     * Número de identificación de la nave.
     */
    private final int numNave;
    
    /**
     * Vida máxima que puede tener la nave.
     */
    private final int vidaMaxima;
    
    /**
     * Vida actual de la nave.
     */
    private int vida;

    /**
     * Constructor de la clase VistaNave.
     *
     * @param numNave Número de identificación de la nave.
     * @param vidaMaxima Vida máxima de la nave.
     */
    public VistaNave(int numNave, int vidaMaxima) {
        this.numNave = numNave;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        
    }

    /**
     * Obtiene el número de identificación de la nave.
     *
     * @return Número de la nave.
     */
    public int getNumNave() {
        return numNave;
    }

    /**
     * Obtiene la vida actual de la nave.
     *
     * @return Vida actual de la nave.
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece la vida actual de la nave.
     *
     * @param vida Nueva vida de la nave.
     */
    public void setVida(int vida) {
        this.vida = vida;
    }
    
    /**
     * Sobrescribe el método paintComponent para dibujar la nave con un color específico según su estado.
     *
     * @param g Objeto Graphics utilizado para dibujar la nave.
     */
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
