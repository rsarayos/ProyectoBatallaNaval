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
        celdasNave1.add(new VistaCelda(0, 2, tablero));
        celdasNave1.add(new VistaCelda(0, 3, tablero));
        celdasNave1.add(new VistaCelda(0, 4, tablero));
        celdasNave1.add(new VistaCelda(0, 5, tablero));
        VistaNave nave1 = new VistaNave(celdasNave1, true, 1);
        tablero.colocarNave(nave1);
        tablero.setNavesEnTablero(nave1);

        Set<VistaCelda> celdasNave2 = new HashSet<>();
        celdasNave2.add(new VistaCelda(4, 0, tablero));
        celdasNave2.add(new VistaCelda(5, 0, tablero));
        celdasNave2.add(new VistaCelda(6, 0, tablero));
        celdasNave2.add(new VistaCelda(7, 0, tablero));
        VistaNave nave2 = new VistaNave(celdasNave2, false, 2);
        tablero.colocarNave(nave2);
        tablero.setNavesEnTablero(nave2);

// Naves de 3 unidades
        Set<VistaCelda> celdasNave3 = new HashSet<>();
        celdasNave3.add(new VistaCelda(3, 4, tablero));
        celdasNave3.add(new VistaCelda(4, 4, tablero));
        celdasNave3.add(new VistaCelda(5, 4, tablero));
        VistaNave nave3 = new VistaNave(celdasNave3, false, 3);
        tablero.colocarNave(nave3);
        tablero.setNavesEnTablero(nave3);

        Set<VistaCelda> celdasNave4 = new HashSet<>();
        celdasNave4.add(new VistaCelda(8, 3, tablero));
        celdasNave4.add(new VistaCelda(8, 4, tablero));
        celdasNave4.add(new VistaCelda(8, 5, tablero));
        VistaNave nave4 = new VistaNave(celdasNave4, true, 4);
        tablero.colocarNave(nave4);
        tablero.setNavesEnTablero(nave4);

// Naves de 2 unidades
        Set<VistaCelda> celdasNave5 = new HashSet<>();
        celdasNave5.add(new VistaCelda(1, 7, tablero));
        celdasNave5.add(new VistaCelda(2, 7, tablero));
        VistaNave nave5 = new VistaNave(celdasNave5, false, 5);
        tablero.colocarNave(nave5);
        tablero.setNavesEnTablero(nave5);

        Set<VistaCelda> celdasNave6 = new HashSet<>();
        celdasNave6.add(new VistaCelda(9, 7, tablero));
        celdasNave6.add(new VistaCelda(9, 8, tablero));
        VistaNave nave6 = new VistaNave(celdasNave6, true, 6);
        tablero.colocarNave(nave6);
        tablero.setNavesEnTablero(nave6);

// Naves de 1 unidad
        Set<VistaCelda> celdasNave7 = new HashSet<>();
        celdasNave7.add(new VistaCelda(2, 2, tablero));
        VistaNave nave7 = new VistaNave(celdasNave7, true, 7);
        tablero.colocarNave(nave7);
        tablero.setNavesEnTablero(nave7);

        Set<VistaCelda> celdasNave8 = new HashSet<>();
        celdasNave8.add(new VistaCelda(5, 7, tablero));
        VistaNave nave8 = new VistaNave(celdasNave8, true, 8);
        tablero.colocarNave(nave8);
        tablero.setNavesEnTablero(nave8);

        Set<VistaCelda> celdasNave9 = new HashSet<>();
        celdasNave9.add(new VistaCelda(7, 9, tablero));
        VistaNave nave9 = new VistaNave(celdasNave9, true, 9);
        tablero.colocarNave(nave9);
        tablero.setNavesEnTablero(nave9);
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
