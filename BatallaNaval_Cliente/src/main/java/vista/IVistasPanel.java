package vista;

import java.awt.Graphics;

/**
 * Interfaz para especificar las acciones que realizará el dibujado y actualización de los distintos estados del juego.
 *
 * @author Raul Alejandro Sauceda Rayos
 */
public interface IVistasPanel {
  
    /**
     * Dibuja los elementos gráficos en el contexto proporcionado.
     *
     * @param g el contexto gráfico donde se dibujarán los elementos
     */
    public void dibujar(Graphics g);
    
    /**
     * Define las acciones que realizarán los componentes interactivos.
     */
    public void accionesComponentes();
    
    /**
     * Crea e inicializa los componentes gráficos del panel.
     */
    public void crearComponentes();
    
    /**
     * Quita todos los componentes gráficos del panel.
     */
    public void quitarComponentes();
    
}
