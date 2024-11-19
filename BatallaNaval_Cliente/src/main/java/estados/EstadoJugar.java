package estados;

import comunicacion.AtacarComando;
import comunicacion.IComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import presentador.Juego;
import presentador.PresentadorJuego;
import vista.VistaJuego;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public class EstadoJugar implements IEstadoJuego {

    private Juego juego;
    private VistaJuego vista;
    private PresentadorJuego presentador;
    private Map<String, IComando> comandos;

    public EstadoJugar(Juego juego, VistaTablero tableroJugador, boolean tuTurno, String nombreOponente) {
        this.juego = juego;
        this.vista = new VistaJuego(juego.getPanel());
        this.presentador = vista.getPresentador();

        // variables del juego
        vista.setTableroJugador(tableroJugador);
        presentador.inicializarJuego(nombreOponente, tuTurno);
        inicializarComandos();

    }

    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("ATACAR", new AtacarComando(this));
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

    public void handleAtacarResponse(Map<String, Object> mensaje) {
        presentador.manejarAtaqueResponse(mensaje);

    }

}
