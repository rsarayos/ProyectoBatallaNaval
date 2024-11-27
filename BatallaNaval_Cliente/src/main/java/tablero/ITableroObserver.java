package tablero;

/**
 * Interfaz para los observadores del tablero. Define el método que se llama cuando el tablero es actualizado.
 *
 * @author alex_
 */
public interface ITableroObserver {
    
    /**
     * Método que se ejecuta cuando el tablero es actualizado.
     */
    void onTableroUpdated();
}
