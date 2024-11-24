package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import presentador.Juego;

/**
 *
 * @author alex_
 */
public class UtilesVista {
    
    // Imagenes
    public static final String PORTADA = "/recursos/batallaNaval.png";
    public static final String FONDO_TABLERO = "/recursos/FondoTablero.png";
    
    // Colores componentes
    public static final Color COLOR_FONDO = new Color(139, 137, 126);
    public static final Color COLOR_TEXTO_AZUL_OSCURO = new Color(0, 30, 61);
    public static final Color COLOR_BOTON_FONDO = new Color(14, 47, 67);
    public static final Color COLOR_BOTON_TEXTO = new Color(218, 218, 211);
    public static final Color COLOR_CAMPO_TEXTO = new Color(218, 218, 211);
    public static final Color COLOR_CAMPO_TEXTO_FONDO = new Color(24, 25, 37);
    
    // Colores estados naves
    public static final Color COLOR_UNIDAD_SIN_DANO = new Color(19, 222, 33);
    public static final Color COLOR_UNIDAD_DANADA = new Color(237, 227, 21);
    public static final Color COLOR_UNIDAD_DESTRUIDA = new Color(252, 24, 24);
    
    // Colores Tablero
    public static final Color COLOR_CELDAS_INVALIDAS = new Color(255, 125, 125, 128);
    public static final Color COLOR_VISTA_PREVIEW = new Color(255, 255, 0, 128);
    public static final String[] LISTA_COLORES_BARCO = {"Rojo","Azul","Negro","Blanco","Verde"};
    public static final Color BARCO_ROJO = new Color(180, 0, 0);
    public static final Color BARCO_AZUL = new Color(0, 70, 180);
    public static final Color BARCO_NEGRO = new Color(30, 30, 30);
    public static final Color BARCO_BLANCO = new Color(250, 250, 250);
    public static final Color BARCO_VERDE = new Color(5, 117, 6);

    // Fuentes comunes
    public static final Font FUENTE_TITULO = new Font("Stencil", Font.PLAIN, 40);
    public static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FUENTE_CAMPO_TEXTO = new Font("SansSerif", Font.PLAIN, 16);
    public static final Font FUENTE_RESULTADO = new Font("Stencil", Font.PLAIN, 70);

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
    
    public static JTable crearTabla(Object[][] datos, String[] columnas, int ancho, int alto) {
        // Modelo de la tabla con los datos y las columnas especificadas
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(modelo);

        // Tamaño preferido de la tabla
        tabla.setPreferredScrollableViewportSize(new Dimension(ancho, alto));
        tabla.setFillsViewportHeight(true);

        // Configuración adicional si deseas personalizar la apariencia
        tabla.setRowHeight(30);  // Altura de las filas
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20)); // Fuente del encabezado
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 18));  // Fuente del contenido

        // Retornar el JTable creado
        return tabla;
    }
    
    public static JComboBox<String> crearComboBox(String[] elementos, int ancho, int alto) {
        JComboBox<String> comboBox = new JComboBox(elementos);

        // Configuración de tamaño
        comboBox.setPreferredSize(new Dimension(ancho, alto));

        // Configuración de fuente 
        comboBox.setFont(UtilesVista.FUENTE_CAMPO_TEXTO);
        
        // Configuración de colores
        comboBox.setBackground(COLOR_BOTON_FONDO);
        comboBox.setForeground(COLOR_BOTON_TEXTO);


        return comboBox;
    }
    
    public static JPanel crearBarcoVista(int ancho, int alto, Color colorFondo) {
        JPanel panel = new JPanel();
        
        // Establecer el tamaño preferido
        panel.setPreferredSize(new Dimension(ancho, alto));
        
        // Aplicar el color de fondo
        if (colorFondo != null) {
            panel.setBackground(colorFondo);
        }

        // Desactivar el diseño si quieres un panel de tamaño fijo sin variaciones
        panel.setLayout(null);

        return panel;
    }
    
    public static Color obtenerColorBarco(String nombreColor) {
        switch (nombreColor) {
            case "Rojo":
                return BARCO_ROJO;
            case "Azul":
                return BARCO_AZUL;
            case "Negro":
                return BARCO_NEGRO;
            case "Blanco":
                return BARCO_BLANCO;
            case "Verde":
                return BARCO_VERDE;
            default:
                return BARCO_NEGRO; // Color por defecto
        }
    }

}
