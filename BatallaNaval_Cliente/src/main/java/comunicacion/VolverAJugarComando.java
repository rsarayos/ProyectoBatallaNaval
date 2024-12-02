package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class VolverAJugarComando implements IComando {
    
    private EstadoEstadisticas estado;

    public VolverAJugarComando(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarVolverAJugar(mensaje);
    }
    
}
