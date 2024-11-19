package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 * Clase que implementa el comando para manejar la espera del jugador durante el estado de organización.
 *
 * @author alex_
 */
public class JugadorEsperandoComando implements IComando {

    /**
     * Estado de organización donde el jugador está esperando.
     */
    private EstadoOrganizar estado;

    /**
     * Constructor que inicializa el comando con el estado de organización especificado.
     *
     * @param estado el estado de organización donde el jugador está esperando
     */
    public JugadorEsperandoComando(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar el estado de espera del jugador.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la espera del jugador
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleJugadorEsperando(mensaje);
    }
    
}
