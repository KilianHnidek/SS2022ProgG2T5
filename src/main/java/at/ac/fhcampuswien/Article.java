package at.ac.fhcampuswien;

public class Article {

    private String author, title, description, url, urlToImage, publishedAt, content;

    public Article(String author, String title) {
        this.setAuthor(author);
        this.setTitle(title);
    }

    public Article(String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return /*getClass().getSimpleName()+*/ "Title:" + title + System.lineSeparator() + "Author:" + author + "";
    }
}
