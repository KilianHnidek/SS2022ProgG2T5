package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.*;
import at.ac.fhcampuswien.downloader.ParallelDownloader;
import at.ac.fhcampuswien.downloader.SequentialDownloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class ArticleIrgendwasController {
    private int pageNumber = 1;
    private int articleIndex;
    private boolean isSorted;

    @FXML
    private Pane emptyPane;
    @FXML
    private VBox vBoxArticlesLeft, vBoxArticlesRight;
    @FXML
    private ImageView pageFliphilip, pageFlifilipe;

    @FXML
    void sortAfterAscendDescLen() {
        isSorted = true;

        Stream<Article> streamFromList = AppController.getAppController().getArticles().stream();

        chooseNews(streamFromList.filter(Objects::nonNull).sorted(
                Comparator.comparing(Article::getDescription, Comparator.comparingInt(String::length)
                        .thenComparing(String::compareTo))
        ).toList());
    }

    @FXML
    private void handleMousePressed(MouseEvent event) {
        App.mouseX = event.getSceneX();
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        randomMethods.handleMouseDragged(event, emptyPane);
    }

    @FXML
    private void handlePhilipTouched(){
        // right
        pageNumber++;
        pageFlifilipe.setVisible(true);
        pageFlifilipe.setDisable(false);

        if (isSorted) sortAfterAscendDescLen();
        else chooseNews(AppController.getAppController().getArticles());
    }

    @FXML
    private void handleFilipeTouched() throws IOException{
        // left
        pageNumber--;
        articleIndex -= 6;

        if (pageNumber == 0) {
            reloadMenu();
        } else {
            if (isSorted) sortAfterAscendDescLen();
            else chooseNews(AppController.getAppController().getArticles());
        }
    }

    public void chooseNews(List<Article> articles) {

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


        for (int k = articleIndex; k < articleIndex + 6; k++) {

            if (articleIndex < articles.size()) {
                Text title = new Text();
                title.setText(articles.get(articleIndex).getTitle());
                title.setFont(Font.font("Times New Roman", 16));
                title.setWrappingWidth(260);

                Label author = new Label();
                author.alignmentProperty().set(Pos.CENTER);
                author.setText(articles.get(articleIndex).getAuthor() != null ? articles.get(articleIndex).getAuthor() : "No Author");
                author.setFont(Font.font("Times New Roman", 14));
                author.setPadding(new Insets(0, 0, 25, 0)); // top, right, bottom, left
                author.setPrefWidth(260);

                if (counterLeftRight < 3) {
                    vBoxArticlesLeft.getChildren().add(title);
                    vBoxArticlesLeft.getChildren().add(author);
                } else {
                    vBoxArticlesRight.getChildren().add(title);
                    vBoxArticlesRight.getChildren().add(author);
                }

                int finalK = k;
                title.setOnMouseClicked(ev -> {
                    try {
                        articles.get(finalK).downloadArticle();
                    } catch (MalformedURLException e) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("URL fails to comply with the specific syntax of the associated protocol");
                        //setcontent? settext?
                        a.show();
                    } catch (IOException e) {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("URL can not be opened");
                        //setcontent? settext?
                        a.show();
                    }
                });

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

    public void downloadArticles() {


        try {
            ParallelDownloader p = new ParallelDownloader();
            long startPar = System.currentTimeMillis();
            p.process(AppController.getAppController().downloadURLs());
            long endPar = System.currentTimeMillis();

            long startSeq;
            long endSeq;

            SequentialDownloader s = new SequentialDownloader();
            startSeq = System.currentTimeMillis();
            s.process(AppController.getAppController().downloadURLs());
            endSeq = System.currentTimeMillis();


            long durSeq = endSeq - startSeq;
            long durPar = endPar - startPar;

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            if (durPar > durSeq) {
                a.setContentText("Der sequentielle Download war um "
                        + (durPar - durSeq) +
                        "ms schneller als der parallele Download.");
            } else if (durPar < durSeq) {
                a.setContentText("Der sequentielle Download war um "
                        + (durSeq - durPar) +
                        "ms langsamer als der parallele Download.");
            } else {
                a.setContentText("Der sequentielle Download war gleich schnell wie"
                        + " der parallele Download.");
            }
            a.show();

        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Problem while downloading");
            System.out.println("hallo");
            a.show();
        }
    }
}
