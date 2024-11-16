package modelo;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alex_
 */
public class ModeloCasilla {

    private final MCoordenada coordenada;
    private Set<MUbicacionUnidad> navesAdyacentes;
    private MUbicacionUnidad unidadOcupante;
    private boolean isHighlighted;
    private boolean atacado;
    private boolean impacto;

    public ModeloCasilla(MCoordenada coordenada) {
        this.coordenada = coordenada;
        this.navesAdyacentes = new HashSet<>();
        this.unidadOcupante = null;
        this.isHighlighted = false;
    }

    public MCoordenada getCoordenada() {
        return coordenada;
    }

    // Métodos para gestionar naves adyacentes
    public void agregarNaveAdyacente(MUbicacionUnidad nave) {
        navesAdyacentes.add(nave);
    }

    public void eliminarNaveAdyacente(MUbicacionUnidad nave) {
        navesAdyacentes.remove(nave);
    }

    public boolean esAdyacentePorOtraNave(MUbicacionUnidad naveActual) {
        // Retorna true si hay alguna nave adyacente que no sea la actual
        for (MUbicacionUnidad nave : navesAdyacentes) {
            if (!nave.equals(naveActual)) {
                return true;
            }
        }
        return false;
    }

    public Set<MUbicacionUnidad> getNavesAdyacentes() {
        return navesAdyacentes;
    }

    public MUbicacionUnidad getUnidadOcupante() {
        return unidadOcupante;
    }

    public void setUnidadOcupante(MUbicacionUnidad unidadOcupante) {
        this.unidadOcupante = unidadOcupante;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    public boolean isAtacado() {
        return atacado;
    }

    public void setAtacado(boolean atacado) {
        this.atacado = atacado;
    }

    public boolean isImpacto() {
        return impacto;
    }

    public void setImpacto(boolean impacto) {
        this.impacto = impacto;
    }

}
