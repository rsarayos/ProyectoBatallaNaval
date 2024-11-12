package modelo;

/**
 *
 * @author alex_
 */
public class ModeloJugador {
    
    private String nombre;
    private String id;
    
    private ModeloJugador() {
    }
    
    public static ModeloJugador getInstance() {
        return ModeloJugadorHolder.INSTANCE;
    }
    
    private static class ModeloJugadorHolder {

        private static final ModeloJugador INSTANCE = new ModeloJugador();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
