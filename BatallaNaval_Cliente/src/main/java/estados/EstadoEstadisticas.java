package estados;

import comunicacion.ClientConnection;
import comunicacion.IComando;
import comunicacion.IniciarPartidaComando;
import comunicacion.OponenteNoQuiereVolverAJugarComando;
import comunicacion.OponenteQuiereVolverAJugarComando;
import comunicacion.OponenteSalioComando;
import comunicacion.VolverAJugarComando;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import presentador.Juego;
import presentador.PresentadorEstadisticas;
import vista.VistaEstadisticas;

/**
 *
 * @author alex_
 */
public class EstadoEstadisticas implements IEstadoJuego {

    /**
     * Referencia al juego principal.
     */
    private Juego juego;

    /**
     * Vista que representa la interfaz de juego.
     */
    private VistaEstadisticas vista;

    /**
     * Presentador asociado a la vista de juego.
     */
    private PresentadorEstadisticas presentador;

    /**
     * Mapa que contiene los comandos disponibles en el estado de juego.
     */
    private Map<String, IComando> comandos;

    /**
     * Mapa que contiene las estadisticas de los jugadores.
     */
    private Map<String, Object> estadisticas;

    public EstadoEstadisticas(Juego juego, Map<String, Object> estadisticas, String ganador, String tiempoPartida) {
        this.juego = juego;
        this.estadisticas = estadisticas;
        this.vista = new VistaEstadisticas(juego.getPanel(), estadisticas, ganador, tiempoPartida, juego);
        inicializarComandos();
    }
    
    private void inicializarComandos() {
        comandos = new HashMap<>();
        comandos.put("OPONENTE_QUIERE_VOLVER_A_JUGAR", new OponenteQuiereVolverAJugarComando(this));
        comandos.put("OPONENTE_NO_QUIERE_VOLVER_A_JUGAR", new OponenteNoQuiereVolverAJugarComando(this));
        comandos.put("INICIAR_PARTIDA", new IniciarPartidaComando(this));
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
        if (accion != null && comandos.containsKey(accion)) {
            comandos.get(accion).execute(mensaje);
        } else {
            System.out.println("Acción no reconocida en EstadoEstadisticas: " + accion);
        }
    }

    public void manejarVolverAJugar(Map<String, Object> mensaje) {
        boolean oponenteQuiereJugar = (Boolean) mensaje.get("oponenteQuiereJugar");
        if (oponenteQuiereJugar) {
            // Ambos quieren jugar, iniciar nueva partida
            JOptionPane.showMessageDialog(null, "El oponente quiere volver a jugar.");
            juego.cambiarEstado(new EstadoOrganizar(juego));
        } else {
            // El oponente no quiere jugar, mostrar mensaje
            JOptionPane.showMessageDialog(null, "El oponente no quiere volver a jugar. Regresando al menú.");
            juego.cambiarEstado(new EstadoMenu(juego));
        }
    }

    public void manejarIniciarPartida(Map<String, Object> mensaje) {
        // Ambos jugadores están listos, iniciar nueva partida
        JOptionPane.showMessageDialog(null, "Ambos jugadores han aceptado volver a jugar. Iniciando nueva partida.");
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }

    public void manejarOponenteSalio(Map<String, Object> mensaje) {
        JOptionPane.showMessageDialog(null, "El oponente ha salido del juego. Regresando al menú.");
        juego.cambiarEstado(new EstadoMenu(juego));
    }
    
    public void manejarOponenteQuiereVolverAJugar(Map<String, Object> mensaje) {
        String textoMensaje = (String) mensaje.get("mensaje");
        int respuesta = JOptionPane.showConfirmDialog(null, textoMensaje, "Volver a Jugar", JOptionPane.YES_NO_OPTION);

        boolean acepta = (respuesta == JOptionPane.YES_OPTION);

        // Enviar la respuesta al servidor
        Map<String, Object> respuestaMensaje = new HashMap<>();
        respuestaMensaje.put("accion", "RESPUESTA_VOLVER_A_JUGAR");
        respuestaMensaje.put("acepta", acepta);

        ClientConnection.getInstance().sendMessage(respuestaMensaje);

        if (!acepta) {
            // El jugador declinó, regresar al menú
            JOptionPane.showMessageDialog(null, "Has decidido no volver a jugar. Regresando al menú.");
            juego.cambiarEstado(new EstadoMenu(juego));
        } else {
            // Esperar a que el servidor confirme si el oponente también quiere jugar
            JOptionPane.showMessageDialog(null, "Has aceptado volver a jugar. Esperando confirmación del oponente.");
        }
    }
    
    public void manejarOponenteNoQuiereVolverAJugar(Map<String, Object> mensaje) {
        String textoMensaje = (String) mensaje.get("mensaje");
        JOptionPane.showMessageDialog(null, textoMensaje, "Volver a Jugar", JOptionPane.INFORMATION_MESSAGE);
        // Regresar al menú
        juego.cambiarEstado(new EstadoMenu(juego));
    }

}
