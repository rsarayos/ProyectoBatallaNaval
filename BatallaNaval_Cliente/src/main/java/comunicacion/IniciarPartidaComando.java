package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class IniciarPartidaComando implements IComando {
    
    private EstadoEstadisticas estado;

    public IniciarPartidaComando(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarIniciarPartida(mensaje);
    }
    
}
