package at.ac.fhcampuswien;

import at.ac.fhcampuswien.normalClass.AppController;
import at.ac.fhcampuswien.normalClass.Article;
import at.ac.fhcampuswien.normalClass.Menu;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

    /**
     * ! Checking if correct modifier was used
     * ! Checking if name was spelled correctly
     */

    @Test
    public void testForTest() {
        try {
            Method handleInput = Menu.class.getDeclaredMethod("handleInput", String.class);
            assertEquals("private", Modifier.toString(handleInput.getModifiers()), "handleInput");
            assertEquals("void", handleInput.getReturnType().toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }


    @Test
    public void testSetArticles1() {
        // Checks for modifier and return type
        try {
            Method setArticles = AppController.class.getDeclaredMethod("setArticles", List.class);
            assertEquals("public", Modifier.toString(setArticles.getModifiers()), "setArticles");
            assertEquals("void", setArticles.getReturnType().toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testSetArticles2() {
        List<Article> articles = new ArrayList<>();
        AppController controller = new AppController();
        controller.setArticles(articles);
        assertEquals(articles.size(), controller.getArticleCount());
    }


    @Test
    public void testGetArticleCount1() {
        // Checks for modifier and return type
        try {
            Method getArticleCount = AppController.class.getDeclaredMethod("getArticleCount");
            assertEquals("public", Modifier.toString(getArticleCount.getModifiers()), "getArticleCount");
            assertEquals("int", getArticleCount.getReturnType().toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testGetArticleCount2() {
        // If not yet set, checking if return is equal to 0
        try {
            AppController controller = new AppController();
            assertEquals(0, controller.getArticleCount());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testGetArticleCount3() {
        // Setting
        try {
            List<Article> articles = new ArrayList<>();
            AppController controller = new AppController();

            controller.setArticles(articles);
            articles.add(new Article("Papa Putin", "Aide Ukraine"));
            articles.add(new Article("Jeff Bezos", "Nagelneuer Benzer"));
            articles.add(new Article("Mama", "Mach Wäsche"));

            assertEquals(articles.size(), controller.getArticleCount());
            assertEquals(3, controller.getArticleCount());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testGetTopHeadlinesAustria1() {
        // Checks for modifier and return type
        try {
            Method getArticleCount = AppController.class.getDeclaredMethod("getTopHeadlinesAustria");
            assertEquals("public", Modifier.toString(getArticleCount.getModifiers()), "getTopHeadlinesAustria");
            assertEquals("interface java.util.List", getArticleCount.getReturnType().toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testGetTopHeadlinesAustria2() {
        try {
            AppController controller = new AppController();
            assertTrue(controller.getTopHeadlinesAustria().size() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    // following test might be deleted/updated later

    @Test
    public void testGetTopHeadlinesAustria3() {
        // Setting
        try {
            List<Article> articles = new ArrayList<>();
            AppController controller = new AppController();

            articles.add(new Article("Papa Putin", "Aide Ukraine"));
            articles.add(new Article("Jeff Bezos", "Nagelneuer Benzer"));
            articles.add(new Article("Mama", "Mach Wäsche"));
            controller.setArticles(articles);

            assertEquals(3, articles.size());
            assertEquals(articles.size(), controller.getTopHeadlinesAustria().size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    // insert more tests for getTopHeadlinesAustria here

    @Test
    public void testGetAllNewsBitcoin1() {
        // Checks for modifier and return type
        try {
            Method getArticleCount = AppController.class.getDeclaredMethod("getAllNewsBitcoin");
            assertEquals("public", Modifier.toString(getArticleCount.getModifiers()), "getAllNewsBitcoin");
            assertEquals("interface java.util.List", getArticleCount.getReturnType().toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testGetAllNewsBitcoin2() {
        try {
            AppController controller = new AppController();
            List<Article> articles = new ArrayList<>();

            articles.add(new Article("abc", "BITCOIN"));
            articles.add(new Article("abc", "hallo bitcoin"));
            articles.add(new Article("abc", "BiTcOiN"));
            articles.add(new Article("abc", "gönnBitcoinHallo"));
            articles.add(new Article("abc", "hallo"));
            articles.add(new Article("abc", "nichtkrypto"));

            controller.setArticles(articles);
            assertEquals(controller.getAllNewsBitcoin().size(), 4);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testGetAllNewsBitcoin3() {
        try {
            AppController controller = new AppController();
            List<Article> articles = new ArrayList<>();

            articles.add(new Article("abc", "BTC"));

            controller.setArticles(articles);
            assertEquals(controller.getAllNewsBitcoin().size(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testFilterList1() {
        // Checks for modifier and return type
        try {
            Method getArticleCount = AppController.class.getDeclaredMethod("filterList", String.class, List.class);
            assertEquals("protected static", Modifier.toString(getArticleCount.getModifiers()), "filterList");
            assertEquals("interface java.util.List", getArticleCount.getReturnType().toString());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }

    @Test
    public void testFilterList2() {
        try {
            List<Article> articles = new ArrayList<>();

            articles.add(new Article("abc", "BItcoin"));
            articles.add(new Article("abc", "hallo bitcoin"));
            articles.add(new Article("abc", "BiTcOiN"));
            articles.add(new Article("abc", "gönnBitcoinHallo"));
            articles.add(new Article("abc", "hallo"));
            articles.add(new Article("abc", "nichtkrypto"));

            assertEquals(AppController.filterList("Co", articles).size(), 4);
            assertEquals(AppController.filterList("x", articles).size(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
    }
}





