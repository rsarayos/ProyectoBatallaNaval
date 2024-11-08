package vista;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;
import presentador.Juego;

/**
 * Clase PanelJuego, que extiende JPanel y representa el panel de dibujo del
 * juego. Contiene métodos para manejar eventos de teclado y ratón, así como
 * para actualizar y dibujar el juego.
 *
 * @author alex_
 */
public class PanelJuego extends JPanel {
    
    // Referencia al objeto Juego
    private Juego juego;

    /**
     * Constructor de la clase PanelJuego que recibe una referencia al juego.
     * Inicializa los manejadores de eventos y configura el tamaño del panel.
     *
     * @param juego Referencia al objeto Juego.
     */
    public PanelJuego(Juego juego) {
        // Establece la referencia al juego
        this.juego = juego;
        // Configura el tamaño del panel
        setPanelSize();
        // Agrega un escuchador de eventos de teclado
//        addKeyListener(new Teclado(this));
        requestFocus();
        setLayout(null);
        // Agrega un escuchador de eventos de clic del mouse
//        addMouseListener(mouse);
        // Agrega un escuchador de eventos de movimiento del mouse
//        addMouseMotionListener(mouse);
    }

    /**
     * Método privado para establecer el tamaño del panel según las dimensiones
     * del juego.
     */
    private void setPanelSize() {
        // Dimensiones del panel según las del juego
        Dimension size = new Dimension(juego.GAME_ANCHO, juego.GAME_ALTO);
        // Establece el tamaño preferido del panel
        setPreferredSize(size);
        // Muestra las dimensiones en la consola
        System.out.println("Size: " + Juego.GAME_ANCHO + " : " + Juego.GAME_ALTO);
    }
    
    public void agregarComponente(JComponent componente, int x, int y, int ancho, int alto) {
        componente.setBounds(x, y, ancho, alto);
        add(componente);
        revalidate(); 
        repaint();
    }
    
    public void quitarComponente(JComponent componente) {
        remove(componente);
        revalidate();
        repaint();
    }

    /**
     * Método para actualizar el juego (a implementar según las necesidades).
     */
    public void updateJuego() {

    }

    /**
     * Método para dibujar en el panel. Llama al método de renderizado del juego
     * y pasa el contexto gráfico.
     *
     * @param g Objeto Graphics para dibujar.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Llama al método de renderizado del juego y pasa el contexto gráfico
        juego.renderizar(g);
    }

    /**
     * Método para obtener la referencia al juego.
     *
     * @return Referencia al objeto Juego.
     */
    public Juego getJuego() {
        return juego;
    }
    
}
