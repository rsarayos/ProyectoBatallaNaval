package estados;

import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import modelo.ModeloJugador;
import presentador.Juego;
import presentador.PresentadorBuscarPartida;
import vista.VistaBuscarPartida;

/**
 *
 * @author alex_
 */
public class EstadoBuscarPartida implements IEstadoJuego {
    
    private Juego juego;
    private VistaBuscarPartida vista;
    private PresentadorBuscarPartida presentador;

    public EstadoBuscarPartida(Juego juego) {
        this.juego = juego;
        this.vista = new VistaBuscarPartida(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
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
            case "UNIRSE_PARTIDA":
                handleUnirsePartidaResponse(mensaje);
                break;
            default:
                System.out.println("Acción desconocida en EstadoBuscarPartida: " + accion);
                break;
        }
    }

    private void handleUnirsePartidaResponse(Map<String, Object> mensaje) {
        if (mensaje.containsKey("error")) {
            String error = (String) mensaje.get("error");
            // Mostrar el mensaje de error en la vista actual
            vista.mostrarMensaje(error);
        } else {
            String idJugador = (String) mensaje.get("id");
            String codigoAcceso = (String) mensaje.get("codigo_acceso");
            List<String> nombresJugadores = (List<String>) mensaje.get("nombres_jugadores");

            // Guardar el id del jugador en ModeloJugador
            ModeloJugador jugador = ModeloJugador.getInstance();
            jugador.setId(idJugador);

            // Limpiar la vista actual
            vista.quitarComponentes();

            // Cambiar al estado de sala de espera, pasando los datos necesarios
            juego.cambiarEstado(new EstadoSalaEspera(juego, codigoAcceso, nombresJugadores));
        }
    }

}
