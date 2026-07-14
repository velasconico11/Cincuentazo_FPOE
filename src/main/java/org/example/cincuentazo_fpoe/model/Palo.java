package org.example.cincuentazo_fpoe.model;

/**
 * Representa los cuatro palos de la baraja estandar de 52 cartas.
 *
 * @author Nicolás Velasco, Daniel Toro
 */

public enum Palo {

    CORAZONES("\u2665"),
    DIAMANTES("\u2666"),
    TREBOLES("\u2663"),
    PICAS("\u2660");

    private final String simbolo;

    Palo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return el simbolo grafico asociado al palo (por ejemplo, el corazon).
     */
    public String getSimbolo() {
        return simbolo;
    }
}
