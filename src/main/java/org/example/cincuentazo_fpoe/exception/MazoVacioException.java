package org.example.cincuentazo_fpoe.exception;

/**
 * Excepcion <b>marcada (checked)</b> y propia del dominio del juego.
 * <p>
 * Se lanza cuando se intenta tomar una carta y no hay ninguna disponible,
 * ni en el mazo ni en el descarte de la mesa para reponerlo. Al ser una
 * excepcion checked, obliga a quien la invoca a manejarla explicitamente
 * (try/catch o declarando throws), reflejando que es una situacion de la
 * cual el programa puede y debe recuperarse.
 *
 * @author Nicolás Velasco, Daniel Toro
 */

public class MazoVacioException extends Exception {

    public MazoVacioException(String mensaje) {
        super(mensaje);
    }

    public MazoVacioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
