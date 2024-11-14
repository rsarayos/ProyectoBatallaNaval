package vista;

import comunicacion.ClientConnection;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaSalaEspera implements EstadoJuego {
    
    private PanelJuego panelJuego;
    private JButton botonContinuar;
    private JButton botonSalir;
    private JTable listaJugadores;
    private DefaultTableModel modeloTabla;
    private String codigoAcceso;
    private String idJugador;
    private boolean estoyListo = false;
    
    public VistaSalaEspera(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.botonContinuar = UtilesVista.crearBoton("Continuar");
        this.botonSalir = UtilesVista.crearBoton("Regresar");

        // Inicializar modelo de tabla
        String[] columnas = {"Nombre de Jugador", "Listo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        this.listaJugadores = new JTable(modeloTabla);
        
        accionesComponentes();
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "SALA DE ESPERA", 60, UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Proporciona el código que se muestra debajo a otro jugador", 150, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "para que se pueda unir a esta sala", 180, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Código de la sala:", 250, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, codigoAcceso != null ? codigoAcceso : "Esperando...", 280, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Lista de Jugadores en la sala", 410, UtilesVista.FUENTE_SUBTITULO);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonContinuar)) {
            panelJuego.agregarComponente(botonContinuar, (Juego.GAME_ANCHO - 500) / 2, Juego.GAME_ALTO - 150, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonSalir)) {
            panelJuego.agregarComponente(botonSalir, (Juego.GAME_ANCHO + 150) / 2, Juego.GAME_ALTO - 150, 200, 40);
        }
        if (!panelJuego.isAncestorOf(listaJugadores)) {
            panelJuego.agregarComponente(listaJugadores, (Juego.GAME_ANCHO - 400) / 2, Juego.GAME_ALTO - 300, 400, 60);
        }

    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            if (!estoyListo) {
                estoyListo = true;
                botonContinuar.setEnabled(false); // Deshabilitar el botón para evitar múltiples clics
                // Enviar al servidor que este jugador está listo
                ClientConnection.getInstance().jugadorListo();
            }
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            panelJuego.quitarComponente(botonContinuar);
            panelJuego.quitarComponente(botonSalir);
            panelJuego.quitarComponente(listaJugadores);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
        });
    }
    
    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
        // Actualizar la interfaz gráfica
        panelJuego.repaint();
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public void agregarJugador(String nombreJugador) {
        modeloTabla.addRow(new Object[]{nombreJugador});
    }

    public void limpiarListaJugadores() {
        modeloTabla.setRowCount(0);
    }
    
    public void agregarOActualizarJugador(String nombreJugador, boolean listo) {
        boolean encontrado = false;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(nombreJugador)) {
                // Actualizar el estado "Listo"
                modeloTabla.setValueAt(listo ? "Sí" : "No", i, 1);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            // Agregar el jugador a la tabla
            modeloTabla.addRow(new Object[]{nombreJugador, listo ? "Sí" : "No"});
        }
    }

    public void limpiarComponentes() {
        panelJuego.quitarComponente(botonContinuar);
        panelJuego.quitarComponente(botonSalir);
        panelJuego.quitarComponente(listaJugadores);
    }

}
