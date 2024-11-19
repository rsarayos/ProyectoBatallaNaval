package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class TodosListosComando implements IComando {

    private EstadoSalaEspera estado;

    public TodosListosComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleTodosListos();
    }
    
} 
