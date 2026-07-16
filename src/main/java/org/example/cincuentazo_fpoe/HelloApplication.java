package org.example.cincuentazo_fpoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada de la aplicacion JavaFX. Carga la pantalla de
 * configuracion inicial (HU-1), donde el jugador elige cuantos
 * jugadores maquina participaran.
 *
 * @author Nicolas Velasco, Daniel Toro - Cincuentazo
 */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/org/example/cincuentazo_fpoe/configuracion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(
                getClass().getResource("/org/example/cincuentazo_fpoe/styles.css").toExternalForm());
        stage.setTitle("Cincuentazo");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}