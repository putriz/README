package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parser can ask the user for a text/String input
 * and then parses the input using Natural Language Processing
 * to fetch the semantic meaning
 */
public class Parser {

    private String text;
    private ArrayList<String> keywords = new ArrayList<>();

    public Parser() {
        initKeywords();
    }

    /**
     * Reads in the resource file keywords.txt into the
     * list keywords in order for the program to recognize
     * certain keywords in the user input
     */
    private void initKeywords() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/parser/resources/keywords.txt"));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                keywords.add(line);
            }

        } catch (Exception e) {
            System.out.println("Error in parsing " + e);
        }

    }

    public void askInput() {
        Scanner user_input = new Scanner(System.in);

        System.out.println("Enter your sentence:");

        text = user_input.nextLine();

        System.out.println(text);
    }

}
