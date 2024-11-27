package presentador;

import comunicacion.ClientConnection;
import enums.ControlPartida;
import ivistas.IVistaJuego;
import java.util.HashMap;
import java.util.Map;
import modelo.ModeloJugador;

/**
 * Clase que actúa como presentador de la vista del juego, manejando la lógica de los ataques y actualizaciones.
 *
 * @author alex_
 */
public class PresentadorJuego implements AtaqueListener{

    /**
     * Vista del juego.
     */
    private IVistaJuego vista;
    
    /**
     * Modelo del jugador.
     */
    private ModeloJugador modeloJugador;
    
    /**
     * Conexión con el servidor.
     */
    private ClientConnection clientConnection;

    /**
     * Constructor que inicializa el presentador con la vista especificada.
     *
     * @param vista la vista del juego
     */
    public PresentadorJuego(IVistaJuego vista) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.clientConnection = ClientConnection.getInstance();
    }

    /**
     * Inicializa la partida con el nombre del oponente y el turno inicial.
     *
     * @param nombreOponente el nombre del oponente
     * @param esMiTurno true si es el turno del jugador, false en caso contrario
     */
    public void inicializarJuego(String nombreOponente, boolean esMiTurno) {
        vista.setNombreOponente(nombreOponente);
        vista.actualizarInterfazTurno(esMiTurno);
    }

    /**
     * Maneja la respuesta del servidor ante un ataque.
     *
     * @param mensaje el mensaje recibido del servidor
     */
    public void manejarAtaqueResponse(Map<String, Object> mensaje) {
        String resultado = (String) mensaje.get("resultado");
        String mensajeTexto = (String) mensaje.get("mensaje");
        Integer vidaNave = (Integer) mensaje.get("vida_nave");
        Integer numeroNave = (Integer) mensaje.get("numero_nave");
        Integer x = (Integer) mensaje.get("x");
        Integer y = (Integer) mensaje.get("y");
        String turnoJugador = (String) mensaje.get(ControlPartida.DETERMINAR_TURNO.name());
        String ganador = (String) mensaje.get("ganador");

        boolean esMiTurno = turnoJugador != null && turnoJugador.equals(modeloJugador.getNombre());
        vista.actualizarInterfazTurno(esMiTurno);

        if (x != null && y != null) {
            if (resultado != null && resultado.startsWith("RESULTADO_ATAQUE_REALIZADO")) {
                boolean impacto = resultado.contains("IMPACTO");
                vista.actualizarTableroEnemigo(x, y, impacto);
                if (impacto && numeroNave != null && vidaNave != null) {
                    vista.actualizarEstadoFlotaEnemigo(numeroNave, vidaNave);
                }
            } else if (resultado != null && resultado.startsWith("RESULTADO_ATAQUE_RECIBIDO")) {
                boolean impacto = resultado.contains("IMPACTO");
                vista.actualizarTableroJugador(x, y, impacto);
                if (impacto && numeroNave != null && vidaNave != null) {
                    vista.actualizarEstadoFlotaJugador(numeroNave, vidaNave);
                }
            }
        }

        if ("PARTIDA_FINALIZADA".equals(resultado)) {
            vista.finalizarJuego(ganador);
        } else {
            vista.mostrarUltimoMensaje(mensajeTexto);
        }
    }

    /**
     * Envía un ataque al servidor en las coordenadas especificadas.
     *
     * @param x la coordenada x del ataque
     * @param y la coordenada y del ataque
     */
    public void atacar(int x, int y) {
        // Enviar ataque al servidor
        clientConnection.atacar(x, y);
    }
    
    /**
     * Envía un ataque vacío al servidor.
     */
    public void enviarAtaqueVacio() {
        clientConnection.atacar(10, 10);
    }

    /**
     * Detiene el temporizador de la vista cuando se realiza un ataque.
     */
    @Override
    public void enAtaqueRealizado() {
        vista.detenerTemporizador();
    }

    /**
     * Envía un mensaje de rendición al servidor.
     */
    public void enviarRendicion() {
        // Enviar mensaje de rendición al servidor
        Map<String, Object> datos = new HashMap<>();
        datos.put("accion", "RENDIRSE");
        datos.put("id_jugador", modeloJugador.getId());
        ClientConnection.getInstance().enviarRendicion(datos);
    }
    
    /**
     * Finaliza el juego debido a la rendición de un jugador y muestra al ganador.
     *
     * @param ganador el nombre del ganador
     */
    public void finalizarJuegoPorRendicion(String ganador) {
        vista.finalizarJuegoPorRendicion(ganador);
    }

}
