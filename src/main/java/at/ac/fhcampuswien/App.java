package at.ac.fhcampuswien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static Stage stage;
    public static Scene scene;
    public static double mouseX;

    @Override
    public void start(Stage stage) throws IOException {

        App.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        scene = new Scene(fxmlLoader.load());

        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("assets/newsdingsta.png"))));

        // background-less
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        // border-less

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*package at.ac.fhcampuswien;

public class App {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}
 */