package modelo;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex_
 */
public class MUbicacionUnidad {
    
    private ModeloUnidad unidad;
    private Set<ModeloCasilla> ubicacion;

    public MUbicacionUnidad(ModeloUnidad unidad, Set<ModeloCasilla> ubicacion) {
        this.unidad = unidad;
        this.ubicacion = ubicacion;
    }

    public ModeloUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(ModeloUnidad unidad) {
        this.unidad = unidad;
    }

    public Set<ModeloCasilla> getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Set<ModeloCasilla> ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}
