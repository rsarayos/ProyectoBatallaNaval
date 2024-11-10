/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import enums.EstadoCasilla;

/**
 *
 * @author af_da
 */
public class Disparo {
    private Casilla objetivo;
    private EstadoCasilla estado;

    public Disparo(Casilla objetivo, EstadoCasilla estado) {
        this.objetivo = objetivo;
        this.estado = estado;
    }
    
    public Casilla getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Casilla objetivo) {
        this.objetivo = objetivo;
    }

    public EstadoCasilla getEstado() {
        return estado;
    }

    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }


    
    
}
