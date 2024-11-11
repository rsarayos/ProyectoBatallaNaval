package modelo;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alex_
 */
public class ModeloTablero {

    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private ModeloCasilla[][] casillas;
    private Set<MUbicacionUnidad> unidades;

    public ModeloTablero() {

        casillas = new ModeloCasilla[FILAS][COLUMNAS];
        
        // se crean las casillas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ModeloCasilla celda = new ModeloCasilla(new MCoordenada(i, j));
                casillas[i][j] = celda;
            }
        }

        // se inicia la lista ubicaciones
        this.unidades = new HashSet();

    }

    public ModeloCasilla[][] getCasillas() {
        return casillas;
    }

    public Set<MUbicacionUnidad> getUnidades() {
        return unidades;
    }

    public ModeloCasilla getCasilla(int x, int y) {
        if (x >= 0 && x < FILAS && y >= 0 && y < COLUMNAS) {
            return casillas[x][y];
        }
        return null;
    }

}
