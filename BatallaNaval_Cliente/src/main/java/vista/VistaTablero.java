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

    public boolean colocarNave(VistaNave nave) {

        VistaCelda primeraCelda = nave.getCeldasOcupadas().iterator().next();
        int filaInicial = primeraCelda.getFila();
        int columnaInicial = primeraCelda.getColumna();

        // Orientacion pendiente
        boolean horizontal = true;

        // Verificar si hay espacio para la nave
        for (VistaCelda celda : nave.getCeldasOcupadas()) {
            int fila = filaInicial + (horizontal ? 0 : celda.getFila() - primeraCelda.getFila());
            int columna = columnaInicial + (horizontal ? celda.getColumna() - primeraCelda.getColumna() : 0);

            if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10 || celdas[fila][columna].getNave() != null) {
                return false; // No hay espacio
            }
        }

        // Colocar la nave en el tablero
        for (VistaCelda celda : nave.getCeldasOcupadas()) {
            int fila = filaInicial + (horizontal ? 0 : celda.getFila() - primeraCelda.getFila());
            int columna = columnaInicial + (horizontal ? celda.getColumna() - primeraCelda.getColumna() : 0);
            celdas[fila][columna].setNave(nave);
        }

        return true;

    }

}
