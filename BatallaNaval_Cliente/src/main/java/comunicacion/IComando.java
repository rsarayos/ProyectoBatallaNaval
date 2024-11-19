package comunicacion;

import java.util.Map;

/**
 *
 * @author alex_
 */
public interface IComando {
    void execute(Map<String, Object> mensaje);
}
