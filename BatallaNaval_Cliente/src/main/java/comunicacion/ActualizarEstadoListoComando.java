package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Clase que implementa el comando para actualizar el estado de "listo" en la sala de espera.
 *
 * @author alex_
 */
public class ActualizarEstadoListoComando implements IComando{

    /**
     * Estado de la sala de espera que se actualizará.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera que será actualizado
     */
    public ActualizarEstadoListoComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para actualizar el estado de "listo" en la sala de espera.
     *
     * @param mensaje un mapa que contiene los datos necesarios para actualizar el estado
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleActualizarEstadoListo(mensaje);
    }
    
}
