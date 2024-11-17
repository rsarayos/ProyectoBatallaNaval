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
 *
 * @author alex_
 */
public class PresentadorOrganizar {

    private IVistaOrganizar vista;
    private ModeloJugador modeloJugador;
    private ClientConnection clientConnection;

    public PresentadorOrganizar(IVistaOrganizar vista) {
        this.vista = vista;
        this.modeloJugador = ModeloJugador.getInstance();
        this.clientConnection = ClientConnection.getInstance();
    }

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

    public void cambiarColorNaves(String nombreColor) {
        Color nuevoColorNave = UtilesVista.obtenerColorBarco(nombreColor);
        vista.getTablero().setColorNave(nuevoColorNave);
        // Podrías agregar un método en la vista para actualizar los paneles laterales
        vista.actualizarColorPanelesLaterales(nuevoColorNave);
    }

    // Métodos para manejar mensajes del servidor
    public void manejarJugadorEsperando(String nombreJugador) {
        vista.mostrarMensajeJugadorEsperando(nombreJugador);
    }

    public void manejarIniciarJuego() {
        vista.navegarAJugar();
    }

}
