package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 * Clase que implementa el comando para realizar un ataque en el estado de juego.
 *
 * @author alex_
 */
public class AtacarComando implements IComando {

    /**
     * Estado del juego en el que se realizará el ataque.
     */
    private EstadoJugar estado;

    /**
     * Constructor que inicializa el comando con el estado del juego especificado.
     *
     * @param estado el estado del juego en el que se realizará el ataque
     */
    public AtacarComando(EstadoJugar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para manejar la respuesta del ataque.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar el ataque
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleAtacarResponse(mensaje);
    }
    
}
