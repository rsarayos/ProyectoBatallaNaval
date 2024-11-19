package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class AtacarComando implements IComando {

    private EstadoJugar estado;

    public AtacarComando(EstadoJugar estado) {
        this.estado = estado;
    }
    
    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleAtacarResponse(mensaje);
    }
    
}
