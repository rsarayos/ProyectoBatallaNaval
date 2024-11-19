package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 * Clase que implementa el comando para iniciar la organización desde el estado de sala de espera.
 *
 * @author alex_
 */
public class IniciarOrganizarComando implements IComando {

    /**
     * Estado de la sala de espera desde el cual se iniciará la organización.
     */
    private EstadoSalaEspera estado;

    /**
     * Constructor que inicializa el comando con el estado de la sala de espera especificado.
     *
     * @param estado el estado de la sala de espera desde el cual se iniciará la organización
     */
    public IniciarOrganizarComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    /**
     * Ejecuta el comando para iniciar la organización.
     */
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarOrganizar();
    }
    
}
