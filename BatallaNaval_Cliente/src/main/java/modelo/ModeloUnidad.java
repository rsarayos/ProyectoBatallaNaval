package modelo;

/**
 *
 * @author alex_
 */
public class ModeloUnidad {
    
    private final int numNave;
    private Orientacion orientacion;
    private final int tamaño;

    public ModeloUnidad(int numNave, Orientacion orientacion, int tamaño) {
        this.numNave = numNave;
        this.orientacion = orientacion;
        this.tamaño = tamaño;
    }

    public int getNumNave() {
        return numNave;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
}
