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
    private final String nombre;
    private int vida;
    private final int tamano;
    private Orientacion orientacion;
    private EstadoUnidad estado;

    public Unidad(String nombre, int vida, int tamano, Orientacion orientacion, EstadoUnidad estado) {
        this.nombre = nombre;
        this.vida = vida;
        this.tamano = tamano;
        this.orientacion = orientacion;
        this.estado = estado;
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
    
    
}
