package vista;

import estados.EstadoMenu;
import estados.EstadoOrganizar;
import ivistas.IVistaSalaEspera;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import presentador.Juego;
import presentador.PresentadorSalaEspera;

/**
 * Clase que representa la vista de la sala de espera, donde los jugadores esperan a que se unan más participantes antes de empezar el juego.
 * Esta clase gestiona la interfaz gráfica, mostrando el código de acceso de la sala y la lista de jugadores conectados.
 *
 * @author alex_
 */
public class VistaSalaEspera implements IVistasPanel, IVistaSalaEspera {
    
    /**
     * Panel principal del juego donde se mostrarán los componentes de esta vista.
     */
    private PanelJuego panelJuego;
    
    /**
     * Botón para que el jugador indique que está listo para continuar.
     */
    private JButton botonContinuar;
    
    /**
     * Botón para que el jugador salga de la sala de espera.
     */
    private JButton botonSalir;
    
    /**
     * Tabla que muestra la lista de jugadores en la sala y su estado (listo o no listo).
     */
    private JTable listaJugadores;
    
    /**
     * Modelo de datos para la tabla de jugadores.
     */
    private DefaultTableModel modeloTabla;
    
    /**
     * Código de acceso de la sala que permite a otros jugadores unirse.
     */
    private String codigoAcceso;
    
    /**
     * Indica si el jugador actual está listo para continuar.
     */
    private boolean estoyListo = false;
    
    /**
     * Presentador asociado a la vista de la sala de espera, que maneja la lógica de negocio.
     */
    private PresentadorSalaEspera presentador;
    
    /**
     * Referencia al juego principal para cambiar de estado.
     */
    private Juego juego;
    
    /**
     * Constructor de la clase VistaSalaEspera.
     *
     * @param panelJuego Panel principal del juego donde se mostrarán los componentes de esta vista.
     * @param juego Referencia al juego principal.
     */
    public VistaSalaEspera(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorSalaEspera(this);
        this.juego = juego;
        crearComponentes();
        accionesComponentes();
        
    }

    /**
     * Dibuja la vista de la sala de espera, mostrando el código de acceso y la lista de jugadores.
     *
     * @param g Objeto Graphics para dibujar los componentes en el panel.
     */
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
    
    /**
     * Crea los componentes de la interfaz gráfica de la vista de sala de espera.
     */
    @Override
    public void crearComponentes() {
        botonContinuar = UtilesVista.crearBoton("Continuar");
        botonSalir = UtilesVista.crearBoton("Regresar");
        String[] columnas = {"Nombre de Jugador", "Listo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        listaJugadores = new JTable(modeloTabla);
    }

    /**
     * Define las acciones de los componentes de la vista de sala de espera.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            presentador.jugadorListo();
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            presentador.salir();
        });
    }
    
    /**
     * Elimina los componentes de la vista de la sala de espera del panel principal.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonContinuar);
        panelJuego.quitarComponente(botonSalir);
        panelJuego.quitarComponente(listaJugadores);
    }
    
    /**
     * Muestra el código de acceso de la sala en la interfaz gráfica.
     *
     * @param codigoAcceso Código de acceso de la sala.
     */
    @Override
    public void mostrarCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
        panelJuego.repaint();
    }

    /**
     * Agrega un jugador a la lista de jugadores en la sala.
     *
     * @param nombreJugador Nombre del jugador a agregar.
     */
    public void agregarJugador(String nombreJugador) {
        modeloTabla.addRow(new Object[]{nombreJugador});
    }
    
    /**
     * Agrega o actualiza un jugador en la lista de jugadores en la sala.
     *
     * @param nombreJugador Nombre del jugador.
     * @param listo Indica si el jugador está listo o no.
     */
    @Override
    public void agregarOActualizarJugador(String nombreJugador, boolean listo) {
        boolean encontrado = false;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(nombreJugador)) {
                // Actualizar el estado "Listo"
                modeloTabla.setValueAt(listo ? "Listo" : "No listo", i, 1);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            // Agregar el jugador a la tabla
            modeloTabla.addRow(new Object[]{nombreJugador, listo ? "Listo" : "No listo"});
        }
    }
    
    /**
     * Limpia la lista de jugadores en la sala.
     */
    @Override
    public void limpiarListaJugadores() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Deshabilita el botón de continuar para que el jugador no pueda presionarlo.
     */
    @Override
    public void bloquearBotonContinuar() {
        botonContinuar.setEnabled(false);
    }

    /**
     * Muestra un mensaje emergente al jugador.
     *
     * @param mensaje Mensaje a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje);
    }

    /**
     * Navega al menú principal del juego.
     */
    @Override
    public void navegarAMenu() {
        quitarComponentes();
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    /**
     * Navega a la vista para organizar el tablero del juego.
     */
    @Override
    public void navegarAOrganizar() {
        quitarComponentes();
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }

    /**
     * Verifica si el jugador está listo para continuar.
     *
     * @return {@code true} si el jugador está listo, de lo contrario {@code false}.
     */
    @Override
    public boolean isEstoyListo() {
        return estoyListo;
    }

    /**
     * Establece si el jugador está listo para continuar.
     *
     * @param listo {@code true} si el jugador está listo, de lo contrario {@code false}.
     */
    @Override
    public void setEstoyListo(boolean listo) {
        this.estoyListo = listo;
    }

    /**
     * Obtiene el presentador asociado a la vista de sala de espera.
     *
     * @return Presentador de la sala de espera.
     */
    @Override
    public PresentadorSalaEspera getPresentador() {
        return presentador;
    }

    /**
     * Establece el código de acceso de la sala.
     *
     * @param codigoAcceso Código de acceso de la sala.
     */
    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

}
