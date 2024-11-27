package presentador;

import comunicacion.ClientConnection;
import ivistas.IVistaOrganizar;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelo.MUbicacionUnidad;
import modelo.ModeloCasilla;
import modelo.ModeloJugador;
import modelo.ModeloTablero;
import modelo.ModeloUnidad;
import vista.UtilesVista;
import vista.VistaTablero;

/**
 * Clase que actúa como presentador de la vista de organización del juego, manejando la lógica de organización de unidades y comunicación con el servidor.
 *
 * @author alex_
 */
public class PresentadorOrganizar {

    /**
     * Vista de organización.
     */
    private IVistaOrganizar vista;
    
    /**
     * Modelo del jugador.
     */
    private ModeloJugador modeloJugador;
    
    /**
     * Conexión con el servidor.
     */
    private ClientConnection clientConnection;

    /**
     * Constructor que inicializa el presentador con la vista especificada.
     *
     * @param vista la vista de organización
     */
    public PresentadorOrganizar(IVistaOrganizar vista) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.clientConnection = ClientConnection.getInstance();
    }

    /**
     * Envía las unidades del jugador al servidor.
     */
    public void enviarUnidadesAlServidor() {
        VistaTablero tablero = vista.getTablero();
        ModeloTablero modeloTablero = tablero.getPresentador().getModeloTablero();

        // Obtener las unidades
        Set<MUbicacionUnidad> unidades = modeloTablero.getUnidades();

        // Serializar las unidades
        List<Map<String, Object>> unidadesData = new ArrayList<>();

        for (MUbicacionUnidad ubicacionUnidad : unidades) {
            Map<String, Object> unidadData = new HashMap<>();
            ModeloUnidad unidad = ubicacionUnidad.getUnidad();

            unidadData.put("numNave", unidad.getNumNave());

            // Obtener las coordenadas
            List<Map<String, Integer>> coordenadas = new ArrayList<>();
            for (ModeloCasilla casilla : ubicacionUnidad.getCasillasOcupadas()) {
                Map<String, Integer> coordenada = new HashMap<>();
                coordenada.put("x", casilla.getCoordenada().getX());
                coordenada.put("y", casilla.getCoordenada().getY());
                coordenadas.add(coordenada);
            }
            unidadData.put("coordenadas", coordenadas);

            unidadesData.add(unidadData);
        }

        // Enviar las unidades al servidor
        clientConnection.enviarUnidades(unidadesData);

        // Bloquear la interfaz si es necesario
        vista.bloquearInterfaz();
    }

    /**
     * Cambia el color de las naves en la vista de organización.
     *
     * @param nombreColor el nombre del nuevo color de las naves
     */
    public void cambiarColorNaves(String nombreColor) {
        Color nuevoColorNave = UtilesVista.obtenerColorBarco(nombreColor);
        vista.getTablero().setColorNave(nuevoColorNave);
        // Podrías agregar un método en la vista para actualizar los paneles laterales
        vista.actualizarColorPanelesLaterales(nuevoColorNave);
    }

    /**
     * Muestra un mensaje indicando que un jugador está esperando.
     *
     * @param nombreJugador el nombre del jugador que está esperando
     */
    public void manejarJugadorEsperando(String nombreJugador) {
        vista.mostrarMensajeJugadorEsperando(nombreJugador);
    }

    /**
     * Inicia el juego cambiando a la vista de juego.
     */
    public void manejarIniciarJuego() {
        vista.navegarAJugar();
    }

}
