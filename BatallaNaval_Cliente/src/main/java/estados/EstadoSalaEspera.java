package estados;

import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import modelo.ModeloJugador;
import presentador.Juego;
import presentador.PresentadorSalaEspera;
import vista.VistaSalaEspera;

/**
 *
 * @author alex_
 */
public class EstadoSalaEspera implements IEstadoJuego {

    private Juego juego;
    private VistaSalaEspera vista;
    private PresentadorSalaEspera presentador;

    public EstadoSalaEspera(Juego juego) {
        this.juego = juego;
        this.vista = new VistaSalaEspera(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
    }
    
    public EstadoSalaEspera(Juego juego, String codigoAcceso, List<String> nombresJugadores) {
        this.juego = juego;
        this.vista = new VistaSalaEspera(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
        
        this.vista.setCodigoAcceso(codigoAcceso);
        this.presentador.actualizarListaJugadores(nombresJugadores);
    }
    
    @Override
    public void salir() {
        vista.quitarComponentes();
    }

    @Override
    public void renderizar(Graphics g) {
        vista.dibujar(g);
    }

    @Override
    public void handleMessage(Map<String, Object> mensaje) {
        String accion = (String) mensaje.get("accion");
        if (accion == null) {
            System.err.println("Mensaje sin 'accion': " + mensaje);
            return;
        }

        switch (accion) {
            case "CREAR_PARTIDA":
                handleCrearPartidaResponse(mensaje);
                break;
            case "NUEVO_JUGADOR":
                handleNuevoJugador(mensaje);
                break;
            case "ACTUALIZAR_ESTADO_LISTO":
                handleActualizarEstadoListo(mensaje);
                break;
            case "TODOS_LISTOS":
                handleTodosListos();
                break;
            case "INICIAR_ORGANIZAR":
                handleIniciarOrganizar();
                break;
            default:
                System.out.println("Acción desconocida en EstadoSalaEspera: " + accion);
                break;
        }
    }
    
    private void handleCrearPartidaResponse(Map<String, Object> mensaje) {
        String codigoAcceso = (String) mensaje.get("codigo_acceso");
        String idJugador = (String) mensaje.get("id");
        // Guardar el id del jugador en ModeloJugador
        ModeloJugador jugador = ModeloJugador.getInstance();
        jugador.setId(idJugador);
        // Configurar la vista de sala de espera
        vista.setCodigoAcceso(codigoAcceso);
        // Actualizar la lista de jugadores en la sala de espera
        vista.agregarJugador(jugador.getNombre());
    }

    private void handleNuevoJugador(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        vista.agregarJugador(nombreJugador);
    }

    private void handleActualizarEstadoListo(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        boolean listo = (Boolean) mensaje.get("listo");
        presentador.manejarActualizarEstadoListo(nombreJugador, listo);
    }

    private void handleTodosListos() {
        presentador.manejarTodosListos();
    }

    private void handleIniciarOrganizar() {
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }
    
}
