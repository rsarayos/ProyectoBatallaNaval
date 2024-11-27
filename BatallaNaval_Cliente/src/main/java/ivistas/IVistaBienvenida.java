package ivistas;

/**
 * Interfaz que define los métodos para la vista de bienvenida del juego.
 *
 * @author alex_
 */
public interface IVistaBienvenida {

    /**
     * Muestra un mensaje de error en la interfaz de bienvenida.
     *
     * @param mensaje el mensaje de error a mostrar
     */
    public void mostrarMensajeError(String mensaje);

    /**
     * Obtiene el nombre del jugador ingresado en la vista de bienvenida.
     *
     * @return el nombre del jugador
     */
    public String obtenerNombreJugador();

}
