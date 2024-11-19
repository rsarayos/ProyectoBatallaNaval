package comunicacion;

import estados.EstadoOrganizar;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class IniciarJuegoComando implements IComando {

    private EstadoOrganizar estado;

    public IniciarJuegoComando(EstadoOrganizar estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarJuego(mensaje);
    }
    
}
