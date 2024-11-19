package vista;

import java.awt.Graphics;

/**
 * Interface para especificar las acciones que realizara el dibujado y actualizacion de los distintos estados del juego
 *
 * @author Raul Alejandro Sauceda Rayos
 */
public interface IVistasPanel {
  
    public void dibujar(Graphics g);
    public void accionesComponentes();
    public void crearComponentes();
    public void quitarComponentes();
    
}
