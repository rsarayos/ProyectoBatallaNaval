package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class JugadorEsperandoComando implements IComando {

    private EstadoOrganizar estado;

    public JugadorEsperandoComando(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleJugadorEsperando(mensaje);
    }
    
}
