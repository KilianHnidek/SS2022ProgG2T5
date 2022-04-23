package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.App;
import at.ac.fhcampuswien.AppController;
import at.ac.fhcampuswien.NewsApi;
import at.ac.fhcampuswien.enums.CountryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MenuController {

    private final AppController ctrl = new AppController();
    public static int labelArticleCount = 0;
    private Stage popUpWindow = new Stage();

    @FXML
    private Pane emptyPane;
    @FXML
    private ImageView waitingGif;

    /**
     * GUI functions
     */

    @FXML
    void getTopHeadlinesAustria(MouseEvent event) throws IOException {

        NewsApi.query = "corona";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.at;


        waitingPopUp(event);

    }

    private void waitingPopUp(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("articleIrgendwas.fxml"));
        Scene popUpScene = new Scene(fxmlLoader.load());
        ArticleIrgendwasController controller = fxmlLoader.getController();

        if (NewsApi.query == "bitcoin") {
            controller.chooseNews(ctrl.getAllNewsBitcoin());
        } else {
            controller.chooseNews(ctrl.getTopHeadlinesAustria());
        }

        double posx = App.stage.getX() + App.stage.getWidth()/2 - 32;
        double posy = App.stage.getY() + App.stage.getHeight()/2 - 32;

        ImageView waitingGif = new ImageView(new Image(Objects.requireNonNull(App.class.getResourceAsStream("assets/waiting.gif"))));
        waitingGif.setFitWidth(64);
        waitingGif.setFitHeight(64);

        Stage popUpWindow = new Stage();
        popUpWindow.setX(posx);
        popUpWindow.setY(posy);
        popUpWindow.setAlwaysOnTop(true);
        popUpWindow.initStyle(StageStyle.TRANSPARENT);
        popUpWindow.setScene(new Scene(new Group(waitingGif), 64, 64));
        popUpWindow.getScene().setFill(Color.TRANSPARENT);
        popUpWindow.show();

        new Timeline(new KeyFrame(Duration.seconds(2), e -> {

            popUpScene.setFill(Color.TRANSPARENT);
            App.stage.setScene(popUpScene);
            popUpWindow.close();
        })).play();
    }

    @FXML
    void getAllNewsAboutBitcoin(MouseEvent event) throws IOException {

        NewsApi.query = "bitcoin";
        NewsApi.endpointEnum = EndpointEnum.everything;
        NewsApi.countryEnum = null;

        waitingPopUp(event);
    }

    @FXML
    void getArticleCount(MouseEvent event) {
        ((Label) event.getSource()).setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? labelArticleCount + "" : "count Articles");
    }

    @FXML
    void quitProgram(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * GUI functions
     */


    /**
     * Next and previous page
     */



    /**
     * Next and previous page
     */


    /**
     * MouseEvent handling functions
     */

    @FXML
    void handleLabelsMouseHovered(MouseEvent event) {
        ((Label) event.getSource()).setUnderline(MouseEvent.MOUSE_ENTERED == event.getEventType());
    }

    @FXML
    void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - App.mouseX);
        stage.setY(event.getScreenY() - emptyPane.getHeight() / 2);
    }

    @FXML
    void handleMousePressed(MouseEvent event) {
        App.mouseX = event.getSceneX();
    }

    /**
     * MouseEvent handling functions
     */
}
