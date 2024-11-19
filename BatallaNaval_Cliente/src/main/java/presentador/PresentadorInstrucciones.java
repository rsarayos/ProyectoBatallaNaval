package presentador;

import estados.EstadoMenu;
import ivistas.IVistaInstrucciones;
import vista.EstadosJuego;

/**
 *
 * @author alex_
 */
public class PresentadorInstrucciones {
    
    private IVistaInstrucciones vista;
    private Juego juego;

    public PresentadorInstrucciones(IVistaInstrucciones vista, Juego juego) {
        this.vista = vista;
        this.juego = juego;
    }
    
    public void regresarAMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }
    
}
