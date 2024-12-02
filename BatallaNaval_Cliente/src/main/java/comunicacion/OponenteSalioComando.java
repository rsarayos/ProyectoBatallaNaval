package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class OponenteSalioComando implements IComando {
    
    private EstadoEstadisticas estado;

    public OponenteSalioComando(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarOponenteSalio(mensaje);
    }
    
}
