/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import enums.AccionesJugador;

/**
 *
 * @author alex_
 */
public class PruebaDTO {
    int x;
    int y;
    AccionesJugador accion;

    public PruebaDTO(int x, int y,AccionesJugador accion) {
        this.x = x;
        this.y = y;
        this.accion = accion;
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public AccionesJugador getAccion() {
        return accion;
    }

    public void setAccion(AccionesJugador accion) {
        this.accion = accion;
    }


    
    
}
