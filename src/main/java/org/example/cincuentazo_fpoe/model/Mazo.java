package org.example.cincuentazo_fpoe.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Representa el mazo de 52 cartas del juego Cincuentazo. Se encarga de
 * crear la baraja completa, barajarla, repartir cartas y reponerse a
 * partir del descarte de la mesa cuando se agota, tal como lo describe
 * el enunciado ("Otras consideraciones").
 *
 * @author Nicolas Velasco
 */

public class Mazo {

    private final Deque<Carta> cartas = new ArrayDeque<>();

    public Mazo() {
        crearBaraja();
        barajar();
    }

    private void crearBaraja() {
        for (Palo palo : Palo.values()) {
            for (Rango rango : Rango.values()) {
                cartas.push(new Carta(rango, palo));
            }
        }
    }

    /** Baraja aleatoriamente las cartas actualmente disponibles en el mazo. */
    public void barajar() {
        List<Carta> lista = new ArrayList<>(cartas);
        Collections.shuffle(lista);
        cartas.clear();
        cartas.addAll(lista);
    }

    public int tamano() {
        return cartas.size();
    }

    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    /**
     * Toma y remueve la carta superior del mazo.
     *
     * @return la carta tomada.
     * @throws MazoVacioException si el mazo no tiene cartas disponibles.
     */
    public Carta tomarCarta() throws MazoVacioException {
        if (cartas.isEmpty()) {
            throw new MazoVacioException("No hay cartas disponibles en el mazo.");
        }
        return cartas.pop();
    }

    /**
     * Repone el mazo agregando las cartas del descarte de la mesa (todas
     * menos la ultima jugada) y las baraja para dejarlas disponibles de
     * nuevo. La suma de la mesa no se modifica por esta operacion.
     */
    public void reponer(List<Carta> cartasDescarte) {
        cartas.addAll(cartasDescarte);
        barajar();
    }

    /**
     * Envia las cartas de un jugador eliminado al final (fondo) del mazo,
     * donde quedan disponibles para ser tomadas mas adelante por otro
     * jugador, tal como lo exige HU-5.
     */
    public void agregarAlFinal(List<Carta> cartasJugador) {
        for (Carta carta : cartasJugador) {
            cartas.addLast(carta);
        }
    }
}
