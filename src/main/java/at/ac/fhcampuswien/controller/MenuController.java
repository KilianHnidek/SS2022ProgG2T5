package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.AppController;
import at.ac.fhcampuswien.Article;
import at.ac.fhcampuswien.NewsApi;
import at.ac.fhcampuswien.enums.CategoryEnum;
import at.ac.fhcampuswien.enums.CountryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;
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

import java.io.IOException;
import java.util.List;

public class MenuController {
    private final AppController ctrl = new AppController();

    private double mouseX;
    private int articleIndex, pageNumber = 1;
    public static int labelArticleCount = 0;



    @FXML
    private Pane emptyPane, anchorPane;
    @FXML
    private VBox vBoxArticlesLeft, vBoxArticlesRight;
    @FXML
    private ImageView pageFliphilip, pageFlifilipe;
    @FXML
    private Label allNewsAboutBitcoin,
            countArticles,
            quitProgram,
            topHeadlinesAustria;


    /**
     * GUI functions
     */

    @FXML
    void getTopHeadlinesAustria(MouseEvent event) throws IOException {
        NewsApi.query = "corona";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.at;
        chooseNews(ctrl.getTopHeadlinesAustria());
    }

    @FXML
    void getAllNewsAboutBitcoin(MouseEvent event) throws IOException {
        NewsApi.query = "bitcoin";
        NewsApi.endpointEnum = EndpointEnum.everything;
        NewsApi.countryEnum = null;
        chooseNews(ctrl.getAllNewsBitcoin());
    }

    void chooseNews (List<Article> articles) {
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
                l2.setText(articles.get(articleIndex).getAuthor());
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

    @FXML
    void handlePhilipTouched(MouseEvent event) throws IOException {
        // right
        pageNumber++;
        pageFlifilipe.setVisible(true);
        pageFlifilipe.setDisable(false);

        if (NewsApi.query.equals("bitcoin")) {
            getAllNewsAboutBitcoin(event);
        } else {
            getTopHeadlinesAustria(event);
        }
    }

    @FXML
    void handleFilipeTouched(MouseEvent event) throws IOException {
        // left
        pageNumber--;
        articleIndex -= 6;

        if (pageNumber == 0) {
            reloadMenu();
        } else {
            if (NewsApi.query.equals("bitcoin")) {
                getAllNewsAboutBitcoin(event);
            } else {
                getTopHeadlinesAustria(event);
            }
        }

    }

    void reloadMenu() {
        for (int i = vBoxArticlesLeft.getChildren().size() - 1; i >= 0; i--) {
            Node g = vBoxArticlesLeft.getChildren().get(i);
            //if (g.getClass().getSimpleName().equals("Label") && g != allNewsAboutBitcoin && g != countArticles && g != quitProgram && g != topHeadlinesAustria) {
                vBoxArticlesLeft.getChildren().remove(g);
            //}
        }
        for (int i = vBoxArticlesRight.getChildren().size() - 1; i >= 0; i--) {
            Node g = vBoxArticlesRight.getChildren().get(i);
            //if (g.getClass().getSimpleName().equals("Label") && g != allNewsAboutBitcoin && g != countArticles && g != quitProgram && g != topHeadlinesAustria) {
                vBoxArticlesRight.getChildren().remove(g);
            //}
        }

        pageFlifilipe.setDisable(true);
        pageFliphilip.setDisable(true);
        pageFlifilipe.setVisible(false);
        pageFliphilip.setVisible(false);

        for (Node n : anchorPane.getChildren()) {
            if (n.getClass().getSimpleName().equals("Label")) {
                n.setDisable(false);
                n.setVisible(true);
            }
        }

        pageNumber = 1;
    }

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
        stage.setX(event.getScreenX() - mouseX);
        stage.setY(event.getScreenY() - emptyPane.getHeight() / 2);
    }

    @FXML
    void handleMousePressed(MouseEvent event) {
        mouseX = event.getSceneX();
    }

    /**
     * MouseEvent handling functions
     */
}
