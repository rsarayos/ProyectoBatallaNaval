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

    public VistaMenu(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }
    
    @Override
    public void dibujar(Graphics g) {
        // Dibujar el fondo
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        // Dibujar el texto
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("MENU PRINCIPAL", 50, 50);
        
        JButton botonIniciar = new JButton("Crear partida");
        panelJuego.agregarComponente(botonIniciar, 150, 120, 150, 30);
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
