package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Clase que implementa el comando para manejar el evento cuando todos los jugadores están listos en la sala de espera.
 *
 * @author alex_
 */
public class TodosListosComando implements IComando {

    /**
     * Estado de la sala de espera donde todos los jugadores están listos.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera donde todos los jugadores están listos
     */
    public TodosListosComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar el estado cuando todos los jugadores están listos.
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleTodosListos();
    }
    
} 
