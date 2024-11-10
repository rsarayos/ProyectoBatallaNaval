package modelo;

/**
 *
 * @author alex_
 */
public class ModeloUnidad {
    
    private final int numNave;
    private Orientacion orientacion;

    public ModeloUnidad(int numNave, Orientacion orientacion) {
        this.numNave = numNave;
        this.orientacion = orientacion;
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
    
}
