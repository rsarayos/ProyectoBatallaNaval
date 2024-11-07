package vista;

import java.awt.Graphics;

/**
 * Interface para especificar las acciones que realizara el dibujado y actualizacion de los distintos estados del juego
 *
 * @author Raul Alejandro Sauceda Rayos
 */
public interface EstadoJuego {
  
    public void dibujar(Graphics g);
    public void actualizar();
    
}
