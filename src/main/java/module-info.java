module at.ac.fhcampuswien {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;


    opens at.ac.fhcampuswien to javafx.fxml;
    exports at.ac.fhcampuswien;
    exports at.ac.fhcampuswien.controller;
    opens at.ac.fhcampuswien.controller to javafx.fxml;
    exports at.ac.fhcampuswien.enums;
    opens at.ac.fhcampuswien.enums to javafx.fxml;
    exports at.ac.fhcampuswien.normalClass;
    opens at.ac.fhcampuswien.normalClass to javafx.fxml;
}