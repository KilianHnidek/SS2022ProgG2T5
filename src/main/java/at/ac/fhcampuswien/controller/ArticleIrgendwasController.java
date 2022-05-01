package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

public class ArticleIrgendwasController {
    private final AppController ctrl = new AppController();
    private int pageNumber = 1;
    private int articleIndex;

    @FXML
    private Pane anchorPane, emptyPane;
    @FXML
    private VBox vBoxArticlesLeft, vBoxArticlesRight;
    @FXML
    private ImageView pageFliphilip, pageFlifilipe;

    @FXML
    private void handleMousePressed(MouseEvent event) {
        App.mouseX = event.getSceneX();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        randomMethods.handleMouseDragged(event, emptyPane);
    }

    @FXML
    private void handlePhilipTouched(MouseEvent event) throws IOException {
        // right
        pageNumber++;
        pageFlifilipe.setVisible(true);
        pageFlifilipe.setDisable(false);

        if (NewsApi.query.equals("bitcoin")) {
            chooseNews(ctrl.getAllNewsBitcoin());
        } else {
            chooseNews(ctrl.getTopHeadlinesAustria());
        }
    }

    @FXML
    private void handleFilipeTouched(MouseEvent event) throws IOException {
        // left
        pageNumber--;
        articleIndex -= 6;

        if (pageNumber == 0) {
            reloadMenu();
        } else {
            if (NewsApi.query.equals("bitcoin")) {
                chooseNews(ctrl.getAllNewsBitcoin());
            } else {
                chooseNews(ctrl.getTopHeadlinesAustria());
            }
        }
    }

    public void chooseNews (List<Article> articles) {
        if (pageNumber * 6 <= articles.size()) {
            pageFliphilip.setVisible(true);
            pageFliphilip.setDisable(false);
        } else {
            pageFliphilip.setVisible(false);
            pageFliphilip.setDisable(true);
        }

        pageFlifilipe.setVisible(true);
        pageFlifilipe.setDisable(false);

        int counterLeftRight = 0;
        articleIndex = pageNumber * 6 - 6;

        vBoxArticlesLeft.getChildren().clear();
        vBoxArticlesRight.getChildren().clear();

        for (Node n : anchorPane.getChildren()) {
            if (n.getClass().getSimpleName().equals("Label")) {
                n.setDisable(true);
                n.setVisible(false);
            }
        }

        for (int k = articleIndex; k < articleIndex + 6; k++) {

            if (articleIndex < articles.size()) {
                Label l1 = new Label();
                l1.alignmentProperty().set(Pos.CENTER);
                l1.setText(articles.get(articleIndex).getTitle());
                l1.setFont(Font.font("Times New Roman", 16));
                l1.setPadding(new Insets(0, 0, 5, 0)); // top, right, bottom, left

                Label l2 = new Label();
                l2.alignmentProperty().set(Pos.CENTER);
                l2.setText(articles.get(articleIndex).getAuthor() != null ? articles.get(articleIndex).getAuthor() :"No Author");
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
                articleIndex++;

                if (counterLeftRight % 6 == 0) break;
            } else {
                break;
            }
        }
    }

    private void reloadMenu() throws IOException {
        pageNumber = 1;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("menu.fxml"));
        App.scene = new Scene(fxmlLoader.load());
        App.scene.setFill(Color.TRANSPARENT);
        App.stage.setScene(App.scene);
    }
}
