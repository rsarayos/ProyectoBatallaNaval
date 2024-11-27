package ivistas;

import presentador.PresentadorSalaEspera;

/**
 * Interfaz que define los métodos para la vista de sala de espera del juego.
 *
 * @author alex_
 */
public interface IVistaSalaEspera {

    /**
     * Muestra el código de acceso para la partida en la interfaz de sala de espera.
     *
     * @param codigoAcceso el código de acceso de la partida
     */
    void mostrarCodigoAcceso(String codigoAcceso);

    /**
     * Agrega o actualiza el estado de un jugador en la lista de jugadores.
     *
     * @param nombreJugador el nombre del jugador
     * @param listo indica si el jugador está listo
     */
    void agregarOActualizarJugador(String nombreJugador, boolean listo);

    /**
     * Limpia la lista de jugadores en la interfaz.
     */
    void limpiarListaJugadores();

    /**
     * Bloquea el botón de continuar en la interfaz de sala de espera.
     */
    void bloquearBotonContinuar();

    /**
     * Muestra un mensaje en la interfaz de sala de espera.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Navega al menú principal.
     */
    void navegarAMenu();

    /**
     * Navega a la vista de organización de naves.
     */
    void navegarAOrganizar();

    /**
     * Verifica si el jugador está listo.
     *
     * @return true si el jugador está listo, false en caso contrario
     */
    boolean isEstoyListo();

    /**
     * Establece si el jugador está listo.
     *
     * @param listo true si el jugador está listo, false en caso contrario
     */
    void setEstoyListo(boolean listo);

    /**
     * Obtiene el presentador asociado a la vista de sala de espera.
     *
     * @return el presentador de la vista de sala de espera
     */
    PresentadorSalaEspera getPresentador();
    
}
