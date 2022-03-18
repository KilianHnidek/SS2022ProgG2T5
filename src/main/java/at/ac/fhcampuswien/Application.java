package at.ac.fhcampuswien;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private final int windowHeight = 320;
    private final int windowWidth = 240;

    private Scene windowScene;
    private Stage windowStage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Application.fxml"));
        windowScene = new Scene(fxmlLoader.load(), windowHeight, windowWidth);

        windowStage.setTitle("Application");
        windowStage.setScene(windowScene);
        windowStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
