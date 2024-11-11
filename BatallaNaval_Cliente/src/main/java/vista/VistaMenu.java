package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaMenu implements EstadoJuego {

    private PanelJuego panelJuego;
    private JButton botonIniciar;

    public VistaMenu(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.botonIniciar = new JButton("Iniciar Juego");
        // Agregar acción al botón
        botonIniciar.addActionListener(e -> {
            panelJuego.quitarComponente(botonIniciar);
            EstadosJuego.estado = EstadosJuego.ORGANIZAR; // Cambiar el estado
        });
    }
    
    @Override
    public void dibujar(Graphics g) {
        // Dibujar el fondo
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        // Dibujar el texto
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Menu", (Juego.GAME_ANCHO / 3), 50);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonIniciar)) {
            panelJuego.agregarComponente(botonIniciar, 80, 120, 150, 30);
        }
        
    }

    @Override
    public void actualizar() {
        
    }
    
}
