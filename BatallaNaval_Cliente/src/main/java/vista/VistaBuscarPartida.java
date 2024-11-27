package vista;

import ivistas.IVistaBuscarPartida;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import presentador.Juego;
import presentador.PresentadorBuscarPartida;

/**
 * Clase que representa la vista para buscar una partida. Implementa las interfaces IVistasPanel e IVistaBuscarPartida.
 * Proporciona la interfaz gráfica donde el jugador puede ingresar el código de la partida a la que desea unirse.
 *
 * @author alex_
 */
public class VistaBuscarPartida implements IVistasPanel, IVistaBuscarPartida {

    
    /**
     * El panel principal del juego donde se agregarán los componentes de la vista.
     */
    private PanelJuego panelJuego;
    
    /**
     * Botón para continuar y unirse a la partida.
     */
    private JButton botonContinuar;
    
    /**
     * Botón para salir y regresar al menú principal.
     */
    private JButton botonSalir;
    
    /**
     * Campo de texto donde el jugador ingresa el código de la sala.
     */
    private JTextField campoSala;
    
    /**
     * Presentador para gestionar la lógica de la vista de buscar partida.
     */
    private PresentadorBuscarPartida presentador;

    /**
     * Constructor de la clase VistaBuscarPartida.
     *
     * @param panelJuego El panel principal del juego donde se agregarán los componentes de la vista.
     * @param juego La instancia del juego actual.
     */
    public VistaBuscarPartida(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorBuscarPartida(this, juego);
        crearComponentes();
        accionesComponentes();
    }

    /**
     * Dibuja la vista de buscar partida en el panel de juego.
     *
     * @param g El objeto Graphics utilizado para dibujar los elementos gráficos.
     */
    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "BUSCAR PARTIDA", 60, UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Introduce el codigo de la partida que deseas unirte", 150, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "para que se pueda unir a la sala", 180, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Codigo de la sala:", 250, UtilesVista.FUENTE_SUBTITULO);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonContinuar)) {
            panelJuego.agregarComponente(botonContinuar, (Juego.GAME_ANCHO - 500) / 2, Juego.GAME_ALTO - 150, 200, 40);
        }
        if (!panelJuego.isAncestorOf(botonSalir)) {
            panelJuego.agregarComponente(botonSalir, (Juego.GAME_ANCHO + 150) / 2, Juego.GAME_ALTO - 150, 200, 40);
        }
        if (!panelJuego.isAncestorOf(campoSala)) {
            panelJuego.agregarComponente(campoSala, (Juego.GAME_ANCHO - 200) / 2, 300, 200, 30);
        }

    }

    /**
     * Crea los componentes necesarios para la vista de buscar partida, como los botones y el campo de texto.
     */
    @Override
    public void crearComponentes() {
        botonContinuar = UtilesVista.crearBoton("Continuar");
        botonSalir = UtilesVista.crearBoton("Regresar");
        campoSala = UtilesVista.crearCampoTexto(20);
    }

    /**
     * Define las acciones para los componentes de la vista, como los botones de continuar y salir.
     */
    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            presentador.unirseAPartida();
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            presentador.regresarAlMenu();
        });
    }

    /**
     * Quita los componentes de la vista de buscar partida del panel de juego.
     */
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonContinuar);
        panelJuego.quitarComponente(botonSalir);
        panelJuego.quitarComponente(campoSala);
    }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     *
     * @param mensaje El mensaje informativo a mostrar.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Obtiene el código de acceso ingresado en el campo de texto.
     *
     * @return El código de acceso ingresado.
     */
    @Override
    public String obtenerCodigoAcceso() {
        return campoSala.getText().trim();
    }

    /**
     * Navega a la sala de espera al quitar los componentes de la vista actual.
     */
    @Override
    public void navegarASalaDeEspera() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
    }

    /**
     * Navega al menú principal al quitar los componentes de la vista actual.
     */
    @Override
    public void navegarAMenu() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.MENU;
    }

    /**
     * Obtiene el presentador asociado a esta vista.
     *
     * @return El presentador de buscar partida.
     */
    @Override
    public PresentadorBuscarPartida getPresentador() {
        return presentador;
    }

}
