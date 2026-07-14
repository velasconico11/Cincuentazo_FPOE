module org.example.cincuentazo_fpoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cincuentazo_fpoe to javafx.fxml;
    exports org.example.cincuentazo_fpoe;
    exports org.example.cincuentazo_fpoe.controller;
    opens org.example.cincuentazo_fpoe.controller to javafx.fxml;
}