package org.example.cincuentazo_fpoe.controller;

import org.example.cincuentazo_fpoe.model.Juego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de la pantalla inicial de configuracion (HU-1: Inicio del
 * juego). Permite elegir con cuantos jugadores maquina (1, 2 o 3) se
 * jugara, y da paso a la pantalla principal del juego.
 *
 * @author Nicolás Velasco
 */
public class ConfiguracionController {

    @FXML
    private RadioButton radioUno;
    @FXML
    private RadioButton radioDos;
    @FXML
    private RadioButton radioTres;

    @FXML
    private void initialize() {
        radioUno.setSelected(true);
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
        int cantidadMaquinas = 1;
        if (radioDos.isSelected()) {
            cantidadMaquinas = 2;
        } else if (radioTres.isSelected()) {
            cantidadMaquinas = 3;
        }

        try {
            Juego juego = new Juego(cantidadMaquinas);
            juego.iniciarJuego();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/edu/univalle/cincuentazo/juego.fxml"));
            Parent root = loader.load();
            JuegoController controller = loader.getController();
            controller.setJuego(juego);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/co/edu/univalle/cincuentazo/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Cincuentazo - En juego");
            stage.setResizable(false);
        } catch (IOException e) {
            mostrarError("No se pudo cargar la pantalla del juego: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
        alert.setHeaderText("Ocurrio un error");
        alert.showAndWait();
    }
}
