package at.ac.fhcampuswien;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

    @BeforeAll
    public static void init() {
        System.out.println("Testing Exercise 1");
    }

    @AfterAll
    public static void finish() {
        System.out.println("Finished Testing Exercise 1");
    }

    @Test
    public void testSetArticles1() {
        List<Article> articles = new ArrayList<>();
        AppController controller = new AppController();
        controller.setArticles(articles);

        assertEquals(articles.size(), controller.getArticleCount());
    }

    @Test
    public void testSetArticles2() {
    }
}
