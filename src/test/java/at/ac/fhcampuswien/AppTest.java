package at.ac.fhcampuswien;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
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
}





