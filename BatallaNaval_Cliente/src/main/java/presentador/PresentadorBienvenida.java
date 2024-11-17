package presentador;

import ivistas.IVistaBienvenida;
import modelo.ModeloJugador;
import vista.EstadosJuego;

/**
 *
 * @author alex_
 */
public class PresentadorBienvenida {

    private IVistaBienvenida vista;
    private ModeloJugador modeloJugador;

    public PresentadorBienvenida(IVistaBienvenida vista) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
    }

    public void iniciarJuego() {
        String nombre = vista.obtenerNombreJugador();
        if (nombre.isBlank()) {
            vista.mostrarMensajeError("El nombre no puede estar vacío");
            return;
        }
        if (!validarNombre(nombre)) {
            vista.mostrarMensajeError("El nombre no tiene el formato adecuado");
            return;
        }
        modeloJugador.setNombre(nombre);
        vista.navegarAlMenu();
        
    }
    
    public void avanzarAMenu() {
        EstadosJuego.estado = EstadosJuego.MENU;
    }

    private boolean validarNombre(String nombre) {
        return nombre.matches("^[a-zA-Z0-9]{1,15}$");
    }

}
