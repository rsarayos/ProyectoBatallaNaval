package ivistas;

import java.awt.Color;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public interface IVistaOrganizar {
    
    void mostrarMensajeJugadorEsperando(String nombreJugador);
    VistaTablero getTablero();
    void mostrarMensaje(String mensaje);
    void navegarAJugar();
    void bloquearInterfaz();
    void actualizarColorPanelesLaterales(Color nuevoColorNave);
    
}
