package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTextField;
import presentador.Juego;
import static vista.EstadosJuego.MENU;

/**
 *
 * @author alex_
 */
public class VistaBienvenida implements EstadoJuego {
    
    private PanelJuego panelJuego;
    private JTextField campoNombre;
    private JButton botonIniciar;

    public VistaBienvenida(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        // Crear los componentes una sola vez
        campoNombre = new JTextField(20);
        botonIniciar = new JButton("Iniciar Juego");

        // Agregar acción al botón
        botonIniciar.addActionListener(e -> {
            panelJuego.quitarComponente(campoNombre);
            panelJuego.quitarComponente(botonIniciar);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
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
        g.drawString("Bienvenido a Batalla Naval", (Juego.GAME_ANCHO / 3), 50);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(campoNombre)) {
            panelJuego.agregarComponente(campoNombre, 80, 80, 150, 30);
        }
        if (!panelJuego.isAncestorOf(botonIniciar)) {
            panelJuego.agregarComponente(botonIniciar, 80, 120, 150, 30);
        }
        
    }

    @Override
    public void actualizar() {
        
        
        
    }
    
}
