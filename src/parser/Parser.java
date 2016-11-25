package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Parser can ask the user for a text/String input
 * and then parses the input using Natural Language Processing
 * to fetch the semantic meaning
 */
public class Parser {

    private String text;
    private ArrayList<String> keywords = new ArrayList<>();

    // this is the map to hold the identified images to draw
    // from the input text and the positions
    private HashMap<String,ArrayList<Float>> images = new HashMap<>();

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

    /**
     * @return the HashMap images filled with shapes to draw
     *         and the coordinates they will hold
     *         For example,
     *         "triangle": [x1, y1, x2, y2, x3, y3]
     *         "circle": [a, b, c, d]
     */
    public HashMap<String,ArrayList<Float>> parse() {
        // TODO:
        // the parse function that does all the parsing stuff
        // put stuff into the images HashMap

        return images;
    }

}
