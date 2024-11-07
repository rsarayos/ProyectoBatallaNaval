package vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

/**
 * Clase VentanaJuego, que extiende JFrame y representa la ventana del juego.
 * Configura la apariencia y comportamiento inicial de la ventana, incluyendo la
 * adición del panel de juego.
 * 
 * @author Raul Alejandro Sauceda Rayos
 */
public class VentanaJuego extends JFrame {
    
    /**
     * Constructor de la clase VentanaJuego que recibe un PanelJuego como
     * argumento. Configura la apariencia y comportamiento inicial de la
     * ventana.
     *
     * @param panel PanelJuego a agregar a la ventana.
     */
    public VentanaJuego(PanelJuego panel) {
        // Configuración inicial de la ventana
        // Define el comportamiento al cerrar la ventana
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // Establece el título de la ventana
        this.setTitle("Batalla Naval");
        // Agrega el panel del juego a la ventana
        this.add(panel);
        // Evita que la ventana se pueda redimensionar
        this.setResizable(false);
        // Ajusta automáticamente el tamaño de la ventana al contenido
        this.pack();
        // Centra la ventana en la pantalla
        this.setLocationRelativeTo(null);
        // Hace visible la ventana
        this.setVisible(true);
        // Agrega un escuchador para el enfoque de la ventana
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                // Llama al método en el juego cuando se pierde el enfoque de la ventana
//                panel.getJuego().windowFocusLost();
            }
        });
        {

        }
    }
    
}
