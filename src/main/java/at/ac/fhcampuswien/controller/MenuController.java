package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.*;
import at.ac.fhcampuswien.enums.CountryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuController {
    private final AppController ctrl = new AppController();
    public static int labelArticleCount = 0;
    //private Stage popUpWindow = new Stage();

    @FXML
    private Pane emptyPane;
    @FXML
    private ImageView waitingGif;
    @FXML
    private Label filter1, filter2, filter3;

    private static String filter1Text, filter2Text, filter3Text;

    @FXML
    public void initialize() {
        filter1Text = filter1.getText();
        filter2Text = filter2.getText();
        filter3Text = filter3.getText();
    }

    /*
     * GUI functions
     */

    @FXML
    void toggleProviderWithMostArticles(MouseEvent event) {
        NewsApi.query = "";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.at;

        try {
            Stream<Article> streamFromList = AppController.getArticles().stream();

            Map<String, Long> map = streamFromList.collect(Collectors.groupingBy(Article::getSourceName, Collectors.counting()));

            //befehl in entry variable speichern und damit arbeiten statt in string und long
            String name = map.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            Long amount = map.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue();

            //filter1.setText(name + " has " + amount + " article(s)");
            String new_text = name + " has " + amount + " article(s)";
            filter1.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? new_text : filter1Text);

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
    void toggleAuthorWithLongestName(MouseEvent event) {
        NewsApi.query = "";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.at;

        try {
            Stream<Article> streamFromList = AppController.getArticles().stream();

            /*filter2.setText
                    (streamFromList
                            .map(Article::getAuthor)                                //filters for author
                            .filter(Objects::nonNull)                               //removes null authors
                            .max(Comparator.comparingInt(String::length))           //filters for longest string
                            .get()                                                  //removes "Optional" before author name
                    );
            */

            String new_text = streamFromList.map(Article::getAuthor).filter(Objects::nonNull).max(Comparator.comparingInt(String::length)).get();
            filter2.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? new_text : filter2Text);
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
    void toggleNewYorkTimesArticleCount(MouseEvent event) {
        NewsApi.query = "";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.us;

        try {
            Stream<Article> streamFromList = AppController.getArticles().stream();

            /*filter3.setText(String.valueOf                                      //converts to string
                    (streamFromList
                            .map(Article::getSourceName)                        //filters for source
                            .filter(s -> s.equals("New York Times"))            //filters for NYT
                            .count()));                                        //counts results
            */

            String new_text = String.valueOf(streamFromList.map(Article::getSourceName).filter(s -> s.equals("New York Times")).count());
            filter3.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? new_text : filter3Text);
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
    private void showArticlesWithShortHeadlines() {
        waitingGif.setVisible(true);

        NewsApi.query = "";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.at;

        try {
            List<Article> all_articles = AppController.getArticles();
            Stream<Article> streamFromList = all_articles.stream();

            List<Article> res_articles = new ArrayList<>();
            streamFromList
                    .map(Article::getTitle)
                    .filter(Objects::nonNull)
                    .filter(t -> t.length() < 15)
                    .forEach(t -> res_articles.add(
                            all_articles.stream().filter(
                                    a -> a.getTitle().equals(t)
                            ).findFirst().get()
                    ));

            //System.out.println(all_articles);

            showArticleScene(res_articles);

            /*FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("articleIrgendwas.fxml"));
            Scene articleScene = new Scene(fxmlLoader.load());
            ArticleIrgendwasController controller = fxmlLoader.getController();

            controller.chooseNews(res_articles);

            articleScene.setFill(Color.TRANSPARENT);
            App.stage.setScene(articleScene);*/
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
    private void getTopHeadlinesAustria(MouseEvent event) {
        waitingGif.setVisible(true);

        NewsApi.query = "corona";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.countryEnum = CountryEnum.at;

        try {
            showArticleScene(ctrl.getTopHeadlinesAustria());
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
    private void getAllNewsBitcoin(MouseEvent event) {
        waitingGif.setVisible(true);

        NewsApi.query = "bitcoin";
        NewsApi.endpointEnum = EndpointEnum.everything;
        NewsApi.countryEnum = null;

        try {
            showArticleScene(ctrl.getAllNewsBitcoin());
        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setcontent? settext?
            a.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showArticleScene(List<Article> articles) throws IOException, NewsApiException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("articleIrgendwas.fxml"));
        Scene articleScene = new Scene(fxmlLoader.load());
        ArticleIrgendwasController controller = fxmlLoader.getController();

        controller.chooseNews(articles);

        /*double posX = App.stage.getX() + App.stage.getWidth() / 2 - 32;
        double posY = App.stage.getY() + App.stage.getHeight() / 2 - 32;

        ImageView waitingGif = new ImageView(new Image(Objects.requireNonNull(App.class.getResourceAsStream("assets/waiting.gif"))));
        waitingGif.setFitWidth(64);
        waitingGif.setFitHeight(64);

        Stage popUpWindow = new Stage();
        popUpWindow.setX(posX);
        popUpWindow.setY(posY);
        popUpWindow.setAlwaysOnTop(true);
        popUpWindow.initStyle(StageStyle.TRANSPARENT);
        popUpWindow.setScene(new Scene(new Group(waitingGif), 64, 64));
        popUpWindow.getScene().setFill(Color.TRANSPARENT);
        popUpWindow.show();*/
        articleScene.setFill(Color.TRANSPARENT);

        new Timeline(new KeyFrame(Duration.seconds(1), e -> App.stage.setScene(articleScene))).play();
    }

    @FXML
    private void getArticleCount(MouseEvent event) {
        ((Label) event.getSource()).setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? labelArticleCount + "" : "count articles");
    }

    @FXML
    private void quitProgram(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /*
     * MouseEvent handling functions
     */

    @FXML
    private void handleLabelsMouseHovered(MouseEvent event) {
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
}
