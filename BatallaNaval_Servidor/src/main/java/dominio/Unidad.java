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
    private String nombre;
    private int vida;
    private TipoUnidad tipo;
    private Orientacion orientacion;
    private EstadoUnidad estado;

    public Unidad(String nombre, int vida, TipoUnidad tipo, Orientacion orientacion, EstadoUnidad estado) {
        this.nombre = nombre;
        this.vida = vida;
        this.tipo = tipo;
        this.orientacion = orientacion;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public TipoUnidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoUnidad tipo) {
        this.tipo = tipo;
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
