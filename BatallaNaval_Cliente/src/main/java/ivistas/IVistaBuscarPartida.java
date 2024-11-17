package ivistas;

import presentador.PresentadorBuscarPartida;

/**
 *
 * @author alex_
 */
public interface IVistaBuscarPartida {

    void mostrarMensaje(String mensaje);

    String obtenerCodigoAcceso();

    void navegarASalaDeEspera();

    void navegarAMenu();
    
    PresentadorBuscarPartida getPresentador();

}
