package vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
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

    public VistaTablero() {
        
        setLayout(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                VistaCelda celda = new VistaCelda(i, j, this);
                celdas[i][j] = celda;
                add(celda);
            }
        }

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
        VistaCelda celda = celdas[fila][columna];

        if (celda.getNave() != null) {
            if (e.getButton() == MouseEvent.BUTTON3) { // Botón derecho
                System.out.println("se presiono rotar");
                rotar(celda.getNave());
                repaint();
            } else {
                isDragging = true;
                celdaOriginal = celda;
                celdaSeleccionada = celda;
                System.out.println("Se selecciona la nave: " + celdaSeleccionada.getNave());
                celda.setHighlighted(true);
            }
        }

    }

    private void mouseSoltado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        VistaCelda celdaDestino = celdas[fila][columna];

        if (isDragging && celdaSeleccionada != null) {
            if (verificarNave(celdaSeleccionada, celdaDestino)) {
                // se elimina la nave anterior
                celdas[celdaOriginal.getFila()][celdaOriginal.getColumna()].setNave(null);
                System.out.println("Se borro la nave en celda " + celdaOriginal.toString());
                // se crea la nueva nave
                Set<VistaCelda> celdasNave = new HashSet<>();
                celdasNave.add(celdaDestino);
                VistaNave naveNueva = new VistaNave(celdasNave, true);
                colocarNave(naveNueva);
                
            }
            celdaSeleccionada.setHighlighted(false);
            isDragging = false;
            celdaSeleccionada = null;
            repaint();
        }
    }

    private void mouseArrastrado(MouseEvent e) {
        int fila = e.getY() / getTamañoCelda().height;
        int columna = e.getX() / getTamañoCelda().width;
        VistaCelda celda = celdas[fila][columna];

        if (isDragging && celdaSeleccionada != null) {
            celdaSeleccionada.setHighlighted(false);
            celda.setHighlighted(true);
            celdaSeleccionada = celda;
            repaint();
        }
    }
    
    public void rotar(VistaNave nave) {
        Set<VistaCelda> celdasOcupadas = nave.getCeldasOcupadas();
        System.out.println("Se encontraron las siguientes celdas: ");
        for (VistaCelda celdasOcupada : celdasOcupadas) {
            System.out.println(celdasOcupada.toString());
        }
        boolean horizontal = nave.isDireccion();

        // Encontrar la primera celda de la nave
        VistaCelda primeraCelda = null;
        for (VistaCelda celda : celdasOcupadas) {
            if (primeraCelda == null || (horizontal && celda.getColumna() < primeraCelda.getColumna()) || (!horizontal && celda.getFila() < primeraCelda.getFila())) {
                primeraCelda = celda;
                
            }
        }
        
        System.out.println("La primera celda de la nave es: ");
        System.out.println(primeraCelda.toString());

        // Actualizar las celdas ocupadas según la nueva orientación
        Set<VistaCelda> nuevasCeldasOcupadas = new HashSet<>();
        for (VistaCelda celda : celdasOcupadas) {
            int nuevaFila, nuevaColumna;
            if (horizontal) {
                nuevaFila = primeraCelda.getFila() + (celda.getColumna() - primeraCelda.getColumna());
                nuevaColumna = primeraCelda.getColumna();
            } else {
                nuevaFila = primeraCelda.getFila();
                nuevaColumna = primeraCelda.getColumna() + (celda.getFila() - primeraCelda.getFila());
            }
            VistaCelda nuevaCelda = celdas[nuevaFila][nuevaColumna];
            nuevasCeldasOcupadas.add(nuevaCelda);
        }
        
        // excluir primera celda
        for (VistaCelda c : nuevasCeldasOcupadas) {
            if (c.getFila() == primeraCelda.getFila() && c.getColumna() == primeraCelda.getColumna()) {
                primeraCelda = c;
            } 
        }
        
        nuevasCeldasOcupadas.remove(primeraCelda);
        
        System.out.println("Las nuevas celdas a ocupar son: ");
        for (VistaCelda celdasOcupada : nuevasCeldasOcupadas) {
            System.out.println(celdasOcupada.toString());
        }

        // Verificar si la nueva orientación cabe en el tablero
        if (puedeColocarNave(nuevasCeldasOcupadas)) {
            // una ves se verifico volver a agregar la primera celda
            nuevasCeldasOcupadas.add(primeraCelda);
            
            // Eliminar la nave antigua 
            for (VistaCelda celda : celdasOcupadas) {
                this.celdas[celda.getFila()][celda.getColumna()].setNave(null);
            }
            // Colocar la nave en la nueva orientación
            nave.setCeldasOcupadas(nuevasCeldasOcupadas);
            nave.setDireccion(!horizontal);
            colocarNave(nave);
        }
        
    }
    
    private boolean puedeColocarNave(Set<VistaCelda> celdasOcupadas) {
        for (VistaCelda celda : celdasOcupadas) {
            int fila = celda.getFila();
            int columna = celda.getColumna();
            if (fila < 0 || fila >= 10 || columna < 0 || columna >= 10 || celdas[fila][columna].getNave() != null) {
                System.out.println(celdas[fila][columna].toString() + " esta ocupada");
                return false; // No hay espacio
            }
        }
        return true;
    }

    public boolean colocarNave(VistaNave nave) {
        Set<VistaCelda> celdasOcupadas = nave.getCeldasOcupadas();
        if (puedeColocarNave(celdasOcupadas)) {
            for (VistaCelda celda : celdasOcupadas) {
                this.celdas[celda.getFila()][celda.getColumna()].setNave(nave);
            }
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
}
