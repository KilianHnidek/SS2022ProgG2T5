package at.ac.fhcampuswien;

import java.util.*;

public class AppController {
    private List<Article> articles;

    public AppController() {
        articles = generateMockList();
    }

    /**
     * ! VON HIER
     */

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getArticleCount() {
        return articles == null ? 0 : articles.size();
    }

    public List<Article> getTopHeadlinesAustria() {
        return articles == null ? new ArrayList<>() : articles;
    }

    public List<Article> getAllNewsBitcoin() {
        return filterList("bitcoin", articles);
    }

    protected static List<Article> filterList(String query, List<Article> articles) {
        List<Article> res_articles = new ArrayList<>();

        for (Article a : articles) {
            if (a.getTitle().toLowerCase().contains(query.toLowerCase())) {
                res_articles.add(a);
            }
        }
        return res_articles;
    }

    /**
     * ! HIER
     */

    private static List<Article> generateMockList() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("abc", "BItcOin"));
        articles.add(new Article("abc", "hallo bitcoin"));
        articles.add(new Article("abc", "BiTcOiN"));
        articles.add(new Article("abc", "gibMalBitcoinHallo"));
        articles.add(new Article("abc", "hallo"));
        articles.add(new Article("abc", "nichtCrypto"));
        return articles;
    }
}
