/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentador;

/**
 * Clase que representa un hilo encargado de la actualización lógica del juego a
 * una velocidad específica (UPS - Actualizaciones por segundo).
 *
 * @author alex_
 */
public class HiloActualizacion extends Thread {
    
    // Referencia al objeto Juego que se está actualizando.
    private Juego juego;
    // Tiempo objetivo entre actualizaciones
    private long targetUpdateTime;

    /**
     * Constructor de la clase HiloActualizacion.
     *
     * @param juego Referencia al objeto Juego que se va a actualizar.
     */
    public HiloActualizacion(Juego juego) {
        this.juego = juego;
        // Calcula el tiempo objetivo entre actualizaciones en nanosegundos.
        targetUpdateTime = 1000000000 / Juego.UPS_SET;
    }

    /**
     * Método que se ejecuta cuando el hilo inicia. Realiza la actualización del
     * juego a la velocidad establecida.
     */
    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastUpdateTime;

            // Verifica si ha pasado el tiempo necesario para una actualización.
            if (elapsedTime >= targetUpdateTime) {
                // Realiza la actualización lógica del juego.
//                juego.update();
                lastUpdateTime = currentTime;
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
