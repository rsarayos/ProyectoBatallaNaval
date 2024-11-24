package ivistas;

import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public interface IVistaJuego {

    void actualizarInterfazTurno(boolean esMiTurno);

    void actualizarTableroEnemigo(int x, int y, boolean impacto);

    void actualizarEstadoFlotaEnemigo(int numeroNave, int vidaNave);

    void actualizarTableroJugador(int x, int y, boolean impacto);

    void actualizarEstadoFlotaJugador(int numeroNave, int vidaNave);

    void mostrarUltimoMensaje(String mensaje);

    void bloquearInteraccion();

    void mostrarMensaje(String mensaje);

    void finalizarJuego(String ganador);

    VistaTablero getTableroJugador();

    VistaTablero getTableroEnemigo();

    public void setNombreOponente(String nombreOponente);
    
    public void detenerTemporizador();
    
    void finalizarJuegoPorRendicion(String ganador);

}
