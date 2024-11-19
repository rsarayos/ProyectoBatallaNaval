package estados;

import comunicacion.ActualizarEstadoListoComando;
import comunicacion.CrearPartidaComando;
import comunicacion.IComando;
import comunicacion.IniciarOrganizarComando;
import comunicacion.NuevoJugadorComando;
import comunicacion.TodosListosComando;
import java.awt.Graphics;
import java.util.HashMap;
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
    private Map<String, IComando> comandos;

    public EstadoSalaEspera(Juego juego) {
        this.juego = juego;
        this.vista = new VistaSalaEspera(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
        inicializarComandos();
    }
    
    // constructor cuando se une a la sala ya creada
    public EstadoSalaEspera(Juego juego, String codigoAcceso, List<String> nombresJugadores) {
        this.juego = juego;
        this.vista = new VistaSalaEspera(juego.getPanel(), juego);
        this.presentador = vista.getPresentador();
        this.vista.setCodigoAcceso(codigoAcceso);
        this.presentador.actualizarListaJugadores(nombresJugadores);
        inicializarComandos();
    }
    
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("CREAR_PARTIDA", new CrearPartidaComando(this));
        comandos.put("NUEVO_JUGADOR", new NuevoJugadorComando(this));
        comandos.put("ACTUALIZAR_ESTADO_LISTO", new ActualizarEstadoListoComando(this));
        comandos.put("TODOS_LISTOS", new TodosListosComando(this));
        comandos.put("INICIAR_ORGANIZAR", new IniciarOrganizarComando(this));
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

        IComando comando = comandos.get(accion);
        if (comando != null) {
            comando.execute(mensaje);
        } else {
            System.out.println("Acción desconocida en EstadoSalaEspera: " + accion);
        }
    }

    public void handleCrearPartidaResponse(Map<String, Object> mensaje) {
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

    public void handleNuevoJugador(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        vista.agregarJugador(nombreJugador);
    }

    public void handleActualizarEstadoListo(Map<String, Object> mensaje) {
        String nombreJugador = (String) mensaje.get("nombre_jugador");
        boolean listo = (Boolean) mensaje.get("listo");
        presentador.manejarActualizarEstadoListo(nombreJugador, listo);
    }

    public void handleTodosListos() {
        presentador.manejarTodosListos();
    }

    public void handleIniciarOrganizar() {
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }
    
}
