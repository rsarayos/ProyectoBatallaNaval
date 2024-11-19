package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class NuevoJugadorComando implements IComando {
    
    private EstadoSalaEspera estado;

    public NuevoJugadorComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleNuevoJugador(mensaje);
    }
    
}
