package vista;

import comunicacion.ClientConnection;
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
 *
 * @author alex_
 */
public class VistaSalaEspera implements IVistasPanel, IVistaSalaEspera {
    
    private PanelJuego panelJuego;
    private JButton botonContinuar;
    private JButton botonSalir;
    private JTable listaJugadores;
    private DefaultTableModel modeloTabla;
    private String codigoAcceso;
    private boolean estoyListo = false;
    private PresentadorSalaEspera presentador;
    private Juego juego;
    
    public VistaSalaEspera(PanelJuego panelJuego, Juego juego) {
        this.panelJuego = panelJuego;
        this.presentador = new PresentadorSalaEspera(this);
        this.juego = juego;
        crearComponentes();
        accionesComponentes();
        
    }

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
    
    @Override
    public void crearComponentes() {
        botonContinuar = UtilesVista.crearBoton("Continuar");
        botonSalir = UtilesVista.crearBoton("Regresar");
        String[] columnas = {"Nombre de Jugador", "Listo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        listaJugadores = new JTable(modeloTabla);
    }

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
    
    @Override
    public void quitarComponentes() {
        panelJuego.quitarComponente(botonContinuar);
        panelJuego.quitarComponente(botonSalir);
        panelJuego.quitarComponente(listaJugadores);
    }
    
    @Override
    public void mostrarCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
        panelJuego.repaint();
    }

    public void agregarJugador(String nombreJugador) {
        modeloTabla.addRow(new Object[]{nombreJugador});
    }
    
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
    
    @Override
    public void limpiarListaJugadores() {
        modeloTabla.setRowCount(0);
    }

    @Override
    public void bloquearBotonContinuar() {
        botonContinuar.setEnabled(false);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panelJuego, mensaje);
    }

    @Override
    public void navegarAMenu() {
        quitarComponentes();
        juego.cambiarEstado(new EstadoMenu(juego));
    }

    @Override
    public void navegarAOrganizar() {
        quitarComponentes();
        juego.cambiarEstado(new EstadoOrganizar(juego));
    }

    @Override
    public boolean isEstoyListo() {
        return estoyListo;
    }

    @Override
    public void setEstoyListo(boolean listo) {
        this.estoyListo = listo;
    }

    @Override
    public PresentadorSalaEspera getPresentador() {
        return presentador;
    }

    public void setCodigoAcceso(String codigoAcceso) {
        this.codigoAcceso = codigoAcceso;
    }

}
