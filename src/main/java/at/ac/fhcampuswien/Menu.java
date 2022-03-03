package at.ac.fhcampuswien;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private AppController controller = new AppController();
    private final static String INVALID_INPUT_MESSAGE = "Invalid input!";
    private final static String EXIT_MESSAGE = "Bye bye!";

    public void start() {
        printMenu();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        handleInput(input);
    }

    private void handleInput(String input) {
        switch (input) {
            case ("a"):
                getTopHeadlinesAustria(controller);
                break;
            case ("b"):
                getAllNewsBitcoin(controller);
                break;
            case ("y"):
                getArticleCount(controller);
                break;
            case ("q"):
                printExitMessage();
                System.exit(0);
                break;
            default:
                Menu.printInvalidInputMessage();
                start();
        }
    }

    private void getArticleCount(AppController ctrl) {
        System.out.println("Number of articles: " + ctrl.getArticleCount());
    }

    private void getTopHeadlinesAustria(AppController ctrl) {
        List<Article> articles = ctrl.getTopHeadlinesAustria();
        if (articles.size() <= 0) {
            System.out.println("No article listed under this category");
        } else {
            for (Article article : ctrl.getTopHeadlinesAustria()) {
                System.out.println(article.toString());
            }
        }
    }

    private void getAllNewsBitcoin(AppController ctrl) {
        List<Article> articles = ctrl.getAllNewsBitcoin();
        if (articles.size() <= 0) {
            System.out.println("No article listed under this category");
        } else {
            for (Article article : ctrl.getAllNewsBitcoin()) {
                System.out.println(article.toString());
            }
        }
    }

    private static void printExitMessage() {
        System.out.println(EXIT_MESSAGE);
    }

    private static void printInvalidInputMessage() {
        System.out.println(INVALID_INPUT_MESSAGE);
    }

    private static void printMenu() {
        System.out.print
                (
                        "****************************" + System.lineSeparator() +
                                "*    Welcome to NewsApp    *" + System.lineSeparator() +
                                "****************************" + System.lineSeparator() +
                                "Enter what you wanna do:" + System.lineSeparator() +
                                "a: Get top headlines austria" + System.lineSeparator() +
                                "b: Get all news about bitcoin" + System.lineSeparator() +
                                "y: Count articles" + System.lineSeparator() +
                                "q: Quit program" + System.lineSeparator()

                );
    }
}


