package estados;

import java.awt.Graphics;
import java.util.Map;

/**
 *
 * @author alex_
 */
public interface IEstadoJuego {

    void salir();

    void renderizar(Graphics g); // Renderizado del estado

    void handleMessage(Map<String, Object> mensaje);

}
