package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 * Clase que implementa el comando para iniciar el juego desde el estado de organización.
 *
 * @author alex_
 */
public class IniciarJuegoComando implements IComando {

    /**
     * Estado de organización desde el cual se iniciará el juego.
     */
    private EstadoOrganizar estado;

    /**
     * Constructor que inicializa el comando con el estado de organización especificado.
     *
     * @param estado el estado de organización desde el cual se iniciará el juego
     */
    public IniciarJuegoComando(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para iniciar el juego.
     *
     * @param mensaje un mapa que contiene los datos necesarios para iniciar el juego
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarJuego(mensaje);
    }
    
}
