package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.*;
import at.ac.fhcampuswien.enums.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuController {
    public static int labelArticleCount = 0;

    @FXML
    private Pane emptyPane;
    @FXML
    private VBox customReqContainer;
    @FXML
    private ImageView waitingGif;
    @FXML
    private Label filter1, filter2, filter3, countArticles;
    @FXML
    private ChoiceBox<String> countrySelector, categorySelector, languageSelector, endPointSelector, sortBySelector;

    private static String filter1Text, filter2Text, filter3Text, countArticlesText;
    private static final String[] countries = {CountryEnum.at.name(), CountryEnum.de.name(), CountryEnum.us.name(),
                                               CountryEnum.fr.name(), CountryEnum.cz.name()},
                                  categories = {CategoryEnum.business.name(), CategoryEnum.entertainment.name(),
                                                CategoryEnum.general.name(), CategoryEnum.health.name(), CategoryEnum.science.name()},
                                  language = {LanguageEnum.de.name(), LanguageEnum.en.name(), LanguageEnum.fr.name(), LanguageEnum.es.name()},

                                  endPoint = {EndpointEnum.topHeadlines.getName(), EndpointEnum.everything.name()},

                                  sortBy = {SortByEnum.popularity.name(), SortByEnum.relevancy.name(), SortByEnum.publishedAt.name()};

    @FXML
    public void initialize() {
        filter1Text = filter1.getText();
        filter2Text = filter2.getText();
        filter3Text = filter3.getText();
        countArticlesText = countArticles.getText();

        //categorySelector.getItems().addAll(categories);           -> doesn't work
        //String[] x = {String.valueOf(CategoryEnum.values())};     -> doesn't work

        countrySelector.setValue("none");
        countrySelector.getItems().add("none");
        for (String element : countries) {
            countrySelector.getItems().add(element);
        }
        categorySelector.setValue("none");
        categorySelector.getItems().add("none");
        for (String element : categories) {
            categorySelector.getItems().add(element);
        }
        languageSelector.setValue("none");
        languageSelector.getItems().add("none");
        for (String element : language) {
            languageSelector.getItems().add(element);
        }
        endPointSelector.setValue("none");
        endPointSelector.getItems().add("none");
        for (String element : endPoint) {
            endPointSelector.getItems().add(element);
        }
        sortBySelector.setValue("none");
        sortBySelector.getItems().add("none");
        for (String element : sortBy) {
            sortBySelector.getItems().add(element);
        }
    }

    /*
     * GUI functions
     */

    @FXML
    void openCustomReqWindow(MouseEvent event) {

        customReqContainer.setVisible(!customReqContainer.isVisible());
    }

    @FXML
    void createCustomReq(ActionEvent event) {

                                                                            //endPoint can only be set to "everything" if countySelector is null
        NewsApi.endpointEnum = !(endPointSelector.getValue().equals("none")) && countrySelector.getValue().equals("none") ? endPointSelector.getValue() : EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = !(countrySelector.getValue().equals("none")) ? countrySelector.getValue() : null;
        NewsApi.categoryEnum = !(categorySelector.getValue().equals("none")) ? categorySelector.getValue() : null;
        NewsApi.sortByEnum = !(sortBySelector.getValue().equals("none")) ? sortBySelector.getValue() : null;
        //NewsApi.languageEnum = languageSelector.getValue();       -> no option to filter languages in NewsAPI



        try {
            showArticleScene(AppController.getArticles());

        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setcontent? settext?
            a.show();

            waitingGif.setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();

            waitingGif.setVisible(false);
        }

    }

    @FXML
    void toggleProviderWithMostArticles(MouseEvent event) {
        NewsApi.query = "";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

        try {
            Stream<Article> streamFromList = AppController.getArticles().stream();

            Map<String, Long> map = streamFromList.collect(Collectors.groupingBy(Article::getSourceName, Collectors.counting()));

            //befehl in entry variable speichern und damit arbeiten statt in string und long
            String name = map.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
            Long amount = map.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue();

            //filter1.setText(name + " has " + amount + " article(s)");
            String new_text = name + " has " + amount + " article(s)";
            filter1.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? new_text : filter1Text);

        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setContent? setText?
            a.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toggleAuthorWithLongestName(MouseEvent event) {
        NewsApi.query = "";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

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
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.us.name();

        try {
            Stream<Article> streamFromList = AppController.getArticles().stream();

            /*filter3.setText(String.valueOf                                      //converts to string
                    (streamFromList
                            .map(Article::getSourceName)                        //filters for source
                            .filter(s -> s.equals("New York Times"))            //filters for NYT
                            .count()));                                        //counts results
            */

            String new_text = String.valueOf(streamFromList.map(Article::getSourceName).filter(s -> s.equals("New York Times")).count());
            //String new_text = "" + streamFromList.filter(a -> a.getSourceName().equals("New York Times")).count();

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
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

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

            showArticleScene(res_articles);

        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setcontent? settext?
            a.show();

            waitingGif.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();

            waitingGif.setVisible(false);
        }
    }

    @FXML
    private void getTopHeadlinesAustria() {
        waitingGif.setVisible(true);

        NewsApi.query = "corona";
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

        try {
            showArticleScene(AppController.getArticles());

        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setcontent? settext?
            a.show();

            waitingGif.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();

            waitingGif.setVisible(false);
        }
    }

    @FXML
    private void getAllNewsBitcoin() {
        waitingGif.setVisible(true);

        NewsApi.query = "bitcoin";
        NewsApi.endpointEnum = EndpointEnum.everything.name();
        NewsApi.countryEnum = null;

        try {
            showArticleScene(AppController.getArticles());
        } catch (NewsApiException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.getMessage());
            //setcontent? settext?
            a.show();

            waitingGif.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();

            waitingGif.setVisible(false);
        }
    }

    private void showArticleScene(List<Article> articles) throws IOException, NewsApiException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("articleIrgendwas.fxml"));
        Scene articleScene = new Scene(fxmlLoader.load());
        ArticleIrgendwasController controller = fxmlLoader.getController();

        controller.chooseNews(articles);

        articleScene.setFill(Color.TRANSPARENT);

        new Timeline(new KeyFrame(Duration.seconds(1), e -> App.stage.setScene(articleScene))).play();
    }

    @FXML
    private void getArticleCount(MouseEvent event) {
        ((Label) event.getSource()).setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? labelArticleCount + "" : countArticlesText);
    }

    @FXML
    private void quitProgram(MouseEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
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
