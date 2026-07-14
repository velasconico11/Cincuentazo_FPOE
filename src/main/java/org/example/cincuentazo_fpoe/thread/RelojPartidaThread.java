package org.example.cincuentazo_fpoe.thread;

import javafx.application.Platform;
import org.example.cincuentazo_fpoe.controller.ConfiguracionController;
import javafx.application.Preloader;
import org.example.cincuentazo_fpoe.controller.JuegoController;

import java.security.KeyStore;

/**
 * Segundo hilo de la aplicacion, independiente del {@link TurnoMaquinaThread}.
 * Mide el tiempo transcurrido de la partida y, cada segundo, actualiza la
 * etiqueta correspondiente en la interfaz a traves de
 * {@link Platform#runLater(Runnable)}. Se detiene cuando el juego finaliza
 * o cuando el controlador lo solicita explicitamente.
 *
 * @author Nicolás Velasco, Daniel Toro
 */

public class RelojPartidaThread extends Thread {

    private final JuegoController controller;
    private volatile boolean activo = true;
    private int segundosTranscurridos = 0;

    public RelojPartidaThread(JuegoController controller){
        super("Hilo-Reloj-Partida");
        this.controller = controller;
        setDaemon(true);
    }

    /** Detiene el hilo de forma segura en la siguiente iteracion. */

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            segundosTranscurridos++;
            final int segundos = segundosTranscurridos;
            Platform.runLater(() -> controller.actualizarTiempo(segundos));
        }
    }
}
