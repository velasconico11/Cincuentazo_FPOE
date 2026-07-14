package org.example.cincuentazo_fpoe.model;

import  org.example.cincuentazo_fpoe.exception.MazoVacioException;
import  org.example.cincuentazo_fpoe.exception.CartaInvalidaException;
import javafx.collections.ObservableList;

/**
 * Interfaz que define el contrato del modelo del juego Cincuentazo.
 *
 * @author Nicolás Velasco
 */

public interface IJuego {

    /** Reparte las cartas iniciales y coloca la carta inicial en la mesa. */
    void iniciarJuego();

    Mesa getMesa();

    Mazo getMazo();

    ObservableList<Jugador> getJugadores();

    Jugador getJugadorActual();

    boolean isFinalizado();

    Jugador getGanador();

    /** Calcula el mejor valor valido que una carta puede aportar a la mesa. */
    Integer valorValidoParaCarta(Carta carta);

    /** Indica si el jugador tiene al menos una jugada valida disponible. */
    boolean tieneJugadaValida(Jugador jugador);

    /** Ejecuta la jugada de una carta para el jugador indicado. */
    void jugarCarta(Jugador jugador, Carta carta, Integer valorAs) throws CartaInvalidaException;

    /** Repone la mano del jugador tomando una carta del mazo. */
    void tomarCartaParaJugador(Jugador jugador) throws MazoVacioException;

    /** Elimina al jugador indicado y envia sus cartas al final del mazo. */
    void eliminarJugador(Jugador jugador);

    /** Avanza el turno al siguiente jugador activo. */
    void siguienteTurno();

    /** Verifica si la partida debe finalizar y registra al ganador. */
    boolean verificarFinJuego();

    int contarActivos();

}
