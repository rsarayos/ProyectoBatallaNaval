package modelo;

import java.util.Map;
import java.util.Set;

/**
 * Clase que representa la ubicación de una unidad en el tablero del juego.
 *
 * @author alex_
 */
public class MUbicacionUnidad {
    
    /**
     * Unidad del modelo que se está ubicando.
     */
    private ModeloUnidad unidad;
    
    /**
     * Conjunto de casillas ocupadas por la unidad.
     */
    private Set<ModeloCasilla> casillasOcupadas;

    /**
     * Constructor que inicializa la unidad y sus casillas ocupadas.
     *
     * @param unidad la unidad del modelo
     * @param casillasOcupadas el conjunto de casillas ocupadas por la unidad
     */
    public MUbicacionUnidad(ModeloUnidad unidad, Set<ModeloCasilla> casillasOcupadas) {
        this.unidad = unidad;
        this.casillasOcupadas = casillasOcupadas;
    }

    /**
     * Obtiene la unidad del modelo.
     *
     * @return la unidad del modelo
     */
    public ModeloUnidad getUnidad() {
        return unidad;
    }

    /**
     * Establece la unidad del modelo.
     *
     * @param unidad la unidad del modelo
     */
    public void setUnidad(ModeloUnidad unidad) {
        this.unidad = unidad;
    }

    /**
     * Obtiene el conjunto de casillas ocupadas por la unidad.
     *
     * @return el conjunto de casillas ocupadas
     */
    public Set<ModeloCasilla> getCasillasOcupadas() {
        return casillasOcupadas;
    }

    /**
     * Establece el conjunto de casillas ocupadas por la unidad.
     *
     * @param casillasOcupadas el conjunto de casillas ocupadas
     */
    public void setCasillasOcupadas(Set<ModeloCasilla> casillasOcupadas) {
        this.casillasOcupadas = casillasOcupadas;
    }
    
}
