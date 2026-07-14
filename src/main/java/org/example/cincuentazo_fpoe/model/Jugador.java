package org.example.cincuentazo_fpoe.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Representa a un jugador de la partida, humano o maquina. Mantiene su
 * mano de cartas como una {@link ObservableList} para que la capa Vista
 * pueda reaccionar automaticamente a los cambios (patron Observer,
 * base del binding de JavaFX dentro de la arquitectura MVC).
 *
 * @author Nicolás Velasco, Daniel Toro
 */

public class Jugador {
    private final String nombre;
    private final boolean maquina;
    private final ObservableList<Carta> mano = FXCollections.observableArrayList();
    private boolean eliminado = false;

    public Jugador(String nombre, boolean maquina) {
        this.nombre = nombre;
        this.maquina = maquina;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isMaquina() {
        return maquina;
    }

    public ObservableList<Carta> getMano() {
        return mano;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void agregarCarta(Carta carta) {
        mano.add(carta);
    }

    public void quitarCarta(Carta carta) {
        mano.remove(carta);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
