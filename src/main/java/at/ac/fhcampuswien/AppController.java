package at.ac.fhcampuswien;

import java.util.*;

public class AppController {
    private List<Article> articles;

    public AppController () {
        //articles = new ArrayList<>();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getArticleCount() {
        return articles == null ? 0 : articles.size();
    }

    public List<Article> getTopHeadlinesAustria() {
        return articles == null ? new ArrayList<>() : articles;
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

    public List<Article> getAllNewsBitcoin() {
        return filterList("bitcoin", new ArrayList<>());
    }
    
    private static List<Article> generateMockList() {
        return null;
    }
}
