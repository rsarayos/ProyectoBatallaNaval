package modelo;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex_
 */
public class MUbicacionUnidad {
    
    private ModeloUnidad unidad;
    private Set<ModeloCasilla> casillasOcupadas;

    public MUbicacionUnidad(ModeloUnidad unidad, Set<ModeloCasilla> casillasOcupadas) {
        this.unidad = unidad;
        this.casillasOcupadas = casillasOcupadas;
    }

    public ModeloUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(ModeloUnidad unidad) {
        this.unidad = unidad;
    }

    public Set<ModeloCasilla> getCasillasOcupadas() {
        return casillasOcupadas;
    }

    public void setCasillasOcupadas(Set<ModeloCasilla> casillasOcupadas) {
        this.casillasOcupadas = casillasOcupadas;
    }
    
}
