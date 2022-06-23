package at.ac.fhcampuswien;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class randomMethods {
    public static void handleMouseDragged(MouseEvent event, Pane emptyPane) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - App.mouseX);
        stage.setY(event.getScreenY() - emptyPane.getHeight() / 2);
    }
}
