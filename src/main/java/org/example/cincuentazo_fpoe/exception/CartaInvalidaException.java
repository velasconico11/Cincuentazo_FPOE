package org.example.cincuentazo_fpoe.exception;

/**
 * Excepcion <b>no marcada (unchecked)</b> y propia del dominio del juego.
 * <p>
 * Se lanza cuando un jugador intenta jugar una carta que no cumple con
 * la regla principal (excederia la suma de 50 en la mesa) o que no se
 * encuentra realmente en su mano. Se modela como unchecked (extiende
 * {@link RuntimeException}) porque representa un error de logica/uso
 * del propio programa (una jugada que la UI nunca deberia permitir
 * construir), no una condicion externa recuperable.
 *
 * @author Nicolás Velasco
 */

public class CartaInvalidaException extends RuntimeException {

    public CartaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
