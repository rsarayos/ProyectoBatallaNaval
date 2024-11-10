package modelo;

/**
 *
 * @author alex_
 */
public class ModeloCasilla {
    
    private final MCoordenada coordenada;
    private boolean esAdyacente;

    public ModeloCasilla(MCoordenada coordenada) {
        this.coordenada = coordenada;
        this.esAdyacente = false;
    }

    public MCoordenada getCoordenada() {
        return coordenada;
    }

    public boolean isEsAdyacente() {
        return esAdyacente;
    }

    public void setEsAdyacente(boolean esAdyacente) {
        this.esAdyacente = esAdyacente;
    }
    
}
