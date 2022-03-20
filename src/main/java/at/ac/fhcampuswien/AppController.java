package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enums.CategoryEnum;
import at.ac.fhcampuswien.enums.CountryEnum;
import at.ac.fhcampuswien.enums.EndpointEnum;

import java.io.IOException;
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
        return articles != null ? articles.size() : 0;
    }

    public List<Article> getTopHeadlinesAustria() throws IOException {
        NewsApi.endpointEnum = EndpointEnum.topHeadlines;
        NewsApi.categoryEnum = CategoryEnum.business;
        NewsApi.countryEnum = CountryEnum.at;

        NewsResponse newsResponse = NewsApi.run();

        return newsResponse.getArticles() != null ? newsResponse.getArticles() : new ArrayList<>();
        //return articles != null ? articles : new ArrayList<>();
    }

    public List<Article> getAllNewsBitcoin() throws IOException {
        NewsApi.endpointEnum = EndpointEnum.everything;
        NewsApi.categoryEnum = CategoryEnum.general;
        NewsApi.countryEnum = null;

        NewsResponse newsResponse = NewsApi.run();

        return newsResponse.getArticles() != null ? filterList("bitcoin", newsResponse.getArticles()) : new ArrayList<>();
        //return newsResponse.getArticles() != null ? newsResponse.getArticles() : new ArrayList<>();
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
     * ! BIS HIER
     */

    private static List<Article> generateMockList() {

        List<Article> articles = new ArrayList<>();

        articles.add(new Article("abc", "BItcOin"));
        articles.add(new Article("abc", "hallo bitcoin"));
        articles.add(new Article("abc", "BiTcOiN"));
        articles.add(new Article("abc", "gibMalBitcoinHallo"));
        articles.add(new Article("abc", "hallo"));
        articles.add(new Article("abc", "nichtCrypto"));

        articles.add(new Article("George Orwell", "21984"));
        articles.add(new Article("hallo", "2BItcOin"));
        articles.add(new Article("abc", "2hal2lo bitcoin"));
        articles.add(new Article("abc", "2BiTc2OiN"));
        articles.add(new Article("abc", "2gibMa2lBitcoinHallo"));
        articles.add(new Article("abc", "2hallo2"));

        articles.add(new Article("abc", "3BItcOin"));
        articles.add(new Article("abc", "3hallo bitcoin"));
        articles.add(new Article("George Orwell", "31984"));

        return articles;
    }
}
