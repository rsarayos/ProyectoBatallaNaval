package presentador;

/**
 * Clase que representa un hilo encargado de la renderización (dibujado) del
 * juego a una velocidad específica (FPS - Cuadros por segundo).
 *
 * @author alex_
 */
public class HiloRenderizado extends Thread {
    
    // Referencia al objeto Juego que se está renderizando.
    private Juego juego;
    // Tiempo objetivo entre renderizaciones.
    private long targetRenderTime;

    /**
     * Constructor de la clase HiloRenderizado.
     *
     * @param juego Referencia al objeto Juego que se va a renderizar.
     */
    public HiloRenderizado(Juego juego) {
        this.juego = juego;
        // Calcula el tiempo objetivo entre renderizaciones en nanosegundos.
        targetRenderTime = 1000000000 / Juego.FPS_SET;
    }

    /**
     * Método que se ejecuta cuando el hilo inicia. Realiza la renderización del
     * juego a la velocidad establecida.
     */
    @Override
    public void run() {
        long lastRenderTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastRenderTime;

            // Verifica si ha pasado el tiempo necesario para una renderización.
            if (elapsedTime >= targetRenderTime) {
                // Solicita el repintado del panel de juego.
                juego.panel.repaint();
                lastRenderTime = currentTime;
            }

            // Agrega una pequeña pausa para evitar el uso excesivo de CPU
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
