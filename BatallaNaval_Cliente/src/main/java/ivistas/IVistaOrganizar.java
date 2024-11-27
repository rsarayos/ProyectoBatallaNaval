package ivistas;

import java.awt.Color;
import vista.VistaTablero;

/**
 * Interfaz que define los métodos para la vista de organización de naves del juego.
 *
 * @author alex_
 */
public interface IVistaOrganizar {

    /**
     * Muestra un mensaje indicando que un jugador está esperando.
     *
     * @param nombreJugador el nombre del jugador que está esperando
     */
    void mostrarMensajeJugadorEsperando(String nombreJugador);

    /**
     * Obtiene la vista del tablero del jugador.
     *
     * @return la vista del tablero del jugador
     */
    VistaTablero getTablero();

    /**
     * Muestra un mensaje en la interfaz de organización.
     *
     * @param mensaje el mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Navega a la vista de juego.
     */
    void navegarAJugar();

    /**
     * Bloquea la interacción del jugador con la interfaz de organización.
     */
    void bloquearInterfaz();

    /**
     * Actualiza el color de los paneles laterales en la interfaz.
     *
     * @param nuevoColorNave el nuevo color de las naves
     */
    void actualizarColorPanelesLaterales(Color nuevoColorNave);

}
