package vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author alex_
 */
public class VistaTablero extends JPanel {

    private VistaCelda[][] celdas = new VistaCelda[10][10];
    private boolean isDragging = false;
    private VistaCelda celdaSeleccionada;
    private VistaCelda celdaOriginal;
    private Set<VistaCelda> celdasOcupadasNaveSeleccionada;
    private Set<VistaCelda> celdasOcupadasNaveInicio;
    private Set<VistaCelda> celdasAdyacentesCompartidas;
    private List<Point> desplazamientosRelativos;
    private VistaNave naveSeleccionada;
    private Set<VistaNave> navesEnTablero;

    public VistaTablero() {

        setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                VistaCelda celda = new VistaCelda(i, j, this);
                celdas[i][j] = celda;
                add(celda);
            }
        }
        
        celdasAdyacentesCompartidas = new HashSet();
        navesEnTablero = new HashSet();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePresionado(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseSoltado(e);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseArrastrado(e);
            }
        });
    }

    private void mousePresionado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        celdaSeleccionada = celdas[fila][columna];
        
        System.out.println("Se presiona la celda: ");
        System.out.println(celdaSeleccionada.toString());

        if (celdaSeleccionada.getNave() != null) {
            if (e.getButton() == MouseEvent.BUTTON3) { // Botón derecho
                System.out.println("se presiono rotar");
                celdasOcupadasNaveSeleccionada = celdaSeleccionada.getNave().getCeldasOcupadas();
                celdasOcupadasNaveInicio = celdaSeleccionada.getNave().getCeldasOcupadas();
                naveSeleccionada = celdaSeleccionada.getNave();
                System.out.println(naveSeleccionada.toString());
                celdaOriginal = celdaSeleccionada;
                // Desmarcar las celdas adyacentes de la posición anterior
                // primero revisamos las celdas que rodean la nave:
                Set<VistaCelda> celdasAdyacentesTotales = obtenerAdyacentes(celdasOcupadasNaveInicio);
                Set<VistaCelda> celdasAdyacentesExclusivas = new HashSet();
                // limpiamos la lista de celdas que son adyacentes con otra nave
                for (VistaCelda c : celdasAdyacentesTotales) {
                    if (verificarCeldasAlrededor(c, naveSeleccionada)) {
                        celdasAdyacentesExclusivas.add(c);
                    } else {
                        celdasAdyacentesCompartidas.add(c);

                    }
                }

                // finalmente desmarcamos solo las que sean exclusivas
                marcarAdyacentes(celdasAdyacentesExclusivas, false);

                rotar(celdaSeleccionada.getNave());
                celdasAdyacentesCompartidas.clear();
                celdaSeleccionada = null;
                celdasOcupadasNaveSeleccionada = null;
                // volvemos a marcar las celdas adyacentes de las otras naves, por que no se por que se desmarcan fak
                for (VistaNave n : navesEnTablero) {
                    if (naveSeleccionada.getNumNave() != n.getNumNave()) {
                        marcarAdyacentes(n.getCeldasOcupadas(), true);
                    }
                }
                repaint();
            } else {
                isDragging = true;
                celdasOcupadasNaveSeleccionada = celdaSeleccionada.getNave().getCeldasOcupadas();
                celdasOcupadasNaveInicio = celdaSeleccionada.getNave().getCeldasOcupadas();
                naveSeleccionada = celdaSeleccionada.getNave();
                System.out.println("se selecciono nave");
                System.out.println(naveSeleccionada.toString());
                celdaOriginal = celdaSeleccionada;
                // Desmarcar las celdas adyacentes de la posición anterior
                // primero revisamos las celdas que rodean la nave:
                Set<VistaCelda> celdasAdyacentesTotales = obtenerAdyacentes(celdasOcupadasNaveInicio);
                Set<VistaCelda> celdasAdyacentesExclusivas = new HashSet();
                // limpiamos la lista de celdas que son adyacentes con otra nave
                for (VistaCelda c : celdasAdyacentesTotales) {
                    if(verificarCeldasAlrededor(c, naveSeleccionada)){
                       celdasAdyacentesExclusivas.add(c); 
                    } else {
                       celdasAdyacentesCompartidas.add(c);
                       
                    }
                }
                
                // finalmente desmarcamos solo las que sean exclusivas
                marcarAdyacentes(celdasAdyacentesExclusivas, false);

                // Guardamos los desplazamientos relativos al inicio del arrastre
                desplazamientosRelativos = new ArrayList<>();
                for (VistaCelda celda : celdasOcupadasNaveSeleccionada) {
                    int deltaFila = celda.getFila() - celdaSeleccionada.getFila();
                    int deltaColumna = celda.getColumna() - celdaSeleccionada.getColumna();
                    desplazamientosRelativos.add(new Point(deltaFila, deltaColumna));
                }
                
                // volvemos a marcar las celdas adyacentes de las otras naves, por que no se por que se desmarcan fak
                for (VistaNave n : navesEnTablero) {
                    if (naveSeleccionada.getNumNave() != n.getNumNave()) {
                        marcarAdyacentes(n.getCeldasOcupadas(), true);
                    }
                }
                
            }
        } 

    }

    private void mouseSoltado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        VistaCelda celdaDestino = celdas[fila][columna];

        if (isDragging && celdaSeleccionada != null) {
            // Calcular las nuevas celdas ocupadas basadas en la celda de ancla (celdaDestino) y los desplazamientos relativos
            Set<VistaCelda> nuevasCeldasOcupadas = celdasOcupadasNaveSeleccionada;

            if (!nuevasCeldasOcupadas.isEmpty() && puedeColocarNave(nuevasCeldasOcupadas, naveSeleccionada) && celdaDestino.getNave() == null) {
                // Limpiar las celdas anteriores
                for (VistaCelda celda : celdasOcupadasNaveInicio) {
                    this.celdas[celda.getFila()][celda.getColumna()].setNave(null);
                    this.celdas[celda.getFila()][celda.getColumna()].setHighlighted(false);
                    this.navesEnTablero.remove(naveSeleccionada);
                }

                // Colocar la nave en la nueva posición
                VistaNave naveNueva = new VistaNave(nuevasCeldasOcupadas, naveSeleccionada.isDireccion(), naveSeleccionada.getNumNave());
                colocarNave(naveNueva);
                marcarAdyacentes(naveNueva.getCeldasOcupadas(), true);
                celdasAdyacentesCompartidas.clear();
                
                // Actualizar la nave en la lista de naves
                this.navesEnTablero.add(naveNueva);

                System.out.println("Nave movida a la nueva posición: ");
                for (VistaCelda celda : nuevasCeldasOcupadas) {
                    System.out.println(celda.toString());
                }
            } else {
                System.out.println("Posición inválida. La nave regresará a su posición inicial.");

                // Volver a colocar la nave en las celdas originales
                VistaNave naveInicial = new VistaNave(celdasOcupadasNaveInicio, naveSeleccionada.isDireccion(), naveSeleccionada.getNumNave());
                colocarNave(naveInicial);
                marcarAdyacentes(naveInicial.getCeldasOcupadas(), true);
                celdasAdyacentesCompartidas.clear();
            }

            // Resetear el estado de arrastre
            for (VistaCelda celda : celdasOcupadasNaveSeleccionada) {
                celda.setHighlighted(false);
            }
            isDragging = false;
            celdaSeleccionada = null;
            celdasOcupadasNaveSeleccionada = null;
            repaint();
        }
    }

    private void mouseArrastrado(MouseEvent e) {
        if (isDragging && celdaSeleccionada != null) {
            int fila = e.getY() / getTamañoCelda().height;
            int columna = e.getX() / getTamañoCelda().width;
            VistaCelda nuevaCeldaAncla = celdas[fila][columna];

            // Desactivamos el resaltado anterior
            for (VistaCelda celda : celdasOcupadasNaveSeleccionada) {
                celda.setHighlighted(false);
            }

            // Calculamos las nuevas posiciones basadas en la celda de ancla y los desplazamientos relativos
            Set<VistaCelda> nuevasCeldas = new HashSet<>();
            for (Point desplazamiento : desplazamientosRelativos) {
                int nuevaFila = nuevaCeldaAncla.getFila() + desplazamiento.x;
                int nuevaColumna = nuevaCeldaAncla.getColumna() + desplazamiento.y;

                if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10) {
                    nuevasCeldas.add(celdas[nuevaFila][nuevaColumna]);
                } else {
                    // Si alguna celda está fuera del tablero, salimos y no resaltamos nada
                    nuevasCeldas.clear();
                    break;
                }
            }

            // Si la posición es válida, resalta las nuevas celdas
            if (!nuevasCeldas.isEmpty() && puedeColocarNave(nuevasCeldas, naveSeleccionada) && revisarCeldasAdyacentesCompartidas(nuevasCeldas)) {
                for (VistaCelda celda : nuevasCeldas) {
                    celda.setHighlighted(true);
                }
                celdasOcupadasNaveSeleccionada = nuevasCeldas;
                System.out.println("Se movio a celdas");
                System.out.println(celdasOcupadasNaveSeleccionada.toString());
            }

            // Actualizamos la posición de la celda ancla
            celdaSeleccionada = nuevaCeldaAncla;
            repaint();
        }
    }

    private Set<VistaCelda> calcularNuevasCeldas(VistaCelda nuevaCelda) {
        Set<VistaCelda> nuevasCeldas = new HashSet<>();
        int filaInicial = nuevaCelda.getFila();
        int columnaInicial = nuevaCelda.getColumna();

        for (VistaCelda celda : celdasOcupadasNaveSeleccionada) {
            int deltaFila = celda.getFila() - celdaOriginal.getFila();
            int deltaColumna = celda.getColumna() - celdaOriginal.getColumna();
            int nuevaFila = filaInicial + deltaFila;
            int nuevaColumna = columnaInicial + deltaColumna;

            if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10) {
                nuevasCeldas.add(celdas[nuevaFila][nuevaColumna]);
            } else {
                // Si alguna celda está fuera del tablero
                return new HashSet<>();
            }
        }
        return nuevasCeldas;
    }

    public void rotar(VistaNave nave) {
        Set<VistaCelda> celdasOcupadas = nave.getCeldasOcupadas();
        boolean horizontal = nave.isDireccion();

        // Encontrar la primera celda de la nave
        VistaCelda primeraCelda = null;
        for (VistaCelda celda : celdasOcupadas) {
            if (primeraCelda == null
                    || (horizontal && celda.getColumna() < primeraCelda.getColumna())
                    || (!horizontal && celda.getFila() < primeraCelda.getFila())) {
                primeraCelda = celda;
            }
        }
        
        

        // Calcular las nuevas celdas para la rotación
        Set<VistaCelda> nuevasCeldasOcupadas = new HashSet<>();
        int tamañoNave = celdasOcupadas.size();

        for (int i = 0; i < tamañoNave; i++) {
            int nuevaFila = horizontal ? primeraCelda.getFila() + i : primeraCelda.getFila();
            int nuevaColumna = horizontal ? primeraCelda.getColumna() : primeraCelda.getColumna() + i;

            // Verificar si está dentro de los límites
            if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10) {
                nuevasCeldasOcupadas.add(celdas[nuevaFila][nuevaColumna]);
            } else {
                // Abortamos si está fuera de los límites
                return;
            }
        }

        // Verificar si las nuevas celdas no están ocupadas por otra nave
        if (puedeColocarNave(nuevasCeldasOcupadas, nave)) {
            // Verificar las adyacencias de las nuevas celdas ocupadas
            for (VistaCelda nuevaCelda : nuevasCeldasOcupadas) {
                Set<VistaCelda> adyacentes = obtenerAdyacentes(nuevaCelda);
                for (VistaCelda adyacente : adyacentes) {
                    if (adyacente.getNave() != null && adyacente.getNave() != nave) {
                        // Cancelamos la rotación si hay otra nave en las adyacencias
                        return;
                    }
                }
            }

            // Realizamos la rotación
            for (VistaCelda celda : celdasOcupadas) {
                celda.setNave(null);
            }
            
            for (VistaCelda celda : celdasOcupadas) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (celdas[i][j].getFila() == celda.getFila() && celdas[i][j].getColumna() == celda.getColumna()) {
                            celdas[i][j].setNave(null);
                        }
                    }
                }

//            celda.setNave(null); // Limpia la referencia a la nave de las celdas anteriores
//            celda.setHighlighted(false); // Limpia el resaltado, si es necesario
            }
            nave.setCeldasOcupadas(nuevasCeldasOcupadas);
            nave.setDireccion(!horizontal);
            colocarNave(nave);
            // desdibujamos las posicion de la nave anterior
            
        }

    }

    private boolean puedeColocarNave(Set<VistaCelda> celdasOcupadas, VistaNave naveActual) {
        for (VistaCelda celda : celdasOcupadas) {
            int fila = celda.getFila();
            int columna = celda.getColumna();
            VistaNave naveEnCelda = celdas[fila][columna].getNave();

            // Ignorar las celdas ocupadas por la misma nave
            if (naveEnCelda != null && naveEnCelda != naveActual || celda.isEsAdyacente()) {
                System.out.println(celdas[fila][columna].toString() + " está ocupada por otra nave.");
                return false; // La celda está ocupada por otra nave
            }
        }
        return true;
    }

    public boolean colocarNave(VistaNave nave) {
        Set<VistaCelda> celdasOcupadas = nave.getCeldasOcupadas();
        if (puedeColocarNave(celdasOcupadas, nave)) {
            for (VistaCelda celda : celdasOcupadas) {
                this.celdas[celda.getFila()][celda.getColumna()].setNave(nave);
            }
            marcarAdyacentes(nave.getCeldasOcupadas(), true);
            desmarcarCeldasNaveAdyacentes();
            return true;
        }
        return false;
    }

    private boolean verificarNave(VistaCelda celdaOrigen, VistaCelda celdaDestino) {
        if (celdaDestino.getNave() == null) {
            celdaDestino.setNave(celdaOrigen.getNave());
            return true;
        }
        return false;
    }

    public Dimension getTamañoCelda() {
        int celdaAncho = getWidth() / 10;
        int celdaAlto = getHeight() / 10;
        return new Dimension(celdaAncho, celdaAlto);
    }
    
    private void marcarAdyacentes(Set<VistaCelda> celdas, boolean marcar) {
        for (VistaCelda celda : celdas) {
            int fila = celda.getFila();
            int columna = celda.getColumna();

            // Recorremos las celdas adyacentes (incluyendo diagonales)
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int filaAdyacente = fila + i;
                    int columnaAdyacente = columna + j;

                    // Verificamos que la celda adyacente esté dentro de los límites del tablero
                    if (filaAdyacente >= 0 && filaAdyacente < this.celdas.length
                            && columnaAdyacente >= 0 && columnaAdyacente < this.celdas[0].length) {

                        // Obtenemos la celda adyacente
                        VistaCelda celdaAdyacente = this.celdas[filaAdyacente][columnaAdyacente];

                        // Marcar o desmarcar la adyacencia dependiendo del parámetro
                        celdaAdyacente.setEsAdyacente(marcar);
                    }
                }
            }
        }
    }
    
    public Set<VistaCelda> obtenerAdyacentes(Set<VistaCelda> celdasOcupadas) {
        Set<VistaCelda> celdasAdyacentes = new HashSet<>();

        for (VistaCelda celda : celdasOcupadas) {
            int fila = celda.getFila();
            int columna = celda.getColumna();

            // Revisar las 8 posiciones alrededor de la celda
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    // Omitir la posición de la celda misma
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    int filaAdyacente = fila + i;
                    int columnaAdyacente = columna + j;

                    // Verificar si está dentro de los límites del tablero
                    if (estaDentroDelTablero(filaAdyacente, columnaAdyacente)) {
                        VistaCelda celdaAdyacente = celdas[filaAdyacente][columnaAdyacente];

                        // Solo agregar la celda adyacente si no está ya ocupada por la nave
                        if (!celdasOcupadas.contains(celdaAdyacente)) {
                            celdasAdyacentes.add(celdaAdyacente);
                        }
                    }
                }
            }
        }

        return celdasAdyacentes;
    }
    
    public boolean verificarCeldasAlrededor(VistaCelda celda, VistaNave naveSeleccionada) {
        int fila = celda.getFila();
        int columna = celda.getColumna();

        // Revisar las 8 posiciones alrededor de la celda
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Omitir la posición de la celda misma
                if (i == 0 && j == 0) {
                    continue;
                }

                int filaAdyacente = fila + i;
                int columnaAdyacente = columna + j;

                // Verificar si está dentro de los límites del tablero
                if (estaDentroDelTablero(filaAdyacente, columnaAdyacente)) {
                    VistaCelda celdaAdyacente = celdas[filaAdyacente][columnaAdyacente];

                    // Si la celda adyacente tiene una nave distinta a la que estamos moviendo
                    if (celdaAdyacente.getNave() != null && celdaAdyacente.getNave() != naveSeleccionada) {
                        return false;
                    }
                }
            }
        }

        return true; // No se encontraron naves diferentes a la seleccionada alrededor
    }
    
    private boolean estaDentroDelTablero(int fila, int columna) {
        int filasTotales = 10;  // Número total de filas del tablero
        int columnasTotales = 10;  // Número total de columnas del tablero

        return fila >= 0 && fila < filasTotales && columna >= 0 && columna < columnasTotales;
    }
    
    public boolean revisarCeldasAdyacentesCompartidas(Set<VistaCelda> nuevasCeldas){
        for (VistaCelda c : celdasAdyacentesCompartidas) {
            for (VistaCelda nc : nuevasCeldas) {
                if (c.getFila() == nc.getFila() && c.getColumna() == nc.getColumna()) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public void setNavesEnTablero(VistaNave nave) {
        this.navesEnTablero.add(nave);
    }
    
    public void desmarcarCeldasNaveAdyacentes(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(celdas[i][j].getNave() != null){
                    celdas[i][j].setEsAdyacente(false);
                }
            }
        }
    }
    
    public Set<VistaCelda> obtenerAdyacentes(VistaCelda celda) {
        Set<VistaCelda> celdasAdyacentes = new HashSet<>();
        int fila = celda.getFila();
        int columna = celda.getColumna();

        // Coordenadas de las 8 celdas adyacentes (arriba, abajo, izquierda, derecha y diagonales)
        int[] desplazamientosFila = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] desplazamientosColumna = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < desplazamientosFila.length; i++) {
            int nuevaFila = fila + desplazamientosFila[i];
            int nuevaColumna = columna + desplazamientosColumna[i];

            // Verificamos si la celda adyacente está dentro de los límites del tablero
            if (nuevaFila >= 0 && nuevaFila < celdas.length && nuevaColumna >= 0 && nuevaColumna < celdas[0].length) {
                celdasAdyacentes.add(celdas[nuevaFila][nuevaColumna]);
            }
        }

        return celdasAdyacentes;
    }

}
