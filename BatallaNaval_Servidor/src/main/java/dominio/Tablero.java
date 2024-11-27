/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author af_da
 */
public class Tablero {

    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private Casilla[][] casillas;
    private List<UbicacionUnidad> unidades;
    private List<Disparo> disparosRecibidos;
    private int numNavesDestruidas;

    // En el constructor inicial se crea la lista de casillas
    public Tablero() {

        casillas = new Casilla[FILAS][COLUMNAS];

        // se crean las casillas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Casilla celda = new Casilla(new Coordenada(i, j));
                casillas[i][j] = celda;
            }
        }

        // se inician la lista de ubicaciones y de los disparos realizados
        this.unidades = new ArrayList<>();
        this.disparosRecibidos = new ArrayList<>();
        this.numNavesDestruidas = 0;
    }

    public void agregarUbicacion(UbicacionUnidad ubicacion) {
        this.unidades.add(ubicacion);
    }

    public void addDisparoRecibido(Disparo disparo) {
        this.disparosRecibidos.add(disparo);
    }

    public List<UbicacionUnidad> getUnidades() {
        return unidades;
    }

    public Tablero(int numNavesDestruidas) {
        this.numNavesDestruidas = numNavesDestruidas;
    }

    public int getNumNavesDestruidas() {
        return numNavesDestruidas;
    }

    public void setNumNavesDestruidas(int numNavesDestruidas) {
        this.numNavesDestruidas = numNavesDestruidas;
    }

    public void addNaveDestruida() {
        this.numNavesDestruidas += 1;
    }

    public List<Disparo> getDisparosRecibidos() {
        return disparosRecibidos;
    }

    public void setDisparosRecibidos(List<Disparo> disparosRecibidos) {
        this.disparosRecibidos = disparosRecibidos;
    }
    
    
    
}
