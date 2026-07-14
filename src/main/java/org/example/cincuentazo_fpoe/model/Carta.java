package org.example.cincuentazo_fpoe.model;

import java.util.Objects;

/**
 * Representa una carta individual de la baraja, compuesta por un
 * {@link Rango} y un {@link Palo}. Es un objeto de valor (inmutable)
 * dentro de la capa Modelo del patron MVC.
 *
 * @author Nicolás Velasco, Daniel Toro
 */

public class Carta {

    private final Rango rango;
    private final Palo palo;

    public Carta(Rango rango, Palo palo) {
        this.rango = rango;
        this.palo = palo;
    }

    public Rango getRango() {
        return rango;
    }

    public Palo getPalo() {
        return palo;
    }

    /**
     * @return los valores que esta carta puede aportar a la suma de la
     * mesa (ver {@link Rango#getValoresPosibles()}).
     */
    public int[] valoresPosibles() {
        return rango.getValoresPosibles();
    }

    /**
     * @return una representacion corta para mostrar en la interfaz,
     * por ejemplo "A ♥" o "10 ♠".
     */
    public String getSimbolo() {
        return rango.getSimbolo() + " " + palo.getSimbolo();
    }

    @Override
    public String toString() {
        return getSimbolo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carta)) {
            return false;
        }
        Carta carta = (Carta) o;
        return rango == carta.rango && palo == carta.palo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rango, palo);
    }

}
