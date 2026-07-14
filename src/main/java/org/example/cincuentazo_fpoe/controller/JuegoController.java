package org.example.cincuentazo_fpoe.controller;

import org.example.cincuentazo_fpoe.thread.RelojPartidaThread;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;




import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de la pantalla principal del juego. Es el puente entre la
 * Vista (juego.fxml) y el Modelo ({@link Juego}): traduce las acciones
 * del jugador humano en llamadas al modelo, y refleja los cambios del
 * modelo en los componentes visuales.
 *
 * @author Mini Proyecto 3 - Cincuentazo
 */
public class JuegoController {

    @FXML
    private Label lblTurno;
    @FXML
    private Label lblSuma;
    @FXML
    private Label lblCartaMesa;
    @FXML
    private Label lblCartasMazo;
    @FXML
    private Label lblTiempo;
    @FXML
    private Label lblMesaVisual;
    @FXML
    private VBox vboxMaquinas;
    @FXML
    private HBox hboxMano;
    @FXML
    private TextArea areaLog;

    private Juego juego;
    private RelojPartidaThread relojThread;

    /**
     * Inicializa el controlador con la partida ya creada y arranca la
     * partida: registra el primer mensaje de log, lanza el hilo del
     * reloj y prepara el primer turno.
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
        areaLog.clear();
        registrarLog("La partida ha comenzado con " + (juego.getJugadores().size() - 1)
                + " jugador(es) maquina.");
        relojThread = new RelojPartidaThread(this);
        relojThread.start();
        prepararTurno();
    }
