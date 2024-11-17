package presentador;

import ivistas.IVistaInstrucciones;
import vista.EstadosJuego;

/**
 *
 * @author alex_
 */
public class PresentadorInstrucciones {
    
    private IVistaInstrucciones vista;

    public PresentadorInstrucciones(IVistaInstrucciones vista) {
        this.vista = vista;
    }

    public void regresarAlMenu() {
        vista.navegarAlMenu();
    }
    
    public void regresarAMenu() {
        EstadosJuego.estado = EstadosJuego.MENU;
    }
    
}
