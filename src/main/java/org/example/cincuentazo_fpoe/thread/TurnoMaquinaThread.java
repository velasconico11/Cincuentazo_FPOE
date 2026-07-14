package org.example.cincuentazo_fpoe.thread;

import org.example.cincuentazo_fpoe.controller.JuegoController;
import javafx.application.Platform;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Primer hilo de la aplicacion. Simula el "tiempo de pensamiento" de un
 * jugador maquina antes de jugar su turno, con una espera aleatoria entre
 * 2 y 4 segundos, tal como lo exigen HU-3 y HU-4. Al finalizar la espera,
 * delega la ejecucion real del turno al hilo de JavaFX mediante
 * {@link Platform#runLater(Runnable)}, ya que toda modificacion de la
 * interfaz debe ocurrir en el hilo de UI.
 *
 * @author Mini Proyecto 3 - Cincuentazo
 */

public class TurnoMaquinaThread extends Thread {

    private static final int ESPERA_MINIMA_MS = 2000;
    private static final int ESPERA_MAXIMA_MS = 4001; // limite exclusivo

    private final JuegoController controller;

    public TurnoMaquinaThread(JuegoController controller) {
        super("Hilo-Turno-Maquina");
        this.controller = controller;
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            int espera = ThreadLocalRandom.current().nextInt(ESPERA_MINIMA_MS, ESPERA_MAXIMA_MS);
            Thread.sleep(espera);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        Platform.runLater(controller::ejecutarTurnoMaquina);
    }
}
