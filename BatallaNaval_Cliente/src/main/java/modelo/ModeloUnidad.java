package modelo;

/**
 * Clase que representa una unidad en el modelo del juego.
 *
 * @author alex_
 */
public class ModeloUnidad {
    
    /**
     * Nombre de la unidad.
     */
    private String nombre;
    
    /**
     * Número de identificación de la nave.
     */
    private final int numNave;
    
    /**
     * Orientación de la unidad (horizontal o vertical).
     */
    private Orientacion orientacion;
    
    /**
     * Tamaño de la unidad.
     */
    private final int tamaño;    

    /**
     * Constructor que inicializa la unidad con su número, nombre, orientación y tamaño.
     *
     * @param numNave el número de la nave
     * @param nombre el nombre de la unidad
     * @param orientacion la orientación de la unidad
     * @param tamaño el tamaño de la unidad
     */
    public ModeloUnidad(int numNave, String nombre, Orientacion orientacion, int tamaño) {
        this.numNave = numNave;
        this.nombre = nombre;
        this.orientacion = orientacion;
        this.tamaño = tamaño;
    }

    /**
     * Obtiene el número de la nave.
     *
     * @return el número de la nave
     */
    public int getNumNave() {
        return numNave;
    }

    /**
     * Establece la orientación de la unidad.
     *
     * @param orientacion la orientación de la unidad
     */
    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    /**
     * Obtiene la orientación de la unidad.
     *
     * @return la orientación de la unidad
     */
    public Orientacion getOrientacion() {
        return orientacion;
    }
    
    /**
     * Obtiene el tamaño de la unidad.
     *
     * @return el tamaño de la unidad
     */
    public int getTamaño() {
        return tamaño;
    }
    
}
