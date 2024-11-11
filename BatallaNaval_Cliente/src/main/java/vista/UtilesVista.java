package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class UtilesVista {
    
    // Imagenes
    public static final String PORTADA = "/recursos/batallaNaval.png";
    
    // Colores componentes
    public static final Color COLOR_FONDO = new Color(139, 137, 126);
    public static final Color COLOR_TEXTO_AZUL_OSCURO = new Color(0, 30, 61);
    public static final Color COLOR_BOTON_FONDO = new Color(14, 47, 67);
    public static final Color COLOR_BOTON_TEXTO = new Color(218, 218, 211);
    public static final Color COLOR_CAMPO_TEXTO = new Color(218, 218, 211);
    public static final Color COLOR_CAMPO_TEXTO_FONDO = new Color(24, 25, 37);

    // Fuentes comunes
    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.PLAIN, 40);
    public static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 20);
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FUENTE_CAMPO_TEXTO = new Font("SansSerif", Font.PLAIN, 16);

    /**
     * Método para dibujar texto centrado horizontalmente.
     *
     * @param g El objeto Graphics para dibujar
     * @param texto El texto a dibujar
     * @param y La posición vertical (y) en la que dibujar el texto
     * @param fuente La fuente a usar para el texto
     */
    public static void dibujarTextoCentrado(Graphics g, String texto, int y, Font fuente) {
        g.setFont(fuente);
        FontMetrics metrics = g.getFontMetrics(fuente);
        int textWidth = metrics.stringWidth(texto);
        int x = (Juego.GAME_ANCHO - textWidth) / 2;
        g.drawString(texto, x, y);
    }

    /**
     * Crea un botón personalizado con estilo consistente.
     *
     * @param texto Texto del botón
     * @return JButton con el estilo definido
     */
    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setBackground(COLOR_BOTON_FONDO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    /**
     * Crea un campo de texto personalizado con estilo consistente.
     *
     * @param anchoTexto Cantidad de caracteres de ancho
     * @return JTextField con el estilo definido
     */
    public static JTextField crearCampoTexto(int anchoTexto) {
        JTextField campoTexto = new JTextField(anchoTexto);
        campoTexto.setFont(FUENTE_CAMPO_TEXTO);
        campoTexto.setForeground(COLOR_CAMPO_TEXTO_FONDO);
        campoTexto.setBackground(COLOR_CAMPO_TEXTO);
        campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        return campoTexto;
    }
    
    public static BufferedImage cargarImagen(String directorio) {
        BufferedImage imagen = null;
        InputStream is = UtilesVista.class.getResourceAsStream(directorio);

        try {
            // Lee la imagen desde el flujo de entrada
            imagen = ImageIO.read(is);

        } catch (IOException ex) {
            // Manejo de excepciones en caso de error al cargar la imagen
            ex.getMessage();
        } finally {
            try {
                // Cierra el flujo de entrada una vez que se ha terminado de cargar la imagen
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // Devuelve la imagen cargada
        return imagen;
    }

}
