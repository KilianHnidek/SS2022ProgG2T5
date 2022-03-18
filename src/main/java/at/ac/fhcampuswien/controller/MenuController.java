package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.AppController;
import at.ac.fhcampuswien.Article;
import at.ac.fhcampuswien.Menu;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

import java.util.List;

public class MenuController {
    private AppController ctrl = new AppController();

    @FXML
    private Pane emptyPane;

    @FXML
    private Label allnewsaboutbitcoin, countarticles, quitprogram, topheadlinesaustria;

    @FXML
    void handleMouseEntered(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - stage.getWidth() / 2);
        stage.setY(event.getScreenY() - emptyPane.getHeight() / 2);
    }

    @FXML
    void getAllNewsAboutBitcoin(MouseEvent event) {
        List<Article> articles = ctrl.getAllNewsBitcoin();
    }

    @FXML
    void getArticleCount(MouseEvent event) {
        ctrl.getArticleCount();
    }

    @FXML
    void getTopHeadlinesAustria(MouseEvent event) {
        ctrl.getTopHeadlinesAustria();
    }

    @FXML
    void quitProgram(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleMouseEvent(MouseEvent event) {
        ((Label) event.getSource()).setUnderline(MouseEvent.MOUSE_ENTERED == event.getEventType());
    }
}