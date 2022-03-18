package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.AppController;
import at.ac.fhcampuswien.Article;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class MenuController {

    private AppController ctrl = new AppController();

    private double mouseX;
    private int articleCounter = 12;

    @FXML
    private Pane emptyPane, anchorPane;
    @FXML
    private VBox vBoxArticlesLeft, vBoxArticlesRight;
    @FXML
    private Label allNewsAboutBitcoin, countArticles, quitProgram, topHeadlinesAustria;

    @FXML
    void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - mouseX);
        stage.setY(event.getScreenY() - emptyPane.getHeight() / 2);
    }

    @FXML
    void handleMousePressed(MouseEvent event) {
        mouseX = event.getSceneX();
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
        for (Node n : anchorPane.getChildren()) {
            if (n.getClass().getSimpleName().equals("Label")) {
                n.setDisable(true);
                n.setVisible(false);
            }
        }

        int counterLeftRight = 0;

        List<Article> articles = ctrl.getTopHeadlinesAustria();

        for (int k = articleCounter; k < articleCounter + 6; k++) {

            if (articleCounter < articles.size()) {
                Label l1 = new Label();
                l1.alignmentProperty().set(Pos.CENTER);
                l1.setText(articles.get(articleCounter).getTitle());
                l1.setFont(Font.font("Times New Roman", 16));
                l1.setPadding(new Insets(0, 0, 5, 0)); // top, right, bottom, left

                Label l2 = new Label();
                l2.alignmentProperty().set(Pos.CENTER);
                l2.setText(articles.get(articleCounter).getAuthor());
                l2.setFont(Font.font("Times New Roman", 12));
                l2.setPadding(new Insets(0, 0, 20, 0)); // top, right, bottom, left


                if (counterLeftRight < 3) {
                    vBoxArticlesLeft.getChildren().add(l1);
                    vBoxArticlesLeft.getChildren().add(l2);
                } else {
                    vBoxArticlesRight.getChildren().add(l1);
                    vBoxArticlesRight.getChildren().add(l2);
                }
                counterLeftRight++;
                articleCounter++;

                if (counterLeftRight % 6 == 0) k = articleCounter + 50000;
            } else {
                k = articleCounter + 50000;
            }
        }
            counterLeftRight = 0;

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