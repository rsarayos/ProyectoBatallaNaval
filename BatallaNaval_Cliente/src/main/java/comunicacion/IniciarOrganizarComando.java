package comunicacion;

import estados.EstadoSalaEspera;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class IniciarOrganizarComando implements IComando {

    private EstadoSalaEspera estado;

    public IniciarOrganizarComando(EstadoSalaEspera estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleIniciarOrganizar();
    }
    
}