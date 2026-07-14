package org.example.cincuentazo_fpoe;

import org.example.cincuentazo_fpoe.model.Carta;
import org.example.cincuentazo_fpoe.model.Palo;
import org.example.cincuentazo_fpoe.model.Rango;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Pruebas unitarias sobre las reglas de valor de las cartas, segun el
 * enunciado del juego Cincuentazo.
 */
class CartaTest {

    @Test
    void numerosDelDosAlOchoSumanSuNumero() {
        Carta carta = new Carta(Rango.CINCO, Palo.CORAZONES);
        assertArrayEquals(new int[]{5}, carta.valoresPosibles());
    }

    @Test
    void elDiezSumaDiez() {
        Carta carta = new Carta(Rango.DIEZ, Palo.PICAS);
        assertArrayEquals(new int[]{10}, carta.valoresPosibles());
    }

    @Test
    void laCartaNueveNoSumaNiResta() {
        Carta carta = new Carta(Rango.NUEVE, Palo.PICAS);
        assertArrayEquals(new int[]{0}, carta.valoresPosibles());
    }

    @Test
    void lasFigurasRestanDiez() {
        Carta jota = new Carta(Rango.JOTA, Palo.TREBOLES);
        Carta reina = new Carta(Rango.REINA, Palo.DIAMANTES);
        Carta rey = new Carta(Rango.REY, Palo.CORAZONES);
        assertArrayEquals(new int[]{-10}, jota.valoresPosibles());
        assertArrayEquals(new int[]{-10}, reina.valoresPosibles());
        assertArrayEquals(new int[]{-10}, rey.valoresPosibles());
    }

    @Test
    void elAsPermiteUnoODiez() {
        Carta as = new Carta(Rango.AS, Palo.DIAMANTES);
        assertArrayEquals(new int[]{1, 10}, as.valoresPosibles());
    }
}

