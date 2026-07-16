package org.example.cincuentazo_fpoe;

import org.example.cincuentazo_fpoe.exception.MazoVacioException;
import org.example.cincuentazo_fpoe.model.Carta;
import org.example.cincuentazo_fpoe.model.Juego;
import org.junit.jupiter.api.Test;
import org.example.cincuentazo_fpoe.model.Mazo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias sobre el comportamiento del mazo: reparto, agotamiento
 * y reposicion, incluyendo la excepcion marcada {@link MazoVacioException}.
 */

class MazoTest {

    @Test
    void elMazoInicialTiene52Cartas() {
        Mazo mazo = new Mazo();
        assertEquals(52, mazo.tamano());
    }

    @Test
    void tomarCartaReduceElTamanoDelMazo() throws MazoVacioException {
        Mazo mazo = new Mazo();
        mazo.tomarCarta();
        assertEquals(51, mazo.tamano());
    }

    @Test
    void tomarCartaDeUnMazoVacioLanzaExcepcionMarcada() throws MazoVacioException {
        Mazo mazo = new Mazo();
        for (int i = 0; i < 52; i++) {
            mazo.tomarCarta();
        }
        assertTrue(mazo.estaVacio());
        assertThrows(MazoVacioException.class, mazo::tomarCarta);
    }

    @Test
    void reponerDejaCartasDisponiblesDeNuevo() throws MazoVacioException {
        Mazo mazo = new Mazo();
        List<Carta> descarte = new ArrayList<>();
        while (!mazo.estaVacio()) {
            descarte.add(mazo.tomarCarta());
        }
        mazo.reponer(descarte);
        assertEquals(52, mazo.tamano());
    }
}
