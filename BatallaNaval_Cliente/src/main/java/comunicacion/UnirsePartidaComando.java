package comunicacion;

import estados.EstadoBuscarPartida;
import java.util.Map;

/**
 * Clase que implementa el comando para unirse a una partida desde el estado de búsqueda de partida.
 *
 * @author alex_
 */
public class UnirsePartidaComando implements IComando {

    /**
     * Estado de búsqueda de partida donde se realiza la unión a una partida.
     */
    private EstadoBuscarPartida estado;

    /**
     * Constructor que inicializa el comando con el estado de búsqueda de partida especificado.
     *
     * @param estado el estado de búsqueda de partida donde se realiza la unión a una partida
     */
    public UnirsePartidaComando(EstadoBuscarPartida estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar la respuesta de unión a la partida.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la unión a la partida
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleUnirsePartidaResponse(mensaje);
    }
    
}
