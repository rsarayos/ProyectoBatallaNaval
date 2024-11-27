package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 * Clase que implementa el comando para rendirse durante el estado de juego.
 *
 * @author alex_
 */
public class RendirseComando implements IComando {

    /**
     * Estado de juego en el que se realiza la rendición.
     */
    private EstadoJugar estado;

    /**
     * Constructor que inicializa el comando con el estado de juego especificado.
     *
     * @param estado el estado de juego donde se realiza la rendición
     */
    public RendirseComando(EstadoJugar estado) {
        this.estado = estado;
    }

    /**
     * Ejecuta el comando para manejar la rendición del jugador.
     *
     * @param mensaje un mapa que contiene los datos necesarios para manejar la rendición
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleRendirseResponse(mensaje);
    }

}
