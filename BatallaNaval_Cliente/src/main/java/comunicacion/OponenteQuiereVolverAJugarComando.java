package comunicacion;

import estados.EstadoEstadisticas;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class OponenteQuiereVolverAJugarComando implements IComando {

    private EstadoEstadisticas estado;

    public OponenteQuiereVolverAJugarComando(EstadoEstadisticas estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.manejarOponenteQuiereVolverAJugar(mensaje);
    }
    
}
