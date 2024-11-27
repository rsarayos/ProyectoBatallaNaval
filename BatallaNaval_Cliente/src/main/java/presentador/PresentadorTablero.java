package presentador;

import comunicacion.ClientConnection;
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
 * Clase que actúa como presentador de la vista del tablero, manejando la lógica de interacción con el tablero y las unidades.
 *
 * @author alex_
 */
public class PresentadorTablero {

    /**
     * Modelo del tablero del juego.
     */
    private ModeloTablero modeloTablero;
    
    /**
     * Vista del tablero del juego.
     */
    private VistaTablero vista;

    /**
     * Indica si se está arrastrando una unidad en el tablero.
     */
    private boolean isDragging;
    
    /**
     * Casilla inicial donde se comenzó la interacción (arrastre o selección).
     */
    private ModeloCasilla casillaInicial;
    
    /**
     * Unidad seleccionada para ser movida o rotada.
     */
    private MUbicacionUnidad unidadSeleccionada;
    
    /**
     * Conjunto de casillas originales que ocupaba la unidad antes de ser movida.
     */
    private Set<ModeloCasilla> casillasOriginales;
    
    /**
     * Orientación original de la unidad antes de cualquier operación de rotación.
     */
    private Orientacion orientacionOriginal;
    
    /**
     * Conjunto de casillas resaltadas durante la interacción para indicar las posiciones posibles.
     */
    private Set<ModeloCasilla> celdasResaltadas = new HashSet<>();

    /**
     * Listener para los eventos de ataque.
     */
    private AtaqueListener ataqueListener;

    /**
     * Constructor que inicializa el presentador con la vista especificada.
     *
     * @param vista la vista del tablero
     */
    public PresentadorTablero(VistaTablero vista) {
        this.vista = vista;
        this.modeloTablero = new ModeloTablero();
        inicializarNaves();
    }

    /**
     * Maneja el evento cuando se presiona el ratón en el tablero.
     *
     * @param e el evento de ratón
     */
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

    /**
     * Maneja el evento cuando se suelta el ratón en el tablero.
     *
     * @param e el evento de ratón
     */
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

            // Notificar al modelo que se actualizó
            modeloTablero.actualizarTablero();
        }
    }

    /**
     * Maneja el evento cuando se arrastra el ratón en el tablero.
     *
     * @param e el evento de ratón
     */
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

                // Notificar al modelo que se actualizó
                modeloTablero.actualizarTablero();
            }
        }
    }

    /**
     * Rota la unidad especificada en el tablero.
     *
     * @param ubicacionUnidad la unidad a rotar
     */
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
        // Notificar al modelo que se actualizó
        modeloTablero.actualizarTablero();
    }

    /**
     * Mueve la unidad especificada a una nueva casilla en el tablero.
     *
     * @param ubicacionUnidad la unidad a mover
     * @param nuevaCasillaAncla la nueva casilla ancla para la unidad
     */
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

    /**
     * Calcula las casillas que ocupará una unidad en base a una casilla ancla.
     *
     * @param casillaAncla la casilla ancla
     * @param unidad la unidad a colocar
     * @return un conjunto de casillas que ocupará la unidad, o null si no es posible colocarla
     */
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

    /**
     * Verifica si una unidad se puede colocar en un conjunto de casillas especificado.
     *
     * @param casillas el conjunto de casillas
     * @param unidadActual la unidad que se desea colocar
     * @return true si la unidad se puede colocar, false en caso contrario
     */
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

    /**
     * Coloca una unidad en el tablero en las casillas especificadas.
     *
     * @param ubicacionUnidad la unidad a colocar
     */
    private void colocarUnidad(MUbicacionUnidad ubicacionUnidad) {
        modeloTablero.getUnidades().add(ubicacionUnidad);
        for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
            casilla.setUnidadOcupante(ubicacionUnidad);
        }
        marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, true);
        // Notificar al modelo que se actualizó
        modeloTablero.actualizarTablero();
    }

    /**
     * Remueve una unidad del tablero.
     *
     * @param ubicacionUnidad la unidad a remover
     */
    private void removerUnidad(MUbicacionUnidad ubicacionUnidad) {
        modeloTablero.getUnidades().remove(ubicacionUnidad);
        for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
            casilla.setUnidadOcupante(null);
        }
        marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, false);
        // Notificar al modelo que se actualizó
        modeloTablero.actualizarTablero();
    }

    /**
     * Limpia todas las adyacencias en el tablero.
     */
    private void limpiarAdyacencias() {
        ModeloCasilla[][] casillas = modeloTablero.getCasillas();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j].getNavesAdyacentes().clear();
            }
        }
    }

    /**
     * Remarca las adyacencias de todas las unidades en el tablero.
     */
    private void remarcarAdyacencias() {
        for (MUbicacionUnidad ubicacionUnidad : modeloTablero.getUnidades()) {
            marcarAdyacentes(ubicacionUnidad.getCasillasOcupadas(), ubicacionUnidad, true);
        }
    }

    /**
     * Marca o desmarca las casillas adyacentes a un conjunto de casillas especificado.
     *
     * @param casillas el conjunto de casillas
     * @param unidad la unidad para la cual se marcarán las adyacencias
     * @param marcar true para marcar, false para desmarcar
     */
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

    /**
     * Obtiene las casillas adyacentes a una casilla especificada.
     *
     * @param casilla la casilla para la cual se desean obtener las adyacencias
     * @return un conjunto de casillas adyacentes
     */
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

    /**
     * Inicializa las naves en el tablero en sus posiciones predeterminadas.
     */
    private void inicializarNaves() {
        // Lista para almacenar las unidades creadas
        List<ModeloUnidad> unidades = new ArrayList<>();

        // Crear 2 naves de tamaño 4
        unidades.add(new ModeloUnidad(1, TipoUnidad.PORTAAVIONES.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.PORTAAVIONES.TAMANO));
        unidades.add(new ModeloUnidad(2, TipoUnidad.PORTAAVIONES.NOMBRE, Orientacion.VERTICAL, TipoUnidad.PORTAAVIONES.TAMANO));

        // Crear 2 naves de tamaño 3
        unidades.add(new ModeloUnidad(3, TipoUnidad.CRUCERO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.CRUCERO.TAMANO));
        unidades.add(new ModeloUnidad(4, TipoUnidad.CRUCERO.NOMBRE, Orientacion.VERTICAL, TipoUnidad.CRUCERO.TAMANO));

        // Crear 4 naves de tamaño 2
        unidades.add(new ModeloUnidad(5, TipoUnidad.SUBMARINO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.SUBMARINO.TAMANO));
        unidades.add(new ModeloUnidad(6, TipoUnidad.SUBMARINO.NOMBRE, Orientacion.VERTICAL, TipoUnidad.SUBMARINO.TAMANO));
        unidades.add(new ModeloUnidad(7, TipoUnidad.SUBMARINO.NOMBRE, Orientacion.VERTICAL, TipoUnidad.SUBMARINO.TAMANO));
        unidades.add(new ModeloUnidad(8, TipoUnidad.SUBMARINO.NOMBRE, Orientacion.VERTICAL, TipoUnidad.SUBMARINO.TAMANO));

        // Crear 3 naves de tamaño 1
        unidades.add(new ModeloUnidad(9, TipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.BARCO.TAMANO));
        unidades.add(new ModeloUnidad(10, TipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.BARCO.TAMANO));
        unidades.add(new ModeloUnidad(11, TipoUnidad.BARCO.NOMBRE, Orientacion.HORIZONTAL, TipoUnidad.BARCO.TAMANO));

        // Definir las posiciones iniciales de las naves
        Map<ModeloUnidad, ModeloCasilla> posicionesIniciales = new HashMap<>();

        posicionesIniciales.put(unidades.get(0), modeloTablero.getCasilla(0, 0)); // Nave 1
        posicionesIniciales.put(unidades.get(1), modeloTablero.getCasilla(6, 0)); // Nave 2
        posicionesIniciales.put(unidades.get(2), modeloTablero.getCasilla(2, 5)); // Nave 3
        posicionesIniciales.put(unidades.get(3), modeloTablero.getCasilla(5, 7)); // Nave 4
        posicionesIniciales.put(unidades.get(4), modeloTablero.getCasilla(8, 2)); // Nave 5
        posicionesIniciales.put(unidades.get(5), modeloTablero.getCasilla(3, 0)); // Nave 6
        posicionesIniciales.put(unidades.get(6), modeloTablero.getCasilla(5, 2)); // Nave 7
        posicionesIniciales.put(unidades.get(7), modeloTablero.getCasilla(3, 9)); // Nave 8

        // Posiciones para las naves de tamaño 1
        posicionesIniciales.put(unidades.get(8), modeloTablero.getCasilla(9, 9)); // Nave 9
        posicionesIniciales.put(unidades.get(9), modeloTablero.getCasilla(3, 3)); // Nave 10
        posicionesIniciales.put(unidades.get(10), modeloTablero.getCasilla(7, 5)); // Nave 11

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

    /**
     * Obtiene el modelo del tablero del juego.
     *
     * @return el modelo del tablero
     */
    public ModeloTablero getModeloTablero() {
        return modeloTablero;
    }

    /**
     * Envía un ataque al servidor en las coordenadas especificadas.
     *
     * @param fila la coordenada de la fila
     * @param columna la coordenada de la columna
     */
    public void enviarAtaque(int fila, int columna) {
        // envia el ataque al servidor
        ClientConnection.getInstance().atacar(fila, columna);
    }

    /**
     * Establece el listener para los eventos de ataque.
     *
     * @param listener el listener de ataque
     */
    public void setAtaqueListener(AtaqueListener listener) {
        this.ataqueListener = listener;
    }

    /**
     * Obtiene el listener para los eventos de ataque.
     *
     * @return el listener de ataque
     */
    public AtaqueListener getAtaqueListener() {
        return ataqueListener;
    }

}
