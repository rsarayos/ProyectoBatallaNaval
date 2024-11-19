package presentador;

import estados.EstadoMenu;
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
    private Juego juego;

    public PresentadorBienvenida(IVistaBienvenida vista, Juego juego) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.juego = juego;
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
        avanzarAMenu();
        
    }
    
    public void avanzarAMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    private boolean validarNombre(String nombre) {
        return nombre.matches("^[a-zA-Z0-9]{1,15}$");
    }

}
