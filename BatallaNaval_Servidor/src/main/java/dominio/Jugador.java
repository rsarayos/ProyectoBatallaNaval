/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author af_da
 */
public class Jugador {

    private String nombre;
    private Estadisticas estadisticas;
    private List<Unidad> unidades;
    private String id;
    private boolean listo;
    private boolean quiereRevancha;

    public Jugador(String nombre, Estadisticas estadisticas, List<Unidad> unidades) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
        this.unidades = unidades;
        this.listo = false;
        this.quiereRevancha = false;
    }

    public Jugador(String nombre, Estadisticas estadisticas) {
        this.nombre = nombre;
        this.estadisticas = estadisticas;
    }

    public Jugador() {
    }

    public Jugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    public void addUnidad(Unidad unidad) {
        this.unidades.add(unidad);
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }
    
    public boolean isQuiereRevancha() {
        return quiereRevancha;
    }

    public void setQuiereRevancha(boolean quiereRevancha) {
        this.quiereRevancha = quiereRevancha;
    }

}
