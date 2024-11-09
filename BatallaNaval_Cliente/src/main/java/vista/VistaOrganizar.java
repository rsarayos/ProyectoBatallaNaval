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

        // Naves de 4 unidades
        Set<VistaCelda> celdasNave1 = new HashSet<>();
        celdasNave1.add(new VistaCelda(2, 4, tablero));
        celdasNave1.add(new VistaCelda(2, 5, tablero));
        celdasNave1.add(new VistaCelda(2, 6, tablero));
        celdasNave1.add(new VistaCelda(2, 7, tablero));
        VistaNave nave1 = new VistaNave(celdasNave1, true);
        tablero.colocarNave(nave1);

        Set<VistaCelda> celdasNave2 = new HashSet<>();
        celdasNave2.add(new VistaCelda(5, 1, tablero));
        celdasNave2.add(new VistaCelda(6, 1, tablero));
        celdasNave2.add(new VistaCelda(7, 1, tablero));
        celdasNave2.add(new VistaCelda(8, 1, tablero));
        VistaNave nave2 = new VistaNave(celdasNave2, true);
        tablero.colocarNave(nave2);

// Naves de 3 unidades
        Set<VistaCelda> celdasNave3 = new HashSet<>();
        celdasNave3.add(new VistaCelda(3, 3, tablero));
        celdasNave3.add(new VistaCelda(4, 3, tablero));
        celdasNave3.add(new VistaCelda(5, 3, tablero));
        VistaNave nave3 = new VistaNave(celdasNave3, false);
        tablero.colocarNave(nave3);

        Set<VistaCelda> celdasNave4 = new HashSet<>();
        celdasNave4.add(new VistaCelda(7, 5, tablero));
        celdasNave4.add(new VistaCelda(7, 6, tablero));
        celdasNave4.add(new VistaCelda(7, 7, tablero));
        VistaNave nave4 = new VistaNave(celdasNave4, true);
        tablero.colocarNave(nave4);

// Naves de 2 unidades
        Set<VistaCelda> celdasNave5 = new HashSet<>();
        celdasNave5.add(new VistaCelda(0, 0, tablero));
        celdasNave5.add(new VistaCelda(1, 0, tablero));
        VistaNave nave5 = new VistaNave(celdasNave5, false);
        tablero.colocarNave(nave5);

        Set<VistaCelda> celdasNave6 = new HashSet<>();
        celdasNave6.add(new VistaCelda(9, 8, tablero));
        celdasNave6.add(new VistaCelda(9, 9, tablero));
        VistaNave nave6 = new VistaNave(celdasNave6, true);
        tablero.colocarNave(nave6);

// Naves de 1 unidad
        Set<VistaCelda> celdasNave7 = new HashSet<>();
        celdasNave7.add(new VistaCelda(3, 9, tablero));
        VistaNave nave7 = new VistaNave(celdasNave7, true);
        tablero.colocarNave(nave7);

        Set<VistaCelda> celdasNave8 = new HashSet<>();
        celdasNave8.add(new VistaCelda(6, 4, tablero));
        VistaNave nave8 = new VistaNave(celdasNave8, true);
        tablero.colocarNave(nave8);

        Set<VistaCelda> celdasNave9 = new HashSet<>();
        celdasNave9.add(new VistaCelda(8, 9, tablero));
        VistaNave nave9 = new VistaNave(celdasNave9, true);
        tablero.colocarNave(nave9);
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
