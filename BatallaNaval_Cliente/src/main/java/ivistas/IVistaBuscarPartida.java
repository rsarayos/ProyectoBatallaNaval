package ivistas;

import presentador.PresentadorBuscarPartida;

/**
 * Interfaz que define los métodos para la vista de búsqueda de partida del juego.
 *
 * @author alex_
 */
public interface IVistaBuscarPartida {

    /**
     * Muestra un mensaje en la interfaz de búsqueda de partida.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Obtiene el código de acceso ingresado en la vista de búsqueda de partida.
     *
     * @return el código de acceso
     */
    String obtenerCodigoAcceso();

    /**
     * Navega a la vista de sala de espera.
     */
    void navegarASalaDeEspera();

    /**
     * Navega al menú principal.
     */
    void navegarAMenu();
    
    /**
     * Obtiene el presentador asociado a la vista de búsqueda de partida.
     *
     * @return el presentador de la vista
     */
    PresentadorBuscarPartida getPresentador();

}
