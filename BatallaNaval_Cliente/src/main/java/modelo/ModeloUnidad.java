package modelo;

/**
 *
 * @author alex_
 */
public class ModeloUnidad {
    
    private String nombre;
    private final int numNave;
    private Orientacion orientacion;
    private final int tamaño;    

    public ModeloUnidad(int numNave, String nombre, Orientacion orientacion, int tamaño) {
        this.numNave = numNave;
        this.nombre = nombre;
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
