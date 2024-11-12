package vista;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTextField;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaBuscarPartida implements EstadoJuego {

    private PanelJuego panelJuego;
    private JButton botonContinuar;
    private JButton botonSalir;
    private JTextField campoSala;

    public VistaBuscarPartida(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.botonContinuar = UtilesVista.crearBoton("Continuar");
        this.botonSalir = UtilesVista.crearBoton("Regresar");
        this.campoSala = UtilesVista.crearCampoTexto(20);
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
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonContinuar.addActionListener(e -> {
            panelJuego.quitarComponente(botonContinuar);
            panelJuego.quitarComponente(botonSalir);
            panelJuego.quitarComponente(campoSala);
            EstadosJuego.estado = EstadosJuego.SALA_ESPERA; // Cambiar el estado
        });
        // Agregar acción al botón
        botonSalir.addActionListener(e -> {
            panelJuego.quitarComponente(botonContinuar);
            panelJuego.quitarComponente(botonSalir);
            panelJuego.quitarComponente(campoSala);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
        });
    }

}
