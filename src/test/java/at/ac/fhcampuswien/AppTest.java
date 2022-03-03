package at.ac.fhcampuswien;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
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
    public void name1() throws NoSuchFieldException {
        try {
            Field handleInput = Menu.class.getDeclaredField("handleInput");
            assertTrue(
                    Modifier.toString(handleInput.getModifiers()).equals("privat") &&
                            handleInput.getType().toString().equals(Menu.class.toString()),
                    "Please check your field names and modifiers for handleInput!");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Ups something went terribly wrong here...");
        }
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
