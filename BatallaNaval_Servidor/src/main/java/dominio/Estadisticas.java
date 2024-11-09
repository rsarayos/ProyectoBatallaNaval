/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author af_da
 */
public class Estadisticas {
    private int movimientos;
    private int turnos;
    private int ataquesExitosos;

    public Estadisticas(int movimientos, int turnos, int ataquesExitosos) {
        this.movimientos = movimientos;
        this.turnos = turnos;
        this.ataquesExitosos = ataquesExitosos;
    }

    public Estadisticas() {
    }

    public int getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public int getAtaquesExitosos() {
        return ataquesExitosos;
    }

    public void setAtaquesExitosos(int ataquesExitosos) {
        this.ataquesExitosos = ataquesExitosos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estadisticas{");
        sb.append("movimientos=").append(movimientos);
        sb.append(", turnos=").append(turnos);
        sb.append(", ataquesExitosos=").append(ataquesExitosos);
        sb.append('}');
        return sb.toString();
    }
    
    
}
