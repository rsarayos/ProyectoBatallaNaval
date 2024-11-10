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

    public ModeloCasilla getCasilla(int fila, int columna) {
        if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
            return casillas[fila][columna];
        }
        return null;
    }

    public boolean agregarUnidad(MUbicacionUnidad ubicacionUnidad) {
        // Validamos que la ubicación no esté ocupada ni en celdas adyacentes
        if (validarUbicacionLibre(ubicacionUnidad)) {
            this.unidades.add(ubicacionUnidad);
            marcarCasillasOcupadas(ubicacionUnidad);
            return true;
        }
        return false;
    }

    private boolean validarUbicacionLibre(MUbicacionUnidad ubicacionUnidad) {
        // Implementación que verifica que ninguna celda de `ubicacionUnidad`
        // esté ocupada ni adyacente a otras unidades.
        for (ModeloCasilla casilla : ubicacionUnidad.getUbicacion()) {
            if (casilla.isEsAdyacente() || casillaOcupada(casilla)) {
                return false;
            }
        }
        return true;
    }

    private boolean casillaOcupada(ModeloCasilla casilla) {
        return unidades.stream()
                .flatMap(unidad -> unidad.getUbicacion().stream())
                .anyMatch(c -> c.equals(casilla));
    }

    private void marcarCasillasOcupadas(MUbicacionUnidad ubicacionUnidad) {
        for (ModeloCasilla casilla : ubicacionUnidad.getUbicacion()) {
            Set<ModeloCasilla> adyacentes = obtenerCasillasAdyacentes(casilla);
            adyacentes.forEach(c -> c.setEsAdyacente(true));
        }
    }

    private Set<ModeloCasilla> obtenerCasillasAdyacentes(ModeloCasilla casilla) {
        Set<ModeloCasilla> adyacentes = new HashSet<>();
        int fila = casilla.getCoordenada().getX();
        int columna = casilla.getCoordenada().getY();

        int[] desplazamientos = {-1, 0, 1};
        for (int dx : desplazamientos) {
            for (int dy : desplazamientos) {
                if (!(dx == 0 && dy == 0)) {
                    int nuevaFila = fila + dx;
                    int nuevaColumna = columna + dy;
                    ModeloCasilla adyacente = getCasilla(nuevaFila, nuevaColumna);
                    if (adyacente != null) {
                        adyacentes.add(adyacente);
                    }
                }
            }
        }
        return adyacentes;
    }

    public Set<MUbicacionUnidad> getUnidades() {
        return unidades;
    }

}
