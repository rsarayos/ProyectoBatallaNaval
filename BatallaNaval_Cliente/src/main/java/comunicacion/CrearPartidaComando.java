package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Clase que implementa el comando para crear una nueva partida.
 *
 * @author alex_
 */
public class CrearPartidaComando implements IComando {

    /**
     * Estado de la sala de espera donde se creará la partida.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera donde se creará la partida
     */
    public CrearPartidaComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar la respuesta de creación de partida.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la creación de la partida
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleCrearPartidaResponse(mensaje);
    }
    
}
