package dominio;

import enums.EstadoPartida;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author af_da
 */
public class Partida {

    public static Partida instance;
    private List<Jugador> jugadores = new ArrayList<>();
    // private Tablero tableroJugador1;
    // private Tablero tableroJugador2;
    private Map<String, Tablero> tableros = new HashMap();
    private Jugador ganador;
    private Long duracion;
    private EstadoPartida estado;
    private Jugador jugadorTurno;
    private String codigoAcceso;

    private Partida() {
    }

    public static Partida getInstance() {
        if (instance == null) {
            instance = new Partida();
        }
        return instance;
    }

    private void actualizarTurno(Jugador jugador) {
        this.jugadorTurno = jugador;
    }

    public static void setInstance(Partida instance) {
        Partida.instance = instance;
    }

    public Map<String, Tablero> getTableros() {
        return tableros;
    }

    public void setTableros(Map<String, Tablero> tableros) {
        this.tableros = tableros;
    }

    public void addTablero(String id, Tablero tablero) {
        this.tableros.put(id, tablero);
    }

    public void ReiniciarPartida() {
        this.ganador = null;
        this.duracion = null;
        this.estado = null;
        this.jugadorTurno = null;
    }

    public void limpiarTableros() {
        this.tableros.forEach((k, tablero) -> {
            tablero.limpiarTablero();
        });
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public Tablero getTableroJugador(String id) {
        return this.tableros.getOrDefault(id, null);
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    public Jugador getJugadorTurno() {
        return jugadorTurno;
    }

    public void setJugadorTurno(Jugador jugadorTurno) {
        this.jugadorTurno = jugadorTurno;
    }

    public String getCodigoAcceso() {
        return codigoAcceso;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

    public void addJugador(Jugador jugador) {
        this.jugadores.add(jugador);
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public boolean todosLosJugadoresListos() {
        for (Jugador jugador : jugadores) {
            if (!jugador.isListo()) {
                return false;
            }
        }
        return true;
    }

}
