package org.example.cincuentazo_fpoe.exception;

/**
 * Excepcion <b>no marcada (unchecked)</b> y propia del dominio del juego.
 * <p>
 * Se lanza cuando se intenta ejecutar una accion (jugar carta, tomar
 * carta, etc.) sobre un jugador que ya fue eliminado de la partida.
 *
 * @author Daniel Toro
 */

public class JugadorEliminadoException extends RuntimeException {
    public JugadorEliminadoException(String mensaje) {
        super(mensaje);
    }
}
