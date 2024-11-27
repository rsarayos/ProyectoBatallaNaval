package vista;

/**
 * Enumeración que representa los diferentes estados del juego.
 *
 * @author alex_
 */
public enum EstadosJuego {
    BIENVENIDA,MENU,ORGANIZAR,INSTRUCCIONES,SALA_ESPERA,BUSCAR_PARTIDA, JUGAR;
    
    /**
     * Variable estática que representa el estado actual del juego.
     */
    public static EstadosJuego estado = BIENVENIDA;
}
