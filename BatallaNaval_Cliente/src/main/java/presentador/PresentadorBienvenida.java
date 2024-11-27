package presentador;

import estados.EstadoMenu;
import ivistas.IVistaBienvenida;
import modelo.ModeloJugador;

/**
 * Clase que actúa como presentador de la vista de bienvenida del juego.
 *
 * @author alex_
 */
public class PresentadorBienvenida {

    /**
     * Vista de bienvenida.
     */
    private IVistaBienvenida vista;
    
    /**
     * Modelo del jugador.
     */
    private ModeloJugador modeloJugador;
    
    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Constructor que inicializa el presentador con la vista y el juego especificados.
     *
     * @param vista la vista de bienvenida
     * @param juego la referencia al juego principal
     */
    public PresentadorBienvenida(IVistaBienvenida vista, Juego juego) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.juego = juego;
    }

    /**
     * Inicia el juego validando el nombre del jugador e iniciando el menú principal.
     */
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
    
    /**
     * Cambia al estado del menú principal.
     */
    public void avanzarAMenu() {
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Valida si el nombre del jugador cumple con el formato adecuado.
     *
     * @param nombre el nombre del jugador
     * @return true si el nombre es válido, false en caso contrario
     */
    private boolean validarNombre(String nombre) {
        return nombre.matches("^[a-zA-Z0-9]{1,15}$");
    }

}
