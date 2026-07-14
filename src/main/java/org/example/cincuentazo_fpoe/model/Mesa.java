package org.example.cincuentazo_fpoe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Representa la mesa de juego: la pila de cartas jugadas (descarte) y la
 * suma acumulada, que nunca debe superar {@link Juego#LIMITE_MESA}.
 * La suma se expone como {@link IntegerProperty} para permitir el
 * enlace (binding) directo con la Vista, sin que el Controlador tenga
 * que sincronizar manualmente cada actualizacion.
 *
 * @author Daniel Toro
 */

public class Mesa {

    private final Deque<Carta> pilaDescartes = new ArrayDeque<>();
    private final IntegerProperty sumaActual = new SimpleIntegerProperty(0);

    public IntegerProperty sumaActualProperty() {
        return sumaActual;
    }

    public int getSumaActual() {
        return sumaActual.get();
    }

    /** @return la ultima carta jugada (visible boca arriba), o null si no hay ninguna. */
    public Carta getUltimaCarta() {
        return pilaDescartes.peek();
    }

    /** Coloca la carta inicial de la partida y fija la suma inicial de la mesa. */
    public void iniciar(Carta cartaInicial, int valorInicial) {
        pilaDescartes.push(cartaInicial);
        sumaActual.set(valorInicial);
    }

    /** Coloca una nueva carta jugada encima de la anterior y actualiza la suma. */
    public void colocarCarta(Carta carta, int valor) {
        pilaDescartes.push(carta);
        sumaActual.set(sumaActual.get() + valor);
    }

    /**
     * Extrae todas las cartas del descarte excepto la ultima jugada, para
     * que el {@link Mazo} pueda reponerse cuando se agote. La ultima
     * carta permanece en la mesa como referencia visual y de suma.
     */
    public List<Carta> extraerParaReponer() {
        List<Carta> resultado = new ArrayList<>(pilaDescartes);
        Carta ultima = resultado.isEmpty() ? null : resultado.remove(0);
        pilaDescartes.clear();
        if (ultima != null) {
            pilaDescartes.push(ultima);
        }
        return resultado;
    }

    public int tamanoDescarte() {
        return pilaDescartes.size();
    }
}
