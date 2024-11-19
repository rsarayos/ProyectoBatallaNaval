package comunicacion;

import java.util.Map;

/**
 * Interfaz que representa un comando que se puede ejecutar con un mensaje especificado.
 *
 * @author alex_
 */
public interface IComando {
    
    /**
     * Ejecuta el comando con los datos proporcionados en el mensaje.
     *
     * @param mensaje un mapa que contiene los datos necesarios para ejecutar el comando
     */
    void execute(Map<String, Object> mensaje);

}
