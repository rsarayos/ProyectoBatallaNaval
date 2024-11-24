package comunicacion;

import estados.EstadoJugar;
import java.util.Map;

/**
 *
 * @author alex_
 */
public class RendirseComando implements IComando {

    private EstadoJugar estado;

    public RendirseComando(EstadoJugar estado) {
        this.estado = estado;
    }

    @Override
    public void execute(Map<String, Object> mensaje) {
        estado.handleRendirseResponse(mensaje);
    }

}
