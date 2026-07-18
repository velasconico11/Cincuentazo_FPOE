package org.example.cincuentazo_fpoe.controller;

import org.example.cincuentazo_fpoe.exception.CartaInvalidaException;
import org.example.cincuentazo_fpoe.exception.JugadorEliminadoException;
import org.example.cincuentazo_fpoe.exception.MazoVacioException;
import org.example.cincuentazo_fpoe.model.Carta;
import org.example.cincuentazo_fpoe.model.Juego;
import org.example.cincuentazo_fpoe.model.Jugador;
import org.example.cincuentazo_fpoe.model.Rango;
import org.example.cincuentazo_fpoe.thread.RelojPartidaThread;
import org.example.cincuentazo_fpoe.thread.TurnoMaquinaThread;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Controlador de la pantalla principal del juego. Es el puente entre la
 * Vista (juego.fxml) y el Modelo ({@link Juego}): traduce las acciones
 * del jugador humano en llamadas al modelo, y refleja los cambios del
 * modelo en los componentes visuales.
 *
 * @author Nicolas Velasco, Daniel Toro
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
    private HBox vboxMaquinas;
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

    /**
     * Prepara el turno del jugador actual: si no tiene jugada valida lo
     * elimina (HU-5), y si el juego ya termino muestra el resultado
     * (HU-6). En caso contrario, actualiza la vista y delega la accion
     * al jugador humano (habilitando su mano) o a la maquina (lanzando
     * {@link TurnoMaquinaThread}).
     */
    private void prepararTurno() {
        actualizarVista();

        if (juego.isFinalizado()) {
            mostrarFinDeJuego();
            return;
        }

        Jugador actual = juego.getJugadorActual();

        if (!juego.tieneJugadaValida(actual)) {
            registrarLog(actual.getNombre() + " no tiene jugadas validas y queda eliminado.");
            juego.eliminarJugador(actual);
            if (juego.verificarFinJuego()) {
                actualizarVista();
                mostrarFinDeJuego();
                return;
            }
            juego.siguienteTurno();
            prepararTurno();
            return;
        }

        lblTurno.setText("Turno de: " + actual.getNombre());

        if (actual.isMaquina()) {
            hboxMano.setDisable(true);
            new TurnoMaquinaThread(this).start();
        } else {
            hboxMano.setDisable(false);
            renderizarManoHumana(actual);
        }
    }

    /** Dibuja la mano del jugador humano como botones clicables (una carta = un boton). */
    private void renderizarManoHumana(Jugador humano) {
        hboxMano.getChildren().clear();
        for (Carta carta : humano.getMano()) {
            Button boton = new Button(carta.getSimbolo());
            boton.getStyleClass().add("carta-boton");
            Integer valorValido = juego.valorValidoParaCarta(carta);
            boton.setDisable(valorValido == null);
            boton.setOnAction(e -> jugarCartaHumano(humano, carta));
            hboxMano.getChildren().add(boton);
        }
    }

    /**
     * Procesa la jugada de una carta seleccionada por el jugador humano:
     * valida la jugada, pide el valor si es un As con ambas opciones
     * validas, actualiza la mesa, toma una carta del mazo y pasa el
     * turno (HU-3 y HU-4).
     */
    private void jugarCartaHumano(Jugador humano, Carta carta) {
        Integer valorAs = null;
        if (carta.getRango() == Rango.AS) {
            valorAs = elegirValorAs(carta);
        }

        try {
            juego.jugarCarta(humano, carta, valorAs);
            registrarLog(humano.getNombre() + " juega " + carta.getSimbolo()
                    + " (suma actual: " + juego.getMesa().getSumaActual() + ").");
        } catch (CartaInvalidaException | JugadorEliminadoException e) {
            mostrarError(e.getMessage());
            return;
        }

        try {
            juego.tomarCartaParaJugador(humano);
        } catch (MazoVacioException e) {
            registrarLog("No fue posible reponer una carta: " + e.getMessage());
        }

        if (juego.verificarFinJuego()) {
            actualizarVista();
            mostrarFinDeJuego();
            return;
        }

        juego.siguienteTurno();
        prepararTurno();
    }

    /**
     * Muestra un dialogo para que el jugador elija si el As suma 1 o 10,
     * restringido a los valores que realmente son validos segun la suma
     * actual de la mesa.
     */
    private Integer elegirValorAs(Carta carta) {
        int sumaActual = juego.getMesa().getSumaActual();
        boolean unoValido = sumaActual + 1 <= Juego.LIMITE_MESA;
        boolean diezValido = sumaActual + 10 <= Juego.LIMITE_MESA;

        if (unoValido && !diezValido) {
            return 1;
        }
        if (diezValido && !unoValido) {
            return 10;
        }

        ButtonType botonUno = new ButtonType("Sumar 1");
        ButtonType botonDiez = new ButtonType("Sumar 10");
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "El As puede sumar 1 o 10. ¿Cual prefieres jugar?",
                botonUno, botonDiez);
        alerta.setHeaderText("Elige el valor del As");
        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == botonDiez ? 10 : 1;
    }

    /**
     * Ejecuta el turno de un jugador maquina: elige la primera carta
     * jugable de su mano, la juega, toma una carta del mazo y pasa el
     * turno. Este metodo es invocado desde {@link TurnoMaquinaThread}
     * mediante {@code Platform.runLater}, por lo que se ejecuta en el
     * hilo de JavaFX.
     */
    public void ejecutarTurnoMaquina() {
        Jugador actual = juego.getJugadorActual();
        Carta cartaElegida = null;
        Integer valorElegido = null;

        for (Carta carta : actual.getMano()) {
            Integer valor = juego.valorValidoParaCarta(carta);
            if (valor != null) {
                cartaElegida = carta;
                valorElegido = valor;
                break;
            }
        }

        if (cartaElegida == null) {
            // No deberia ocurrir: prepararTurno ya valida tieneJugadaValida antes de llamar aqui.
            prepararTurno();
            return;
        }

        try {
            juego.jugarCarta(actual, cartaElegida, valorElegido);
            registrarLog(actual.getNombre() + " juega " + cartaElegida.getSimbolo()
                    + " (suma actual: " + juego.getMesa().getSumaActual() + ").");
        } catch (CartaInvalidaException | JugadorEliminadoException e) {
            registrarLog("Error en el turno de " + actual.getNombre() + ": " + e.getMessage());
        }

        try {
            juego.tomarCartaParaJugador(actual);
        } catch (MazoVacioException e) {
            registrarLog("No fue posible reponer una carta: " + e.getMessage());
        }

        if (juego.verificarFinJuego()) {
            actualizarVista();
            mostrarFinDeJuego();
            return;
        }

        juego.siguienteTurno();
        prepararTurno();
    }

    /** Refresca todas las etiquetas y paneles con el estado actual del modelo. */
    private void actualizarVista() {
        lblSuma.setText("Suma actual: " + juego.getMesa().getSumaActual());
        Carta ultima = juego.getMesa().getUltimaCarta();
        lblCartaMesa.setText("Ultima carta: " + (ultima != null ? ultima.getSimbolo() : "-"));
        lblMesaVisual.setText(ultima != null ? ultima.getSimbolo() : "🂠");
        lblCartasMazo.setText("Cartas en el mazo: " + juego.getMazo().tamano());

        vboxMaquinas.getChildren().clear();
        for (Jugador jugador : juego.getJugadores()) {
            if (jugador.isMaquina()) {
                String estado = jugador.isEliminado()
                        ? jugador.getNombre() + ": eliminado"
                        : jugador.getNombre() + ": " + jugador.getMano().size() + " carta(s)";
                Label lbl = new Label(estado);
                lbl.getStyleClass().add("info-label-maquina");
                vboxMaquinas.getChildren().add(lbl);
            }
        }
    }

    /** Detiene el hilo del reloj y muestra el resultado final de la partida (HU-6). */
    private void mostrarFinDeJuego() {
        if (relojThread != null) {
            relojThread.detener();
        }
        hboxMano.setDisable(true);
        hboxMano.getChildren().clear();

        Jugador ganador = juego.getGanador();
        String mensaje = ganador != null
                ? "¡" + ganador.getNombre() + " gana la partida!"
                : "La partida termino sin un ganador.";
        registrarLog(mensaje);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alerta.setHeaderText("Fin del juego");
        alerta.showAndWait();
    }

    /** Actualiza la etiqueta de tiempo transcurrido, invocado desde {@link RelojPartidaThread}. */
    public void actualizarTiempo(int segundosTotales) {
        int minutos = segundosTotales / 60;
        int segundos = segundosTotales % 60;
        lblTiempo.setText(String.format("Tiempo: %02d:%02d", minutos, segundos));
    }

    /** Agrega una linea de texto al log de la partida. */
    private void registrarLog(String mensaje) {
        areaLog.appendText(mensaje + System.lineSeparator());
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alerta.setHeaderText("Jugada invalida");
        alerta.showAndWait();
    }
}