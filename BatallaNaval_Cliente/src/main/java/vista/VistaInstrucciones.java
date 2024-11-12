package vista;

import java.awt.Graphics;
import javax.swing.JButton;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class VistaInstrucciones implements EstadoJuego {
    
    private PanelJuego panelJuego;
    private JButton botonRegresar;
    
    public VistaInstrucciones(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        this.botonRegresar = UtilesVista.crearBoton("Regresar");
        accionesComponentes();
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(UtilesVista.COLOR_FONDO);
        g.fillRect(0, 0, Juego.GAME_ANCHO, Juego.GAME_ALTO);

        g.setColor(UtilesVista.COLOR_TEXTO_AZUL_OSCURO);
        UtilesVista.dibujarTextoCentrado(g, "INSTRUCCIONES", 60, UtilesVista.FUENTE_TITULO);
        UtilesVista.dibujarTextoCentrado(g, "Inicio del Juego", 100, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Selecciona \"Crear Partida\" en el menú principal para", 120, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "comenzar una partida e invitar a otro jugador. Seleccionar", 140, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "\"Unirse a Partida\" para unirte mediante un codigo a otro jugador", 160, UtilesVista.FUENTE_CAMPO_TEXTO);
        
        UtilesVista.dibujarTextoCentrado(g, "Configurar el Tablero", 190, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Cada jugador debe colocar sus unidades en su tablero", 210, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "Las unidades pueden ser colocadas en cualquier", 230, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "casilla, pero no deben estar adyacentes o sobre sí.", 250, UtilesVista.FUENTE_CAMPO_TEXTO);
        
        UtilesVista.dibujarTextoCentrado(g, "Turnos de Juego", 280, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "El juego se juega por turnos. Cada jugador tiene un turno", 300, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "para atacar. En tu turno, selecciona una casilla en el tablero", 320, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "del oponente para disparar.", 340, UtilesVista.FUENTE_CAMPO_TEXTO);
        
        UtilesVista.dibujarTextoCentrado(g, "Realizar un Ataque", 370, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Haz clic en la casilla que deseas atacar. El sistema te ", 390, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "informará si el ataque fue exitoso o fallido. Si hundes una", 410, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "unidad, el sistema mostrará un mensaje de éxito.", 430, UtilesVista.FUENTE_CAMPO_TEXTO);
        
        UtilesVista.dibujarTextoCentrado(g, "Finalizar el Turno", 460, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "Después de atacar, el turno pasa al oponente", 480, UtilesVista.FUENTE_CAMPO_TEXTO);
        UtilesVista.dibujarTextoCentrado(g, "automáticamente. Espera a que el oponente realice su turno", 500, UtilesVista.FUENTE_CAMPO_TEXTO);
        
        UtilesVista.dibujarTextoCentrado(g, "El jugador que logre hundir todas las naves del oponente", 590, UtilesVista.FUENTE_SUBTITULO);
        UtilesVista.dibujarTextoCentrado(g, "primero sera el ganador de la partida", 620, UtilesVista.FUENTE_SUBTITULO);

        // Agregar componentes al panel si no están ya agregados
        if (!panelJuego.isAncestorOf(botonRegresar)) {
            panelJuego.agregarComponente(botonRegresar, (Juego.GAME_ANCHO - 200) / 2, Juego.GAME_ALTO - 80, 200, 40);
        }
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void accionesComponentes() {
        // Agregar acción al botón
        botonRegresar.addActionListener(e -> {
            panelJuego.quitarComponente(botonRegresar);
            EstadosJuego.estado = EstadosJuego.MENU; // Cambiar el estado
        });
    }

}
