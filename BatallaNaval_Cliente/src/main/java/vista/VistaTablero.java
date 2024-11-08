package vista;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaTablero extends JPanel {

    private VistaCelda[][] celdas = new VistaCelda[10][10];

    public VistaTablero() {
        setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                celdas[i][j] = new VistaCelda(i, j);
                add(celdas[i][j]);
            }
        }

    }

    public boolean colocarNave(VistaNave nave, int filaInicial, int columnaInicial, boolean horizontal) {
        // lógica para verificar si hay espacio y colocar la nave (preliminar)
        for (int i = 0; i < nave.getUbicacion().length; i++) {
            int fila = filaInicial + (horizontal ? 0 : i);
            int columna = columnaInicial + (horizontal ? i : 0);
            if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10 || celdas[fila][columna].getNave() != null) {
                return false; // No hay espacio
            }
            celdas[fila][columna].setNave(nave);
            System.out.println("Se coloco nave");
        }
        return true;
    }

}
