package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.AppController;
import at.ac.fhcampuswien.Article;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class MenuController {
    private final AppController ctrl = new AppController();

    private double mouseX;
    private int articleCounter, pageNumber = 1;
    private boolean bitcoin;

    {
        ctrl.setArticles(AppController.generateMockList());
    }

    @FXML
    private Pane emptyPane, anchorPane;
    @FXML
    private VBox vBoxArticlesLeft, vBoxArticlesRight;
    @FXML
    private ImageView pageFliphilip, pageFlifilipe;

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

        bitcoin = true;

        if (pageNumber * 6 <= articles.size()) {
            pageFliphilip.setVisible(true);
            pageFliphilip.setDisable(false);
        } else {
            pageFliphilip.setVisible(false);
            pageFliphilip.setDisable(true);
        }


        int counterLeftRight = 0;
        articleCounter = pageNumber * 6 - 6;

        vBoxArticlesLeft.getChildren().clear();
        vBoxArticlesRight.getChildren().clear();

        for (Node n : anchorPane.getChildren()) {
            if (n.getClass().getSimpleName().equals("Label")) {
                n.setDisable(true);
                n.setVisible(false);
            }
        }

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

                if (counterLeftRight % 6 == 0) k = articleCounter + 50000000;
            } else {
                k = articleCounter + 50000000;
            }
        }
    }

    @FXML
    void getArticleCount(MouseEvent event) {
        ctrl.getArticleCount();
    }

    @FXML
    void handlePhilipTouched(MouseEvent event) {
        // right
        pageNumber++;
        pageFlifilipe.setVisible(true);
        pageFlifilipe.setDisable(false);

        if (bitcoin) {
            getAllNewsAboutBitcoin(event);
        } else {
            getTopHeadlinesAustria(event);
        }
    }

    @FXML
    void handleFilipeTouched(MouseEvent event) {
        // left
        pageNumber--;
        articleCounter -= 6;

        if (pageNumber == 1) {
            pageFlifilipe.setVisible(false);
            pageFlifilipe.setDisable(true);
        }

        if (bitcoin) {
            getAllNewsAboutBitcoin(event);
        } else {
            getTopHeadlinesAustria(event);
        }
    }

    @FXML
    void getTopHeadlinesAustria(MouseEvent event) {
        bitcoin = false;

        if (pageNumber * 6 <= ctrl.getTopHeadlinesAustria().size()) {
            pageFliphilip.setVisible(true);
            pageFliphilip.setDisable(false);
        } else {
            pageFliphilip.setVisible(false);
            pageFliphilip.setDisable(true);
        }

        List<Article> articles = ctrl.getTopHeadlinesAustria();
        int counterLeftRight = 0;
        articleCounter = pageNumber * 6 - 6;

        vBoxArticlesLeft.getChildren().clear();
        vBoxArticlesRight.getChildren().clear();

        for (Node n : anchorPane.getChildren()) {
            if (n.getClass().getSimpleName().equals("Label")) {
                n.setDisable(true);
                n.setVisible(false);
            }
        }

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

                if (counterLeftRight % 6 == 0) k = articleCounter + 50000000;
            } else {
                k = articleCounter + 50000000;
            }
        }
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