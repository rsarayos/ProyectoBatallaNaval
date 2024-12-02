package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class OponenteNoQuiereVolverAJugarComando implements IComando {
    
    private EstadoEstadisticas estado;

    public OponenteNoQuiereVolverAJugarComando(EstadoEstadisticas estado) {
        this.estado = estado;
    }

    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarOponenteNoQuiereVolverAJugar(mensaje);
    }
    
}
