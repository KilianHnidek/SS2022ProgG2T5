package at.ac.fhcampuswien.normalClass;

public class Article {

    private String author, title, description, url, urlToImage, publishedAt, content;

    public Article(String author, String title) {
        this.setAuthor(author);
        this.setTitle(title);
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
