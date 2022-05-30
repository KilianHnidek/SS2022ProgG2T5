package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.*;
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
import java.util.stream.Stream;

public class ArticleIrgendwasController {
    private final AppController ctrl = new AppController();
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
    void sortAfterAscendDescLen(MouseEvent event) {

        isSorted = true;
        try {
                  /**Just for testing purposes*/
            /*
            List<String> list = Arrays.asList(
                    ". Jetzt kostenlos online ansehen!", "Wiener bot bei 1:0 gegen Liverpool starke Leistung – 'Alaba über- überglücklich'",
                    "Alle Informationen rund um die Lage in der Ukraine sowie Reaktionen aus aller Welt finden Sie hier.",
                    "Zier finden Sie alle Live-Ticker, Spielpläne, Fußball Ergebnisse, Tabellen, Tipps und Spielberichte der Admiral Bundesliga - Österreich",
                    "Fier finden Sie alle Live-Ticker, Spielpläne, Fußball Ergebnisse, Tabellen, Tipps und Spielberichte der Admiral Bundesliga - Österreich",
                    "Die Nummer 1 kommt wieder von Oskar Haag. Bibiza & Mola starten rasant und steigen auf Platz 6 ein.",
                    "Das wochenlange Gerichtsverfahren zwischen Amber Heard und Johnny Depp hat am Freitag geendet. Jetzt berät die Jury.",
                    "Hier finden Sie alle Live-Ticker, Spielpläne, Fußball Ergebnisse, Tabellen, Tipps und Spielberichte der Admiral Bundesliga - Österreich",
                    "Hitzige Debatte um die umstrittene Nordostumfahrung. Die rote Parteijugend probte wortgewaltig den Aufstand, war aber deutlich in der Minderheit.",
                    "Romy Schneider starb vor 40 Jahren. Doch die Stadt Berlin lehnt ein Gedenken an sie ab. Obwohl die Schauspielerin dort ihre glücklichste Zeit hatte.",
                    " Jetzt kostenlos online ansehen!", "Wiener bot bei 1:0 gegen Liverpool starke Leistung – 'Alaba über- überglücklich'"
                    );
            Stream<String> streamFromList = list.stream();

            //Comparator<String> compByLength = (aName, bName) -> aName.length() - bName.length(); */     /**How to write a comparator */


            Stream<Article> streamFromList = AppController.getArticles().stream();


            chooseNews(streamFromList.filter(Objects::nonNull).sorted(
                    Comparator.comparing(Article::getDescription, Comparator.comparingInt(String::length)
                            .thenComparing(String::compareTo))
            ).toList());


            Stream<Article> streamFromList2 = AppController.getArticles().stream();

            streamFromList2
                    .map(Article::getDescription)                       //filters for description
                    .filter(Objects::nonNull)                           //removes null descriptions
                    .sorted(Comparator.comparingInt(String::length)     //sorts by length
                            .thenComparing(String::compareTo))          //and then alphabetically
                    .forEach(System.out::println);                      //prints the list

            //String new_text = "" + streamFromList.filter(a -> a.getSourceName().equals("New York Times")).count();


        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setcontent? settext?
            a.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void handlePhilipTouched(MouseEvent event) throws NewsApiException {
        // right
        pageNumber++;
        pageFlifilipe.setVisible(true);
        pageFlifilipe.setDisable(false);


        if (isSorted) sortAfterAscendDescLen(event);
        else chooseNews(AppController.getArticles());
    }

    @FXML
    private void handleFilipeTouched(MouseEvent event) throws IOException, NewsApiException {
        // left
        pageNumber--;
        articleIndex -= 6;

        if (pageNumber == 0) {
            reloadMenu();
        } else {
            if (isSorted) sortAfterAscendDescLen(event);
            else chooseNews(AppController.getArticles());
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


        for (int k = articleIndex; k < articleIndex + 6; k++) {

            if (articleIndex < articles.size()) {
                Text title = new Text();
                title.setText(articles.get(articleIndex).getTitle());
                title.setFont(Font.font("Times New Roman", 16));
                title.setWrappingWidth(260);

                Label author = new Label();
                author.alignmentProperty().set(Pos.CENTER);
                author.setText(articles.get(articleIndex).getAuthor() != null ? articles.get(articleIndex).getAuthor() :"No Author");
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
}
