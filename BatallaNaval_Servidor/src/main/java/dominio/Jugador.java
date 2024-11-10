/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.List;

/**
 *
 * @author af_da
 */
public class Jugador {
    private String nombre;
    private Estadisticas estadisticas;
    private List<Unidad> unidades;

    public Jugador(String nombre, Estadisticas estadisticas, List<Unidad> unidades) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
        this.unidades = unidades;
    }

    public Jugador(String nombre, Estadisticas estadisticas) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Estadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }
    
    public void addUnidad(Unidad unidad){
        this.unidades.add(unidad);
    }
    
}
