package ivistas;

import vista.VistaTablero;

/**
 * Interfaz que define los métodos para la vista de juego donde los jugadores atacan.
 *
 * @author alex_
 */
public interface IVistaJuego {

    /**
     * Actualiza la interfaz para reflejar si es el turno del jugador.
     *
     * @param esMiTurno true si es el turno del jugador, false en caso contrario
     */
    void actualizarInterfazTurno(boolean esMiTurno);

    /**
     * Actualiza el tablero del enemigo en la interfaz con el resultado de un ataque.
     *
     * @param x la coordenada x del ataque
     * @param y la coordenada y del ataque
     * @param impacto true si el ataque fue un impacto, false en caso contrario
     */
    void actualizarTableroEnemigo(int x, int y, boolean impacto);

    /**
     * Actualiza el estado de la flota enemiga en la interfaz.
     *
     * @param numeroNave el número de la nave
     * @param vidaNave la vida restante de la nave
     */
    void actualizarEstadoFlotaEnemigo(int numeroNave, int vidaNave);

    /**
     * Actualiza el tablero del jugador en la interfaz con el resultado de un ataque.
     *
     * @param x la coordenada x del ataque
     * @param y la coordenada y del ataque
     * @param impacto true si el ataque fue un impacto, false en caso contrario
     */
    void actualizarTableroJugador(int x, int y, boolean impacto);

    /**
     * Actualiza el estado de la flota del jugador en la interfaz.
     *
     * @param numeroNave el número de la nave
     * @param vidaNave la vida restante de la nave
     */
    void actualizarEstadoFlotaJugador(int numeroNave, int vidaNave);

    /**
     * Muestra el último mensaje en la interfaz.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarUltimoMensaje(String mensaje);

    /**
     * Bloquea la interacción del jugador con la interfaz.
     */
    void bloquearInteraccion();

    /**
     * Muestra un mensaje en la interfaz de juego.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Finaliza el juego y muestra al ganador en la interfaz.
     *
     * @param ganador el nombre del ganador
     */
    void finalizarJuego(String ganador);

    /**
     * Obtiene el tablero del jugador.
     *
     * @return la vista del tablero del jugador
     */
    VistaTablero getTableroJugador();

    /**
     * Obtiene el tablero del enemigo.
     *
     * @return la vista del tablero del enemigo
     */
    VistaTablero getTableroEnemigo();

    /**
     * Establece el nombre del oponente en la interfaz.
     *
     * @param nombreOponente el nombre del oponente
     */
    public void setNombreOponente(String nombreOponente);
    
    /**
     * Detiene el temporizador del juego.
     */
    public void detenerTemporizador();
    
    /**
     * Finaliza el juego debido a la rendición de un jugador y muestra al ganador.
     *
     * @param ganador el nombre del ganador
     */
    void finalizarJuegoPorRendicion(String ganador);

}
