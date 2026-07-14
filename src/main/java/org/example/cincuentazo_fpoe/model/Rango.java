package org.example.cincuentazo_fpoe.model;

/**
 * Representa el rango de una carta (A, 2-10, J, Q, K) y encapsula la regla
 * de negocio de cuanto suma o resta cada rango en la mesa, segun el
 * enunciado del juego Cincuentazo:
 * <ul>
 *     <li>2 al 8 y el 10 suman su propio numero.</li>
 *     <li>El 9 no suma ni resta (valor 0).</li>
 *     <li>J, Q, K restan 10.</li>
 *     <li>El As suma 1 o 10, segun convenga al jugador.</li>
 * </ul>
 *
 * @author Nicolás Velasco
 */

public enum Rango {

    AS("A", new int[]{1, 10}),
    DOS("2", new int[]{2}),
    TRES("3", new int[]{3}),
    CUATRO("4", new int[]{4}),
    CINCO("5", new int[]{5}),
    SEIS("6", new int[]{6}),
    SIETE("7", new int[]{7}),
    OCHO("8", new int[]{8}),
    NUEVE("9", new int[]{0}),
    DIEZ("10", new int[]{10}),
    JOTA("J", new int[]{-10}),
    REINA("Q", new int[]{-10}),
    REY("K", new int[]{-10});

    private final String simbolo;
    private final int[] valoresPosibles;

    Rango(String simbolo, int[] valoresPosibles) {
        this.simbolo = simbolo;
        this.valoresPosibles = valoresPosibles;
    }

    /**
     * @return la representacion textual del rango (ej. "A", "10", "K").
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @return los valores que esta carta puede aportar a la suma de la mesa.
     * Todas las cartas tienen un unico valor posible, excepto el As, que
     * tiene dos (1 o 10).
     */
    public int[] getValoresPosibles() {
        return valoresPosibles;
    }
}
