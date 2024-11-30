/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.Map;

/**
 *
 * @author af_da
 */
public class UbicacionUnidad {
    private Unidad unidad;
    private Map<Casilla, Boolean> casillas;

    public UbicacionUnidad(Unidad unidad, Map<Casilla, Boolean> casillas) {
        this.unidad = unidad;
        this.casillas = casillas;
    }

    public void addCasilla(Casilla casilla, Boolean bool) {
        this.casillas.put(casilla, bool);
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Map<Casilla, Boolean> getCasillas() {
        return casillas;
    }

    public void limipiarCasillas() {
        this.casillas.clear();
    }

    public void setCasillas(Map<Casilla, Boolean> casillas) {
        this.casillas = casillas;
    }

    @Override
    public String toString() {
        return "UbicacionUnidad{" + "unidad=" + unidad + ", casillas=" + casillas + '}';
    }

}
