package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaOrganizar implements EstadoJuego {

    private PanelJuego panelJuego;
    private VistaTablero tablero;
    private JComboBox<String> colorSelector;

    public VistaOrganizar(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.tablero = new VistaTablero();
        this.colorSelector = new JComboBox();
        
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Organizar naves", (Juego.GAME_ANCHO / 3), 50);

        if (!panelJuego.isAncestorOf(tablero)) {
            panelJuego.agregarComponente(tablero, 50, 50, 300, 300);
        }
        if (!panelJuego.isAncestorOf(colorSelector)) {
            panelJuego.agregarComponente(colorSelector, 50, 360, 300, 50);
        }

    }

    @Override
    public void actualizar() {

    }

}
