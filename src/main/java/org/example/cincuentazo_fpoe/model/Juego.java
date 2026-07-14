package org.example.cincuentazo_fpoe.model;

import org.example.cincuentazo_fpoe.exception.CartaInvalidaException;
import org.example.cincuentazo_fpoe.exception.JugadorEliminadoException;
import org.example.cincuentazo_fpoe.exception.MazoVacioException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor principal del juego Cincuentazo. Concentra toda la logica de
 * negocio descrita en el enunciado: reparto de cartas, validacion de
 * jugadas segun la regla principal (no exceder 50), turnos, eliminacion
 * de jugadores y condicion de fin de partida.
 * <p>
 * Esta clase pertenece a la capa <b>Modelo</b> del patron MVC: no conoce
 * FXML ni controladores, solo expone su estado (a traves de
 * {@link ObservableList} y propiedades) para que la capa Vista/Controlador
 * pueda reaccionar a los cambios.
 *
 * @author Nicolás Velasco
 */

public class Juego implements IJuego {

    /** Suma maxima permitida en la mesa (regla principal del juego). */
    public static final int LIMITE_MESA = 50;
    private static final int CARTAS_INICIALES = 4;

    private final Mazo mazo;
    private final Mesa mesa;
    private final ObservableList<Jugador> jugadores = FXCollections.observableArrayList();
    private int turnoActual;
    private boolean finalizado;
    private Jugador ganador;

    /**
     * Crea una nueva partida con el jugador humano y la cantidad indicada
     * de jugadores maquina.
     *
     * @param cantidadMaquinas cantidad de jugadores maquina (1, 2 o 3).
     */
    public Juego(int cantidadMaquinas) {
        if (cantidadMaquinas < 1 || cantidadMaquinas > 3) {
            throw new IllegalArgumentException("La cantidad de jugadores maquina debe ser 1, 2 o 3.");
        }
        this.mazo = new Mazo();
        this.mesa = new Mesa();
        jugadores.add(new Jugador("Jugador", false));
        for (int i = 1; i <= cantidadMaquinas; i++) {
            jugadores.add(new Jugador("Maquina " + i, true));
        }
        this.turnoActual = 0;
        this.finalizado = false;
    }

    /**
     * Reparte 4 cartas a cada jugador y coloca la carta inicial boca
     * arriba en la mesa, iniciando la suma segun el rango de dicha carta
     * (HU-2 / Preparacion del juego).
     */
    @Override
    public void iniciarJuego() {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < CARTAS_INICIALES; i++) {
                try {
                    jugador.agregarCarta(mazo.tomarCarta());
                } catch (MazoVacioException e) {
                    throw new IllegalStateException("El mazo inicial no tiene suficientes cartas.", e);
                }
            }
        }
        try {
            Carta cartaInicial = mazo.tomarCarta();
            int valorInicial = cartaInicial.valoresPosibles()[0];
            mesa.iniciar(cartaInicial, valorInicial);
        } catch (MazoVacioException e) {
            throw new IllegalStateException("El mazo inicial no tiene suficientes cartas.", e);
        }
    }

    @Override
    public Mesa getMesa() {
        return mesa;
    }

    @Override
    public Mazo getMazo() {
        return mazo;
    }

    @Override
    public ObservableList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    @Override
    public boolean isFinalizado() {
        return finalizado;
    }

    @Override
    public Jugador getGanador() {
        return ganador;
    }

    /**
     * Calcula, para una carta dada, el mejor valor que puede aportar sin
     * exceder {@link #LIMITE_MESA}.
     *
     * @return el valor a aplicar, o {@code null} si ningun valor posible
     * de la carta es valido (la carta no se puede jugar).
     */
    @Override
    public Integer valorValidoParaCarta(Carta carta) {
        int sumaActual = mesa.getSumaActual();
        Integer mejor = null;
        for (int valor : carta.valoresPosibles()) {
            if (sumaActual + valor <= LIMITE_MESA) {
                if (mejor == null || valor > mejor) {
                    mejor = valor;
                }
            }
        }
        return mejor;
    }

    /** @return true si el jugador tiene al menos una carta jugable en su mano. */
    @Override
    public boolean tieneJugadaValida(Jugador jugador) {
        for (Carta carta : jugador.getMano()) {
            if (valorValidoParaCarta(carta) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ejecuta la jugada de una carta para el jugador indicado (HU-3).
     *
     * @param jugador jugador que realiza la jugada (debe ser el jugador actual).
     * @param carta   carta seleccionada, debe estar en su mano.
     * @param valorAs valor elegido si la carta es un As (1 o 10); se ignora en
     *                cualquier otro caso. Puede ser {@code null} para que el
     *                motor elija automaticamente el mejor valor valido.
     * @throws CartaInvalidaException     si la carta no esta en la mano o si
     *                                    jugarla excederia el limite de la mesa.
     * @throws JugadorEliminadoException  si el jugador ya fue eliminado.
     */
    @Override
    public void jugarCarta(Jugador jugador, Carta carta, Integer valorAs) throws CartaInvalidaException {
        if (jugador.isEliminado()) {
            throw new JugadorEliminadoException("El jugador " + jugador.getNombre() + " ya fue eliminado.");
        }
        if (!jugador.getMano().contains(carta)) {
            throw new CartaInvalidaException("La carta " + carta + " no se encuentra en la mano de " + jugador.getNombre() + ".");
        }
        int valor = determinarValorJugada(carta, valorAs);
        if (mesa.getSumaActual() + valor > LIMITE_MESA) {
            throw new CartaInvalidaException("Jugar " + carta + " excederia el limite de " + LIMITE_MESA + " en la mesa.");
        }
        jugador.quitarCarta(carta);
        mesa.colocarCarta(carta, valor);
    }

    private int determinarValorJugada(Carta carta, Integer valorAs) {
        int[] posibles = carta.valoresPosibles();
        if (posibles.length == 1) {
            return posibles[0];
        }
        // Es un As (1 o 10): se respeta la eleccion del jugador si es valida.
        if (valorAs != null) {
            for (int v : posibles) {
                if (v == valorAs) {
                    return v;
                }
            }
        }
        Integer mejor = valorValidoParaCarta(carta);
        return mejor != null ? mejor : posibles[0];
    }

    /**
     * Repone la mano del jugador tomando una carta del mazo para que
     * siempre conserve 4 cartas (HU-4). Si el mazo esta vacio, primero
     * lo repone automaticamente con el descarte de la mesa.
     *
     * @throws MazoVacioException si tampoco hay cartas en el descarte
     *                             para reponer el mazo.
     */
    @Override
    public void tomarCartaParaJugador(Jugador jugador) throws MazoVacioException {
        if (mazo.estaVacio()) {
            List<Carta> reposicion = mesa.extraerParaReponer();
            if (reposicion.isEmpty()) {
                throw new MazoVacioException("No hay cartas disponibles para reponer el mazo.");
            }
            mazo.reponer(reposicion);
        }
        Carta nueva = mazo.tomarCarta();
        jugador.agregarCarta(nueva);
    }

    /**
     * Elimina al jugador indicado: lo marca como eliminado y envia sus
     * cartas al final del mazo, donde quedan disponibles para otros
     * jugadores (HU-5).
     */
    @Override
    public void eliminarJugador(Jugador jugador) {
        jugador.setEliminado(true);
        List<Carta> cartas = new ArrayList<>(jugador.getMano());
        jugador.getMano().clear();
        mazo.agregarAlFinal(cartas);
    }

    /** Avanza el turno al siguiente jugador que no haya sido eliminado. */
    @Override
    public void siguienteTurno() {
        int intentos = 0;
        do {
            turnoActual = (turnoActual + 1) % jugadores.size();
            intentos++;
        } while (jugadores.get(turnoActual).isEliminado() && intentos <= jugadores.size());
    }

    /**
     * Verifica si la partida debe finalizar (queda un solo jugador activo)
     * y, de ser asi, registra al ganador (HU-6).
     *
     * @return true si la partida ya finalizo.
     */
    @Override
    public boolean verificarFinJuego() {
        List<Jugador> activos = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (!jugador.isEliminado()) {
                activos.add(jugador);
            }
        }
        if (activos.size() <= 1) {
            finalizado = true;
            ganador = activos.isEmpty() ? null : activos.get(0);
            return true;
        }
        return false;
    }

    @Override
    public int contarActivos() {
        int contador = 0;
        for (Jugador jugador : jugadores) {
            if (!jugador.isEliminado()) {
                contador++;
            }
        }
        return contador;
    }
}
