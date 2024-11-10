/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import enums.EstadoPartida;

/**
 *
 * @author af_da
 */
public class Partida {

    public static Partida instance;

    private Tablero tableroJugador;
    private Tablero tableroEnemigo;
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

    public Tablero getTableroJugador() {
        return tableroJugador;
    }

    public void setTableroJugador(Tablero tableroJugador) {
        this.tableroJugador = tableroJugador;
    }

    public Tablero getTableroEnemigo() {
        return tableroEnemigo;
    }

    public void setTableroEnemigo(Tablero tableroEnemigo) {
        this.tableroEnemigo = tableroEnemigo;
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

}
