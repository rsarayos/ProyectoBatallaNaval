package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Clase que implementa el comando para manejar la llegada de un nuevo jugador en la sala de espera.
 *
 * @author alex_
 */
public class NuevoJugadorComando implements IComando {
    
    /**
     * Estado de la sala de espera donde llega el nuevo jugador.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera donde llega el nuevo jugador
     */
    public NuevoJugadorComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para manejar la llegada de un nuevo jugador.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la llegada del nuevo jugador
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleNuevoJugador(mensaje);
    }
    
}
