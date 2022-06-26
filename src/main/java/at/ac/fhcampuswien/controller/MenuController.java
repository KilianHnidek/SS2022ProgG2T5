package at.ac.fhcampuswien.controller;

import at.ac.fhcampuswien.*;
import at.ac.fhcampuswien.enums.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    private static String filter1Text, filter2Text, filter3Text,
            countArticlesText;

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
    private ComboBox<String> countrySelector, categorySelector,
            languageSelector, endPointSelector, sortBySelector;

    private final ComboBox<String>[] comboBoxes = new ComboBox[3];

    @FXML
    public void initialize() {
        comboBoxes[0] = countrySelector;
        comboBoxes[1] = categorySelector;
        comboBoxes[2] = languageSelector;

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
            endPointSelector.getItems().add(String.valueOf(element
                    .getName()));
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

        if (!countrySelector.getValue().equals("none") || querySelector
                .getText().equals("")) {

            endPointSelector.setValue(EndpointEnum.topHeadlines.getName());
            endPointSelector.setDisable(true);

        } else if (countrySelector.getValue().equals("none") && !querySelector
                .getText().equals("")) {

            if (endPointSelector.isDisabled()) endPointSelector
                    .setValue("none");
            endPointSelector.setDisable(false);
        }

        if (endPointSelector.getValue().equals(EndpointEnum.everything
                .getName())) {
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
        NewsApi.getNewsApi().setQuery(querySelector.getText());
        //endPoint can only be set to "everything" if countySelector is null
        NewsApi.getNewsApi().setEndpointEnum(!(endPointSelector.getValue()
                .equals("none")) ? endPointSelector.getValue() :
                EndpointEnum.topHeadlines.getName());
        NewsApi.getNewsApi().setCountryEnum(!(countrySelector.getValue()
                .equals("none")) ? countrySelector.getValue() : null);
        NewsApi.getNewsApi().setCategoryEnum(!(categorySelector.getValue()
                .equals("none")) ? categorySelector.getValue() : null);
        NewsApi.getNewsApi().setSortByEnum(!(sortBySelector.getValue()
                .equals("none")) ? sortBySelector.getValue() : null);
        NewsApi.getNewsApi().setLanguageEnum(!(languageSelector.getValue()
                .equals("none")) ? languageSelector.getValue() : null);


        try {
            AppController.getAppController().requestArticles();
            showArticleScene();

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
        NewsApi.getNewsApi().setEndpointEnum(EndpointEnum.topHeadlines
                .getName());
        NewsApi.getNewsApi().setCountryEnum(CountryEnum.at.name());

        try {
            AppController.getAppController().requestArticles();
            Stream<Article> streamFromList = AppController.getAppController()
                    .getArticles().stream();

            Map<String, Long> map = streamFromList.collect(Collectors
                    .groupingBy(Article::getSourceName, Collectors.counting())
            );

            // befehl in entry variable speichern und damit arbeiten statt
            // in string und long
            String name = map.entrySet().stream().max(Comparator.comparing(
                    Map.Entry::getValue)).get().getKey();
            Long amount = map.entrySet().stream().max(Comparator.comparing(
                    Map.Entry::getValue)).get().getValue();

            //filter1.setText(name + " has " + amount + " article(s)");
            String new_text = name + " has " + amount + " article(s)";
            filter1.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ?
                    new_text : filter1Text);

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
        NewsApi.getNewsApi().setQuery("");
        NewsApi.getNewsApi().setEndpointEnum(EndpointEnum.topHeadlines
                .getName());
        NewsApi.getNewsApi().setCountryEnum(CountryEnum.at.name());

        try {
            AppController.getAppController().requestArticles();
            Stream<Article> streamFromList = AppController.getAppController()
                    .getArticles().stream();

            String new_text = streamFromList.map(Article::getAuthor).filter(
                    Objects::nonNull).max(Comparator.comparingInt(String::
                    length)).get();
            filter2.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ?
                    new_text : filter2Text);
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
        NewsApi.getNewsApi().setEndpointEnum(EndpointEnum.topHeadlines
                .getName());
        NewsApi.getNewsApi().setCountryEnum(CountryEnum.us.name());

        try {
            AppController.getAppController().requestArticles();
            Stream<Article> streamFromList = AppController.getAppController()
                    .getArticles().stream();

            String new_text = String.valueOf(streamFromList.map(Article::
                    getSourceName).filter(s -> s.equals("New York Times"))
                    .count());

            filter3.setText(MouseEvent.MOUSE_ENTERED == event.getEventType() ?
                    new_text : filter3Text);
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


        NewsApi.getNewsApi().setEndpointEnum(EndpointEnum.topHeadlines
                .getName());
        NewsApi.getNewsApi().setCountryEnum(CountryEnum.at.name());

        try {
            AppController.getAppController().requestArticles();

            List<Article> all_articles = AppController.getAppController()
                    .getArticles();
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

            AppController.getAppController().setArticles(res_articles);
            showArticleScene();

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
        //disableToggle();
        waitingGif.setVisible(true);

        NewsApi.getNewsApi().setQuery("corona");
        NewsApi.getNewsApi().setEndpointEnum(EndpointEnum.topHeadlines
                .getName());
        NewsApi.getNewsApi().setCountryEnum(CountryEnum.at.name());

        try {
            AppController.getAppController().requestArticles();
            showArticleScene();
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

        NewsApi.getNewsApi().setQuery("bitcoin");
        NewsApi.getNewsApi().setEndpointEnum(EndpointEnum.everything.name());

        try {
            AppController.getAppController().requestArticles();
            showArticleScene();
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

    private void showArticleScene() throws IOException,
            NewsApiException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("articleIrgendwas.fxml"));
        Scene articleScene = new Scene(fxmlLoader.load());
        ArticleIrgendwasController controller = fxmlLoader.getController();

        controller.chooseNews(AppController.getAppController().getArticles());

        articleScene.setFill(Color.TRANSPARENT);

        new Timeline(new KeyFrame(Duration.seconds(3), e -> App.stage
                .setScene(articleScene))).play();
    }

    @FXML
    private void getArticleCount(MouseEvent event) {
        ((Label) event.getSource()).setText(MouseEvent.MOUSE_ENTERED ==
                event.getEventType() ? AppController.getAppController()
                .getArticleCount() + "" : countArticlesText);
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
        ((Label) event.getSource()).setUnderline(MouseEvent.MOUSE_ENTERED ==
                event.getEventType());
    }

    @FXML
    void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene()
                .getWindow();
        stage.setX(event.getScreenX() - App.mouseX);
        stage.setY(event.getScreenY() - emptyPane.getHeight() / 2);
    }

    @FXML
    void handleMousePressed(MouseEvent event) {
        App.mouseX = event.getSceneX();
    }
}
