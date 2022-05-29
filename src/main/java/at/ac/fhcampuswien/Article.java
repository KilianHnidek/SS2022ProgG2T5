package at.ac.fhcampuswien;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Article {
    class Source {
        private String id;
        private String name;

        public Source(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Source source;
    private String author, title, description, url, urlToImage, publishedAt, content;

    public Article(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceName() {
        return source.name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return /*getClass().getSimpleName()+*/ "Title:" + title + System.lineSeparator() + "Author:" + author + "";
    }

    public void downloadArticle() throws IOException {
        InputStream in = new URL(url).openStream();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(App.stage);

        if (file != null) {
            if (file.exists()) {
                file.delete();
            }

            Files.copy(in, Paths.get(file.getAbsolutePath()));
        }
    }
}
