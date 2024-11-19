package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class ActualizarEstadoListoComando implements IComando{

    private EstadoSalaEspera estado;

    public ActualizarEstadoListoComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleActualizarEstadoListo(mensaje);
    }
    
}
