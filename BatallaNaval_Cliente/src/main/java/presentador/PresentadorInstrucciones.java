package presentador;

import estados.EstadoMenu;
import ivistas.IVistaInstrucciones;

/**
 * Clase que actúa como presentador de la vista de instrucciones del juego, manejando la navegación hacia otros estados.
 *
 * @author alex_
 */
public class PresentadorInstrucciones {
    
    /**
     * Vista de instrucciones.
     */
    private IVistaInstrucciones vista;
    
    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el presentador con la vista y el juego especificados.
     *
     * @param vista la vista de instrucciones
     * @param juego la referencia al juego principal
     */
    public PresentadorInstrucciones(IVistaInstrucciones vista, Juego juego) {
        this.vista = vista;
        this.juego = juego;
    }
    
    /**
     * Regresa al menú principal.
     */
    public void regresarAMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }
    
}
