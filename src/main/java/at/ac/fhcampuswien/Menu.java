package at.ac.fhcampuswien;

import java.util.Scanner;

public class Menu {

    private AppController controller;
    private final static String INVALID_INPUT_MESSAGE = "Invalid input!";
    private final static String EXIT_MESSAGE = "Bye bye!";

    public void start() {
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
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        handleInput(input);

    }

    private void handleInput(String input) {
        switch (input) {
            case ("a"):
                controller.getTopHeadlinesAustria();
                break;
            case ("b"):
                controller.getAllNewsBitcoin();
                break;
            case ("y"):
                controller.getArticleCount();
                break;
            case ("q"): System.exit(0);
                break;
            default:
                Menu.printInvalidInputMessage();
        }
    }

    private void getArticleCount(AppController ctrl) {
        System.out.println("Number of articles: " + ctrl.getArticleCount());
    }

    private void getTopHeadlinesAustria(AppController ctrl) {
        for (Article article : ctrl.getTopHeadlinesAustria()) {
            System.out.println(article.getTitle());
        }
    }

    private void getAllNewsBitcoin(AppController ctrl) {

    }

    private static void printExitMessage() {
        System.out.println(EXIT_MESSAGE);
    }

    private static void printInvalidInputMessage() {
        System.out.println(INVALID_INPUT_MESSAGE);
    }

    private static void printMenu() {
    }
}
