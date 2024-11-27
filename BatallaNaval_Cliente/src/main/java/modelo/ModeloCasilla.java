package modelo;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa una casilla en el tablero del juego.
 *
 * @author alex_
 */
public class ModeloCasilla {

    /**
     * Coordenada de la casilla en el tablero.
     */
    private final MCoordenada coordenada;
    
    /**
     * Conjunto de unidades adyacentes a la casilla.
     */
    private Set<MUbicacionUnidad> navesAdyacentes;
    
    /**
     * Unidad que ocupa la casilla.
     */
    private MUbicacionUnidad unidadOcupante;
    
    /**
     * Indicador de si la casilla está resaltada.
     */
    private boolean isHighlighted;
    
    /**
     * Indicador de si la casilla ha sido atacada.
     */
    private boolean atacado;
    
    /**
     * Indicador de si el ataque en la casilla fue un impacto.
     */
    private boolean impacto;

    /**
     * Constructor que inicializa la casilla con una coordenada.
     *
     * @param coordenada la coordenada de la casilla
     */
    public ModeloCasilla(MCoordenada coordenada) {
        this.coordenada = coordenada;
        this.navesAdyacentes = new HashSet<>();
        this.unidadOcupante = null;
        this.isHighlighted = false;
    }

    /**
     * Obtiene la coordenada de la casilla.
     *
     * @return la coordenada de la casilla
     */
    public MCoordenada getCoordenada() {
        return coordenada;
    }

    /**
     * Agrega una unidad a la lista de naves adyacentes a la casilla.
     *
     * @param nave la unidad a agregar como adyacente
     */
    public void agregarNaveAdyacente(MUbicacionUnidad nave) {
        navesAdyacentes.add(nave);
    }

    /**
     * Elimina una unidad de la lista de naves adyacentes a la casilla.
     *
     * @param nave la unidad a eliminar de la lista de adyacentes
     */
    public void eliminarNaveAdyacente(MUbicacionUnidad nave) {
        navesAdyacentes.remove(nave);
    }

    /**
     * Verifica si la casilla es adyacente a otra unidad distinta a la actual.
     *
     * @param naveActual la unidad actual
     * @return true si hay alguna otra unidad adyacente, false en caso contrario
     */
    public boolean esAdyacentePorOtraNave(MUbicacionUnidad naveActual) {
        // Retorna true si hay alguna nave adyacente que no sea la actual
        for (MUbicacionUnidad nave : navesAdyacentes) {
            if (!nave.equals(naveActual)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el conjunto de unidades adyacentes a la casilla.
     *
     * @return el conjunto de unidades adyacentes
     */
    public Set<MUbicacionUnidad> getNavesAdyacentes() {
        return navesAdyacentes;
    }

    /**
     * Obtiene la unidad que ocupa la casilla.
     *
     * @return la unidad que ocupa la casilla
     */
    public MUbicacionUnidad getUnidadOcupante() {
        return unidadOcupante;
    }

    /**
     * Establece la unidad que ocupa la casilla.
     *
     * @param unidadOcupante la unidad que ocupará la casilla
     */
    public void setUnidadOcupante(MUbicacionUnidad unidadOcupante) {
        this.unidadOcupante = unidadOcupante;
    }

    /**
     * Verifica si la casilla está resaltada.
     *
     * @return true si la casilla está resaltada, false en caso contrario
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Establece si la casilla debe estar resaltada.
     *
     * @param highlighted true para resaltar la casilla, false en caso contrario
     */
    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    /**
     * Verifica si la casilla ha sido atacada.
     *
     * @return true si la casilla ha sido atacada, false en caso contrario
     */
    public boolean isAtacado() {
        return atacado;
    }

    /**
     * Establece si la casilla ha sido atacada.
     *
     * @param atacado true si la casilla ha sido atacada, false en caso contrario
     */
    public void setAtacado(boolean atacado) {
        this.atacado = atacado;
    }

    /**
     * Verifica si el ataque en la casilla fue un impacto.
     *
     * @return true si el ataque fue un impacto, false en caso contrario
     */
    public boolean isImpacto() {
        return impacto;
    }

    /**
     * Establece si el ataque en la casilla fue un impacto.
     *
     * @param impacto true si el ataque fue un impacto, false en caso contrario
     */
    public void setImpacto(boolean impacto) {
        this.impacto = impacto;
    }

}
