/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import enums.EstadoUnidad;
import enums.Orientacion;

/**
 *
 * @author af_da
 */
public class Unidad {
    
    private int numNave;
    private final String nombre;
    private int vida;
    private int vidaMaxima;
    private final int tamano;
    private Orientacion orientacion;
    private EstadoUnidad estado;

    // solamente recibimos el nombre y tamaño para construir, la orientacion,  se recibe despues
    public Unidad(String nombre, int tamano) {
        this.nombre = nombre;
        this.vida = tamano;
        this.vidaMaxima = vida;
        this.tamano = tamano;
        this.estado = EstadoUnidad.SIN_DANO;
    }

    public int getNumNave() {
        return numNave;
    }

    public void setNumNave(int numNave) {
        this.numNave = numNave;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getTamano() {
        return tamano;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    public EstadoUnidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoUnidad estado) {
        this.estado = estado;
    }
    
    public void reiniciarVida(){
        this.vida = vidaMaxima;
        this.estado = EstadoUnidad.SIN_DANO;
    }

    @Override
    public String toString() {
        return "Unidad{" + "numNave=" + numNave + ", nombre=" + nombre + '}';
    }
    
    
}
