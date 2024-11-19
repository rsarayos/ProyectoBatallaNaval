package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class CrearPartidaComando implements IComando {

    private EstadoSalaEspera estado;

    public CrearPartidaComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleCrearPartidaResponse(mensaje);
    }
    
}
