package ivistas;

import presentador.PresentadorSalaEspera;

/**
 *
 * @author alex_
 */
public interface IVistaSalaEspera {

    void mostrarCodigoAcceso(String codigoAcceso);

    void agregarOActualizarJugador(String nombreJugador, boolean listo);

    void limpiarListaJugadores();

    void bloquearBotonContinuar();

    void mostrarMensaje(String mensaje);

    void navegarAMenu();

    void navegarAOrganizar();

    boolean isEstoyListo();

    void setEstoyListo(boolean listo);

    PresentadorSalaEspera getPresentador();
    
}
