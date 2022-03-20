module at.ac.fhcampuswien {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.ac.fhcampuswien to javafx.fxml;
    exports at.ac.fhcampuswien;
    exports at.ac.fhcampuswien.controller;
    opens at.ac.fhcampuswien.controller to javafx.fxml;
}