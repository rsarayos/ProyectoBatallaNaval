package vista;

/**
 *
 * @author alex_
 */
public enum EstadosJuego {
    BIENVENIDA,MENU,ORGANIZAR,INSTRUCCIONES,SALA_ESPERA,BUSCAR_PARTIDA;
    
    /**
     * Variable estática que representa el estado actual del juego.
     */
    public static EstadosJuego estado = BIENVENIDA;
}
