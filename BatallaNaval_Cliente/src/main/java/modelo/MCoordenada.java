package modelo;

/**
 * Clase que representa una coordenada en el tablero del juego.
 *
 * @author alex_
 */
public class MCoordenada {
    
    /**
     * Coordenada x en el tablero.
     */
    private int x;
    
    /**
     * Coordenada y en el tablero.
     */
    private int y;

    /**
     * Constructor que inicializa las coordenadas x e y.
     *
     * @param x la coordenada x
     * @param y la coordenada y
     */
    public MCoordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la coordenada x.
     *
     * @return la coordenada x
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada y.
     *
     * @return la coordenada y
     */
    public int getY() {
        return y;
    }
    
}
