/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import enums.EstadoPartida;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author af_da
 */
public class Partida {

    public static Partida instance;
    private List<Jugador> jugadores = new ArrayList<>();
    private Tablero tableroJugador1;
    private Tablero tableroJugador2;
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

    public Tablero getTableroJugador1() {
        return tableroJugador1;
    }

    public void setTableroJugador1(Tablero tableroJugador) {
        this.tableroJugador1 = tableroJugador;
    }

    public Tablero getTableroJugador2() {
        return tableroJugador2;
    }

    public void setTableroJugador2(Tablero tableroJugador2) {
        this.tableroJugador2 = tableroJugador2;
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
