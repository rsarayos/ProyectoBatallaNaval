package presentador;

import comunicacion.ClientConnection;
import enums.ControlPartida;
import ivistas.IVistaJuego;
import java.util.HashMap;
import java.util.Map;
import modelo.ModeloJugador;

/**
 *
 * @author alex_
 */
public class PresentadorJuego implements AtaqueListener{

    private IVistaJuego vista;
    private ModeloJugador modeloJugador;
    private ClientConnection clientConnection;

    public PresentadorJuego(IVistaJuego vista) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.clientConnection = ClientConnection.getInstance();
    }

    public void inicializarJuego(String nombreOponente, boolean esMiTurno) {
        vista.setNombreOponente(nombreOponente);
        vista.actualizarInterfazTurno(esMiTurno);
    }

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

    public void atacar(int x, int y) {
        // Enviar ataque al servidor
        clientConnection.atacar(x, y);
    }
    
    public void enviarAtaqueVacio() {
        clientConnection.atacar(10, 10);
    }

    @Override
    public void enAtaqueRealizado() {
        vista.detenerTemporizador();
    }

    public void enviarRendicion() {
        // Enviar mensaje de rendición al servidor
        Map<String, Object> datos = new HashMap<>();
        datos.put("accion", "RENDIRSE");
        datos.put("id_jugador", modeloJugador.getId());
        ClientConnection.getInstance().enviarRendicion(datos);
    }
    
    public void finalizarJuegoPorRendicion(String ganador) {
        vista.finalizarJuegoPorRendicion(ganador);
    }

}
