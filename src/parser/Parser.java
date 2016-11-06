package parser;

import java.util.Scanner;

/**
 * Parser can ask the user for a text/String input
 * and then parses the input using Natural Language Processing
 * to fetch the semantic meaning
 */
public class Parser {

    private String text;

    public Parser() { }

    public void askInput() {
        Scanner user_input = new Scanner(System.in);

        System.out.println("Enter your sentence:");

        text = user_input.nextLine();

        System.out.println(text);
    }

}
