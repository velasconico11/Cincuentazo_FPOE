package org.example.cincuentazo_fpoe;

import org.example.cincuentazo_fpoe.exception.CartaInvalidaException;
import org.example.cincuentazo_fpoe.model.Carta;
import org.example.cincuentazo_fpoe.model.Juego;
import org.example.cincuentazo_fpoe.model.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias sobre la logica principal del juego: preparacion,
 * validacion de jugadas, eliminacion de jugadores y fin de partida.
 */

class JuegoTest {

    private Juego juego;

    @BeforeEach
    void setUp() {
        juego = new Juego(1);
        juego.iniciarJuego();
    }

    @Test
    void cadaJugadorRecibeCuatroCartasAlIniciar() {
        for (Jugador jugador : juego.getJugadores()) {
            assertEquals(4, jugador.getMano().size());
        }
    }

    @Test
    void laSumaInicialNuncaSuperaElLimite() {
        assertTrue(juego.getMesa().getSumaActual() <= Juego.LIMITE_MESA);
    }

    @Test
    void jugarUnaCartaValidaNoLanzaExcepcionYActualizaLaSuma() {
        Jugador jugadorActual = juego.getJugadorActual();
        Carta carta = jugadorActual.getMano().get(0);
        Integer valorValido = juego.valorValidoParaCarta(carta);

        if (valorValido != null) {
            int sumaAntes = juego.getMesa().getSumaActual();
            assertDoesNotThrow(() -> juego.jugarCarta(jugadorActual, carta, null));
            assertEquals(sumaAntes + valorValido, juego.getMesa().getSumaActual());
        } else {
            assertThrows(CartaInvalidaException.class,
                    () -> juego.jugarCarta(jugadorActual, carta, null));
        }
    }

    @Test
    void eliminarJugadorLoMarcaComoEliminadoYVaciaSuMano() {
        Jugador jugador = juego.getJugadores().get(1);
        juego.eliminarJugador(jugador);
        assertTrue(jugador.isEliminado());
        assertEquals(0, jugador.getMano().size());
    }

    @Test
    void elJuegoFinalizaCuandoSoloQuedaUnJugadorActivo() {
        for (int i = 1; i < juego.getJugadores().size(); i++) {
            juego.eliminarJugador(juego.getJugadores().get(i));
        }
        assertTrue(juego.verificarFinJuego());
        assertNotNull(juego.getGanador());
        assertEquals(juego.getJugadores().get(0), juego.getGanador());
    }
}
