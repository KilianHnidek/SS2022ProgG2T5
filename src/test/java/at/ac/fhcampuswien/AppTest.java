package at.ac.fhcampuswien;

import jdk.jfr.Description;
import org.junit.jupiter.api.*;

import javax.annotation.processing.SupportedAnnotationTypes;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AppTest {

    // source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /*
    DisplayName - Better because it allows naming with spacing
    Nested - Groups via class
    BeforeAll/after - Message before/ and after the testing is done
     */


    @DisplayName("Grouped tests for Exercise 1")
    @Nested
            // Nested enables to group via class
    class Exercise1 {

        @BeforeAll
        public static void init() {
            System.out.println("Started Testing " + ANSI_RED + "Exercise 1" + ANSI_RESET);
        }
        // Message before testing begins

        @AfterAll
        public static void finish() {
            System.out.println("Finished Testing " + ANSI_RED + "Exercise 1" + ANSI_RESET);
        }
        // Message after testing begins

        /**
         * ! Checking if correct modifier was used
         * ! Checking if name was spelled correctly
         */


        // DisplayNames are better because spaces are allowed
        // in method names they are not
        @DisplayName("Grouped tests for Method setArticle")
        @Nested
                // Nested enables to group via class
        class SetArticle {

            @BeforeAll
            public static void init() {
                System.out.println("Testing Exercise 1 - Part(" + ANSI_GREEN + "1" + ANSI_RESET + "/5)");
            }
            // Message before testing begins

            @AfterAll
            public static void finish() {
                System.out.println("Finished Testing Exercise 1 - Part(" + ANSI_GREEN + "1" + ANSI_RESET + "/5)");
            }
            // Message after testing begins


            @Test
            @DisplayName("testSetArticles1 // Modifier && Return type")
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
            @DisplayName("testSetArticles2 // Set Articles")
            public void testSetArticles2() {
                List<Article> articles = new ArrayList<>();
                AppController controller = new AppController();
                controller.setArticles(articles);
                assertEquals(articles.size(), controller.getArticleCount());
            }
        }


        @DisplayName("Grouped tests for Method getArticleCount")
        @Nested
                // Nested enables to group via class
        class ArticleCount {

            @BeforeAll
            public static void init() {
                System.out.println("Testing Exercise 1 - Part(" + ANSI_GREEN + "2" + ANSI_RESET + "/5)");
            }
            // Message before testing begins

            @AfterAll
            public static void finish() {
                System.out.println("Finished Testing Exercise 1 - Part(" + ANSI_GREEN + "2" + ANSI_RESET + "/5)");
            }
            // Message after testing begins

            @Test
            @DisplayName("testGetArticleCount1 // Modifier && Return type")
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
            @DisplayName("testGetArticleCount2 // Article Count size")
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
            @DisplayName("testGetArticleCount3 // Article Count size")
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

        @DisplayName("Grouped tests for Method getTopHeadlinesAustria")
        @Nested
                // Nested enables to group via class
        class TopHeadLines {

            @BeforeAll
            public static void init() {
                System.out.println("Testing Exercise 1 - Part(" + ANSI_GREEN + "3" + ANSI_RESET + "/5)");
            }
            // Message before testing begins

            @AfterAll
            public static void finish() {
                System.out.println("Finished Testing Exercise 1 - Part(" + ANSI_GREEN + "3" + ANSI_RESET + "/5)");
            }
            // Message after testing begins

            @Test
            @DisplayName("testGetTopHeadlinesAustria1 // Modifier && Return type")
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
            @DisplayName("testGetTopHeadlinesAustria2 // Austria TOP Article size")
            public void testGetTopHeadlinesAustria2() {
                try {
                    AppController controller = new AppController();
                    assertEquals(0, controller.getTopHeadlinesAustria().size());
                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Ups something went terribly wrong here...");
                }
            }

            // following test might be deleted/updated later

            @Test
            @DisplayName("testGetTopHeadlinesAustria3 // Austria TOP Article size")
            public void testGetTopHeadlinesAustria3() {
                // Setting
                try {
                    List<Article> articles = new ArrayList<>();
                    AppController controller = new AppController();

                    articles.add(new Article("Mama", "Essen!"));
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

        }

        @DisplayName("Grouped tests for Method getAllNewsBitcoin")
        @Nested
                // Nested enables to group via class
        class NewsBitcoin {

            @BeforeAll
            public static void init() {
                System.out.println("Testing Exercise 1 - Part(" + ANSI_GREEN + "4" + ANSI_RESET + "/5)");
            }
            // Message before testing begins

            @AfterAll
            public static void finish() {
                System.out.println("Finished Testing Exercise 1 - Part(" + ANSI_GREEN + "4" + ANSI_RESET + "/5)");
            }
            // Message after testing begins

            @Test
            @DisplayName("testGetAllNewsBitcoin1 // Modifier && Return type")
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
            @DisplayName("testGetAllNewsBitcoin2 // Bitcoin Article size")
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
            @DisplayName("testGetAllNewsBitcoin3 // Bitcoin Article size")
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
        }


        @DisplayName("Grouped tests for Method filterList")
        @Nested
                // Nested enables to group via class
        class FilterList {

            @BeforeAll
            public static void init() {
                System.out.println("Testing Exercise 1 - Part(" + ANSI_GREEN + "5" + ANSI_RESET + "/5)");
            }
            // Message before testing begins

            @AfterAll
            public static void finish() {
                System.out.println("Finished Testing Exercise 1 - Part(" + ANSI_GREEN + "5" + ANSI_RESET + "/5)");
            }
            // Message after testing begins


            @Test
            @DisplayName("testFilterList1 // Modifier && Return type")
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
            @DisplayName("testFilterList2 // Mock Liste nach Co gesucht")
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

                } catch (Exception e) { // if function in progress fails
                    e.printStackTrace();
                    fail("Ups something went terribly wrong here...");
                }
            }

            @Test
            @DisplayName("testFilterList3 // Mock Liste nach x gesucht")
            public void testFilterList3() {
                try {
                    List<Article> articles = new ArrayList<>();

                    articles.add(new Article("abc", "BItcoin"));
                    articles.add(new Article("abc", "hallo bitcoin"));
                    articles.add(new Article("abc", "BiTcOiN"));
                    articles.add(new Article("abc", "gönnBitcoinHallo"));
                    articles.add(new Article("abc", "hallo"));
                    articles.add(new Article("abc", "nichtkrypto"));

                    assertEquals(AppController.filterList("x", articles).size(), 0);

                } catch (Exception e) {
                    e.printStackTrace();
                    fail("Ups something went terribly wrong here...");
                }
            }
        }

    }
}





