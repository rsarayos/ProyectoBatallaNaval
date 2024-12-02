package vista;

import ivistas.IVistaEstadisticas;
import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import presentador.PresentadorEstadisticas;

/**
 *
 * @author alex_
 */
public class VistaEstadisticas implements IVistasPanel, IVistaEstadisticas {

    private PanelJuego panelJuego;
    private PresentadorEstadisticas presentador;
    private JTable tablaEstadisticas;
    private JButton btnVolverAJugar;
    private JButton btnSalir;
    private Map<String, Object> estadisticas;

    public VistaEstadisticas(PanelJuego panelJuego, Map<String, Object> estadisticas) {
        this.panelJuego = panelJuego;
        this.estadisticas = estadisticas;
        this.presentador = new PresentadorEstadisticas(this);
        crearComponentes();
    }

    @Override
    public void crearComponentes() {
        // Crear la tabla de estadísticas
        crearTablaEstadisticas();

        // Crear los botones
        btnVolverAJugar = new JButton("Volver a Jugar");
        btnSalir = new JButton("Salir al Menú");

        // Configurar los componentes y agregarlos al panel
        configurarComponentes();
    }

    @Override
    public void accionesComponentes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void dibujar(Graphics g) {
        // Título
        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        g.setFont(UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Estadísticas de la Partida", 50, UtilesVista.FUENTE_TITULO);
    }

    @Override
    public void quitarComponentes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void volverAJugar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void crearTablaEstadisticas() {
        // Obtener los nombres de los jugadores y las estadísticas
        String[] nombresJugadores = obtenerNombresJugadores();
        String[] nombresEstadisticas = {"Naves Destruidas", "Naves Restantes", "Disparos Acertados", "Disparos Fallados", "Disparos Totales", "Tiempo de Partida"};

        // Crear el modelo de la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel(nombresEstadisticas, 0);

        // Agregar los datos al modelo
        Object[][] datos = obtenerDatosEstadisticas(nombresEstadisticas);
        for (int i = 0; i < datos.length; i++) {
            modeloTabla.addRow(datos[i]);
        }

        // Crear la tabla
        tablaEstadisticas = new JTable(modeloTabla);

        // Configurar la tabla
        tablaEstadisticas.setEnabled(false);
    }

    private String[] obtenerNombresJugadores() {
        // Obtener los nombres de los jugadores de las estadísticas
        // Asumiendo que las claves son los IDs de los jugadores
        Set<String> idsJugadores = estadisticas.keySet();
        String[] nombres = new String[idsJugadores.size()];
        int index = 0;
        for (String id : idsJugadores) {
            Map<String, Object> statsJugador = (Map<String, Object>) estadisticas.get(id);
            nombres[index++] = (String) statsJugador.get("nombre");
        }
        return nombres;
    }

    private Object[][] obtenerDatosEstadisticas(String[] nombresEstadisticas) {
        // Obtener los datos de las estadísticas para cada jugador
        Set<String> idsJugadores = estadisticas.keySet();
        int numJugadores = idsJugadores.size();
        int numEstadisticas = nombresEstadisticas.length;

        Object[][] datos = new Object[numEstadisticas][numJugadores + 1]; // +1 para el nombre de la estadística

        int col = 0;
        for (String id : idsJugadores) {
            Map<String, Object> statsJugador = (Map<String, Object>) estadisticas.get(id);
            int row = 0;
            for (String nombreEstadistica : nombresEstadisticas) {
                if (col == 0) {
                    // Primera columna, agregar el nombre de la estadística
                    datos[row][col] = nombreEstadistica;
                }
                // Agregar el valor de la estadística para el jugador
                datos[row][col + 1] = statsJugador.get(nombreEstadistica.toLowerCase().replace(" ", "_"));
                row++;
            }
            col++;
        }

        return datos;
    }

    private void configurarComponentes() {
        // Configurar y agregar la tabla
        JScrollPane scrollPane = new JScrollPane(tablaEstadisticas);
        scrollPane.setBounds(100, 100, 600, 200);
        panelJuego.agregarComponente(tablaEstadisticas, 100, 100, 600, 200);

//        // Configurar y agregar los botones
//        btnVolverAJugar.setBounds(250, 350, 150, 40);
//        panelJuego.agregarComponente(btnVolverAJugar, 250, 350, 150, 40);
//
//        btnSalir.setBounds(450, 350, 150, 40);
//        panelJuego.agregarComponente(btnSalir, 450, 350, 150, 40);

        panelJuego.repaint();
    }

}
