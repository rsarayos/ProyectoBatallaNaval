package comunicacion;

import estados.EstadoBuscarPartida;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class UnirsePartidaComando implements IComando {

    private EstadoBuscarPartida estado;

    public UnirsePartidaComando(EstadoBuscarPartida estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleUnirsePartidaResponse(mensaje);
    }
    
}
