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
public class Tablero {
    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private List<List<Casilla>> casillas;
    private List<UbicacionUnidad> unidades;
    private List<Disparo>disaparosRealizados;

    public Tablero(List<List<Casilla>> casillas, List<UbicacionUnidad> unidades, List<Disparo> disaparosRealizados) {
        this.casillas = casillas;
        this.unidades = unidades;
        this.disaparosRealizados = disaparosRealizados;
    }

    public List<List<Casilla>> getCasillas() {
        return casillas;
    }

    public void setCasillas(List<List<Casilla>> casillas) {
        this.casillas = casillas;
    }

    public List<UbicacionUnidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UbicacionUnidad> unidades) {
        this.unidades = unidades;
    }

    public List<Disparo> getDisaparosRealizados() {
        return disaparosRealizados;
    }

    public void setDisaparosRealizados(List<Disparo> disaparosRealizados) {
        this.disaparosRealizados = disaparosRealizados;
    }
    
    
}
