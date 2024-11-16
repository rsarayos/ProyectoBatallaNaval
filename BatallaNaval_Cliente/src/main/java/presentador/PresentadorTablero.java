package presentador;

import comunicacion.ClientConnection;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingUtilities;
import modelo.MUbicacionUnidad;
import modelo.ModeloCasilla;
import modelo.ModeloTablero;
import modelo.ModeloUnidad;
import modelo.Orientacion;
import modelo.TipoUnidad;
import vista.VistaTablero;

/**
 *
 * @author alex_
 */
public class PresentadorTablero {

    private ModeloTablero modeloTablero;
    private VistaTablero vista;

    // Variables de estado para interacción
    private boolean isDragging;
    private ModeloCasilla casillaInicial;
    private MUbicacionUnidad unidadSeleccionada;
    private Set<ModeloCasilla> casillasOriginales;
    private Orientacion orientacionOriginal;
    private Set<ModeloCasilla> celdasResaltadas = new HashSet<>();

    public PresentadorTablero(VistaTablero vista) {
        this.vista = vista;
        this.modeloTablero = new ModeloTablero();
        inicializarNaves();
    }

    // Manejo de eventos
    public void onMousePressed(MouseEvent e) {
        int fila = e.getY() / vista.getTamañoCelda().height;
        int columna = e.getX() / vista.getTamañoCelda().width;

        casillaInicial = modeloTablero.getCasilla(fila, columna);

        if (casillaInicial != null && casillaInicial.getUnidadOcupante() != null) {
            unidadSeleccionada = casillaInicial.getUnidadOcupante();
            casillasOriginales = new HashSet<>(unidadSeleccionada.getCasillasOcupadas());
            orientacionOriginal = unidadSeleccionada.getUnidad().getOrientacion();

            if (SwingUtilities.isRightMouseButton(e)) {
                // Rotar unidad
                System.out.println("Se presiono rotar");
                rotarUnidad(unidadSeleccionada);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                // Iniciar arrastre
                isDragging = true;
                vista.setIsDragging(true); // Notificar a la vista
                vista.setUnidadSeleccionada(unidadSeleccionada);
            }
        }
    }

    public void onMouseReleased(MouseEvent e) {
        if (isDragging && unidadSeleccionada != null) {
            int fila = e.getY() / vista.getTamañoCelda().height;
            int columna = e.getX() / vista.getTamañoCelda().width;

            ModeloCasilla casillaDestino = modeloTablero.getCasilla(fila, columna);

            // Mover unidad
            moverUnidad(unidadSeleccionada, casillaDestino);

            // Limpiar resaltado
            for (ModeloCasilla casilla : celdasResaltadas) {
                casilla.setHighlighted(false);
            }
            celdasResaltadas.clear();

            // Resetear estado de arrastre
            isDragging = false;
            unidadSeleccionada = null;
            casillaInicial = null;
            vista.setIsDragging(false); // Notificar a la vista
            vista.setUnidadSeleccionada(null);
            
            vista.actualizarVista();
        }
    }

    public void onMouseDragged(MouseEvent e) {
        if (isDragging && unidadSeleccionada != null) {
            int fila = e.getY() / vista.getTamañoCelda().height;
            int columna = e.getX() / vista.getTamañoCelda().width;

            ModeloCasilla nuevaCasillaAncla = modeloTablero.getCasilla(fila, columna);

            if (nuevaCasillaAncla != null) {
                Set<ModeloCasilla> nuevasCasillas = calcularCasillasUnidad(nuevaCasillaAncla, unidadSeleccionada.getUnidad());

                // Limpiar resaltado anterior
                for (ModeloCasilla casilla : celdasResaltadas) {
                    casilla.setHighlighted(false);
                }
                celdasResaltadas.clear();

                // Resaltar nuevas celdas si la posición es válida
                if (nuevasCasillas != null && puedeColocarUnidad(nuevasCasillas, unidadSeleccionada)) {
                    for (ModeloCasilla casilla : nuevasCasillas) {
                        casilla.setHighlighted(true);
                        celdasResaltadas.add(casilla);
                    }
                }

                // Actualizar la vista
                vista.actualizarVista();
            }
        }
    }

    // Métodos de lógica
    private void rotarUnidad(MUbicacionUnidad ubicacionUnidad) {
        ModeloUnidad unidad = ubicacionUnidad.getUnidad();
        Orientacion nuevaOrientacion = (unidad.getOrientacion() == Orientacion.HORIZONTAL) ? Orientacion.VERTICAL : Orientacion.HORIZONTAL;
        unidad.setOrientacion(nuevaOrientacion);

        Set<ModeloCasilla> nuevasCasillas = calcularCasillasUnidad(casillaInicial, unidad);

        if (nuevasCasillas != null && puedeColocarUnidad(nuevasCasillas, ubicacionUnidad)) {
            // Actualizar modelo
            removerUnidad(ubicacionUnidad);

            // Limpiar adyacencias antes de colocar la unidad
            limpiarAdyacencias();

            ubicacionUnidad.setCasillasOcupadas(nuevasCasillas);
            colocarUnidad(ubicacionUnidad);

            // Remarcar adyacencias de todas las naves
            remarcarAdyacencias();
        } else {
            // No se puede rotar la unidad, restaurar orientación original
            unidad.setOrientacion(orientacionOriginal);
            colocarUnidad(ubicacionUnidad);
            remarcarAdyacencias();
        }
    }

    private void moverUnidad(MUbicacionUnidad ubicacionUnidad, ModeloCasilla nuevaCasillaAncla) {
        ModeloUnidad unidad = ubicacionUnidad.getUnidad();

        Set<ModeloCasilla> nuevasCasillas = calcularCasillasUnidad(nuevaCasillaAncla, unidad);

        if (nuevasCasillas != null && puedeColocarUnidad(nuevasCasillas, ubicacionUnidad)) {
            // Actualizar modelo
            removerUnidad(ubicacionUnidad);

            // Limpiar adyacencias antes de colocar la unidad
            limpiarAdyacencias();

            ubicacionUnidad.setCasillasOcupadas(nuevasCasillas);
            colocarUnidad(ubicacionUnidad);

            // Remarcar adyacencias de todas las naves
            remarcarAdyacencias();
        } else {
            // No se puede mover la unidad, restaurar posición original
            colocarUnidad(ubicacionUnidad);
            remarcarAdyacencias();
        }
    }

    private Set<ModeloCasilla> calcularCasillasUnidad(ModeloCasilla casillaAncla, ModeloUnidad unidad) {
        Set<ModeloCasilla> casillas = new HashSet<>();
        int x = casillaAncla.getCoordenada().getX();
        int y = casillaAncla.getCoordenada().getY();
        int tamaño = unidad.getTamaño();

        if (unidad.getOrientacion() == Orientacion.HORIZONTAL) {
            for (int i = 0; i < tamaño; i++) {
                ModeloCasilla casilla = modeloTablero.getCasilla(x, y + i);
                if (casilla != null) {
                    casillas.add(casilla);
                } else {
                    return null;
                }
            }
        } else {
            for (int i = 0; i < tamaño; i++) {
                ModeloCasilla casilla = modeloTablero.getCasilla(x + i, y);
                if (casilla != null) {
                    casillas.add(casilla);
                } else {
                    return null;
                }
            }
        }

        return casillas;
    }

    private boolean puedeColocarUnidad(Set<ModeloCasilla> casillas, MUbicacionUnidad unidadActual) {
        for (ModeloCasilla casilla : casillas) {
            MUbicacionUnidad unidadEnCasilla = casilla.getUnidadOcupante();
            if (unidadEnCasilla != null && !unidadEnCasilla.equals(unidadActual)) {
                // La casilla está ocupada por otra unidad
                return false;
            }
            if (casilla.esAdyacentePorOtraNave(unidadActual)) {
                // La casilla es adyacente a otra unidad
                return false;
            }
        }
        return true;
    }

    private void colocarUnidad(MUbicacionUnidad ubicacionUnidad) {
        modeloTablero.getUnidades().add(ubicacionUnidad);
        for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
            casilla.setUnidadOcupante(ubicacionUnidad);
        }
        marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, true);
    }

    private void removerUnidad(MUbicacionUnidad ubicacionUnidad) {
        modeloTablero.getUnidades().remove(ubicacionUnidad);
        for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
            casilla.setUnidadOcupante(null);
        }
        marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, false);
    }
    
    private void limpiarAdyacencias() {
        ModeloCasilla[][] casillas = modeloTablero.getCasillas();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j].getNavesAdyacentes().clear();
            }
        }
    }

    private void remarcarAdyacencias() {
        for (MUbicacionUnidad ubicacionUnidad : modeloTablero.getUnidades()) {
            marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, true);
        }
    }

    private void marcarAdyacentes(Set<ModeloCasilla> casillas, MUbicacionUnidad unidad, boolean marcar) {
        for (ModeloCasilla casilla : casillas) {
            for (ModeloCasilla adyacente : obtenerAdyacentes(casilla)) {
                if (!casillas.contains(adyacente)) {
                    if (marcar) {
                        adyacente.agregarNaveAdyacente(unidad);
                    } else {
                        adyacente.eliminarNaveAdyacente(unidad);
                    }
                }
            }
        }
    }

    private Set<ModeloCasilla> obtenerAdyacentes(ModeloCasilla casilla) {
        Set<ModeloCasilla> adyacentes = new HashSet<>();
        int x = casilla.getCoordenada().getX();
        int y = casilla.getCoordenada().getY();

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            ModeloCasilla adyacente = modeloTablero.getCasilla(x + dx[i], y + dy[i]);
            if (adyacente != null) {
                adyacentes.add(adyacente);
            }
        }

        return adyacentes;
    }

    private void inicializarNaves() {
        // Lista para almacenar las unidades creadas
        List<ModeloUnidad> unidades = new ArrayList<>();

        // Crear 2 naves de tamaño 4
        unidades.add(new ModeloUnidad(1, TipoUnidad.PORTAAVIONES.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.PORTAAVIONES.TAMANO));
        unidades.add(new ModeloUnidad(2, TipoUnidad.PORTAAVIONES.NOMBRE, Orientacion.VERTICAL, TipoUnidad.PORTAAVIONES.TAMANO));

        // Crear 2 naves de tamaño 3
        unidades.add(new ModeloUnidad(3, TipoUnidad.CRUCERO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.CRUCERO.TAMANO));
        unidades.add(new ModeloUnidad(4, TipoUnidad.CRUCERO.NOMBRE, Orientacion.VERTICAL, TipoUnidad.CRUCERO.TAMANO));

        // Crear 2 naves de tamaño 2
        unidades.add(new ModeloUnidad(5, TipoUnidad.SUBMARINO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.SUBMARINO.TAMANO));
        unidades.add(new ModeloUnidad(6, TipoUnidad.SUBMARINO.NOMBRE, Orientacion.VERTICAL, TipoUnidad.SUBMARINO.TAMANO));

        // Crear 3 naves de tamaño 1
        unidades.add(new ModeloUnidad(7, TipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.BARCO.TAMANO));
        unidades.add(new ModeloUnidad(8, TipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.BARCO.TAMANO));
        unidades.add(new ModeloUnidad(9, TipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.BARCO.TAMANO));

        // Definir las posiciones iniciales de las naves
        Map<ModeloUnidad, ModeloCasilla> posicionesIniciales = new HashMap<>();

        posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 1
        posicionesIniciales.put(unidades.get(1), modeloTablero.getCasilla(6, 0)); // Nave 2
        posicionesIniciales.put(unidades.get(2), modeloTablero.getCasilla(2, 5)); // Nave 3
        posicionesIniciales.put(unidades.get(3), modeloTablero.getCasilla(5, 7)); // Nave 4
        posicionesIniciales.put(unidades.get(4), modeloTablero.getCasilla(8, 2)); // Nave 5

        // Reposicionamos la unidad 6 a una nueva posición
        posicionesIniciales.put(unidades.get(5), modeloTablero.getCasilla(3, 0)); // Nave 6

        // Posiciones para las naves de tamaño 1
        posicionesIniciales.put(unidades.get(6), modeloTablero.getCasilla(9, 9)); // Nave 7
        posicionesIniciales.put(unidades.get(7), modeloTablero.getCasilla(3, 3)); // Nave 8
        posicionesIniciales.put(unidades.get(8), modeloTablero.getCasilla(7, 5)); // Nave 9

        // Colocar las naves en el tablero
        for (ModeloUnidad unidad : unidades) {
            ModeloCasilla casillaAncla = posicionesIniciales.get(unidad);

            Set<ModeloCasilla> casillasOcupadas = calcularCasillasUnidad(casillaAncla, unidad);

            if (casillasOcupadas != null) {
                // Creamos la ubicación de la unidad antes de verificar si puede ser colocada
                MUbicacionUnidad ubicacionUnidad = new MUbicacionUnidad(unidad, casillasOcupadas);

                if (puedeColocarUnidad(casillasOcupadas, ubicacionUnidad)) {
                    colocarUnidad(ubicacionUnidad);
                } else {
                    // Manejar el caso en que no se pueda colocar la unidad
                    System.out.println("No se pudo colocar la unidad: " + unidad.getNumNave());
                }
            } else {
                System.out.println("No se pudo calcular las casillas para la unidad: " + unidad.getNumNave());
            }
        }

        // Actualizar la vista
        vista.actualizarVista();
    }

    public ModeloTablero getModeloTablero() {
        return modeloTablero;
    }

    public void enviarAtaque(int fila, int columna) {
        // envia el ataque al servidor
        ClientConnection.getInstance().atacar(fila, columna);
    }
    
    

}
