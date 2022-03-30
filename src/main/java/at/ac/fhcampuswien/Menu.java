package at.ac.fhcampuswien;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private AppController controller;
    private final static String INVALID_INPUT_MESSAGE = "Invalid input!";
    private final static String EXIT_MESSAGE = "Bye bye!";
    private Boolean bo = true;

    public void start(){
        Scanner scanner = new Scanner(System.in);
        controller = new AppController();
        while (bo) {
            printMenu();
            String input = scanner.next();
            handleInput(input);
        }
    }

    private void handleInput(String input) {
        switch (input) {
            case ("a") -> getTopHeadlinesAustria(controller);
            case ("b") -> getAllNewsBitcoin(controller);
            case ("y") -> getArticleCount(controller);
            case ("q") -> {
                bo = false;
                printExitMessage();
                System.exit(0);
            }
            default -> Menu.printInvalidInputMessage();
        }
    }

    private void getArticleCount(AppController ctrl) {
        System.out.println("Number of articles: " + ctrl.getArticleCount());
    }

    private void getTopHeadlinesAustria(AppController ctrl){
        List<Article> articles = ctrl.getTopHeadlinesAustria();
        if (articles.size() == 0) {
            System.out.println("No article listed under this category");
        } else {
            System.out.print("[");
            for (Article a : articles) {
                System.out.print(a.toString());
                if (articles.indexOf(a) + 1 != articles.size()) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    private void getAllNewsBitcoin(AppController ctrl) {
        List<Article> articles = ctrl.getAllNewsBitcoin();
        if (articles.size() == 0) {
            System.out.println("No article listed under this category");
        } else {
            System.out.print("[");
            for (Article a : articles) {
                System.out.print(a.toString());
                if (articles.indexOf(a) + 1 != articles.size()) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
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

    /*
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
     */
}


