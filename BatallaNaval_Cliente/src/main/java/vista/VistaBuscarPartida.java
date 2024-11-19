package vista;

import ivistas.IVistaBuscarPartida;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import presentador.Juego;
import presentador.PresentadorBuscarPartida;

/**
 *
 * @author alex_
 */
public class VistaBuscarPartida implements IVistasPanel, IVistaBuscarPartida {

    private PanelJuego panelJuego;
    private JButton botonContinuar;
    private JButton botonSalir;
    private JTextField campoSala;
    private PresentadorBuscarPartida presentador;

    public VistaBuscarPartida(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorBuscarPartida(this, juego);
        crearComponentes();
        accionesComponentes();
    }

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

    @Override
    public void crearComponentes() {
        botonContinuar = UtilesVista.crearBoton("Continuar");
        botonSalir = UtilesVista.crearBoton("Regresar");
        campoSala = UtilesVista.crearCampoTexto(20);
    }

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

    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonContinuar);
        panelJuego.quitarComponente(botonSalir);
        panelJuego.quitarComponente(campoSala);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String obtenerCodigoAcceso() {
        return campoSala.getText().trim();
    }

    @Override
    public void navegarASalaDeEspera() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.SALA_ESPERA;
    }

    @Override
    public void navegarAMenu() {
        quitarComponentes();
        EstadosJuego.estado = EstadosJuego.MENU;
    }

    @Override
    public PresentadorBuscarPartida getPresentador() {
        return presentador;
    }

}
