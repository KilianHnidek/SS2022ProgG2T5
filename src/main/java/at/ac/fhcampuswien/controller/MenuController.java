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
import javafx.scene.control.*;
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

    private static String filter1Text, filter2Text, filter3Text, countArticlesText;
    public static AppController controller = new AppController();

    @FXML
    private Pane emptyPane;
    @FXML
    private VBox customReqContainer;
    @FXML
    private ImageView waitingGif;
    @FXML
    private Label filter1, filter2, filter3, countArticles;
    @FXML
    private TextField querySelector;
    @FXML
    private Button customReqButton;
    @FXML
    private ComboBox<String> countrySelector, categorySelector, languageSelector, endPointSelector, sortBySelector;

    private final ComboBox<String>[] comboBoxes = new ComboBox[3];


    @FXML
    public void initialize() {

        comboBoxes[0] = countrySelector;
        comboBoxes[1] = categorySelector;
        comboBoxes[2] = languageSelector;

        NewsApi.query = "";

        filter1Text = filter1.getText();
        filter2Text = filter2.getText();
        filter3Text = filter3.getText();
        countArticlesText = countArticles.getText();

        countrySelector.setValue("none");
        countrySelector.getItems().add("none");
        for (CountryEnum element : CountryEnum.values()) {
            countrySelector.getItems().add(String.valueOf(element));
        }

        categorySelector.setValue("none");
        categorySelector.getItems().add("none");
        for (CategoryEnum element : CategoryEnum.values()) {
            categorySelector.getItems().add(String.valueOf(element));
        }

        languageSelector.setValue("none");
        languageSelector.getItems().add("none");
        for (LanguageEnum element : LanguageEnum.values()) {
            languageSelector.getItems().add(String.valueOf(element));
        }

        endPointSelector.setValue("none");
        endPointSelector.getItems().add("none");
        for (EndpointEnum element : EndpointEnum.values()) {
            endPointSelector.getItems().add(String.valueOf(element.getName()));
        }
        sortBySelector.setValue("none");
        sortBySelector.getItems().add("none");
        for (SortByEnum element : SortByEnum.values()) {
            sortBySelector.getItems().add(String.valueOf(element));
        }
    }

    /*
     * GUI functions
     */


    @FXML
    void updateQueryParams() {


        customReqButton.setDisable(true);

        if (querySelector.getText().equals("")) {
            for (ComboBox cb : comboBoxes) {

                if (!cb.getValue().equals("none")) {
                    customReqButton.setDisable(false);
                }
            }
        } else customReqButton.setDisable(false);

        if (!countrySelector.getValue().equals("none") || querySelector.getText().equals("")) {

            endPointSelector.setValue(EndpointEnum.topHeadlines.getName());
            endPointSelector.setDisable(true);

        } else if (countrySelector.getValue().equals("none") && !querySelector.getText().equals("")) {

            if (endPointSelector.isDisabled()) endPointSelector.setValue("none");
            endPointSelector.setDisable(false);
        }

        if (endPointSelector.getValue().equals(EndpointEnum.everything.getName())) {
            categorySelector.setValue("none");
            categorySelector.setDisable(true);
            countrySelector.setDisable(true);
            countrySelector.setValue("none");
        } else {
            categorySelector.setDisable(false);
            countrySelector.setDisable(false);
        }
    }

    @FXML
    void openCustomReqWindow() {
        updateQueryParams();
        customReqContainer.setVisible(!customReqContainer.isVisible());
    }

    @FXML
    void createCustomReq() {

        waitingGif.setVisible(true);
        NewsApi.query = querySelector.getText();
                                                                            //endPoint can only be set to "everything" if countySelector is null
        NewsApi.endpointEnum = !(endPointSelector.getValue().equals("none")) ? endPointSelector.getValue() : EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = !(countrySelector.getValue().equals("none")) ? countrySelector.getValue() : null;
        NewsApi.categoryEnum = !(categorySelector.getValue().equals("none")) ? categorySelector.getValue() : null;
        NewsApi.sortByEnum = !(sortBySelector.getValue().equals("none")) ? sortBySelector.getValue() : null;
        NewsApi.languageEnum = !(languageSelector.getValue().equals("none")) ? languageSelector.getValue() : null;


        try {
            controller.setArticles(AppController.requestArticles());
            showArticleScene(controller.getArticles());

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
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

        try {

            Stream<Article> streamFromList = AppController.requestArticles().stream();

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
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

        try {
            Stream<Article> streamFromList = AppController.requestArticles().stream();

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
        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.us.name();

        try {
            Stream<Article> streamFromList = AppController.requestArticles().stream();

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

        NewsApi.endpointEnum = EndpointEnum.topHeadlines.getName();
        NewsApi.countryEnum = CountryEnum.at.name();

        try {
            List<Article> all_articles = AppController.requestArticles();
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

            controller.setArticles(res_articles);
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
            controller.setArticles(AppController.requestArticles());
            showArticleScene(controller.getArticles());

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
            controller.setArticles(AppController.requestArticles());
            showArticleScene(controller.getArticles());

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

        new Timeline(new KeyFrame(Duration.seconds(3), e -> App.stage.setScene(articleScene))).play();
    }

    @FXML
    private void getArticleCount(MouseEvent event) {
        ((Label) event.getSource()).setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ? controller.getArticleCount() + "" : countArticlesText);
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
