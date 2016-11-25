package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.*;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import static processing.javafx.PSurfaceFX.PApplicationFX.surface;

/**
 * Parser can ask the user for a text/String input
 * and then parses the input using Natural Language Processing
 * to fetch the semantic meaning
 */
public class Parser {

    private String text;
    private ArrayList<String> keywords = new ArrayList<>();

    private int width, height;

    // this is the map to hold the identified images to draw
    // from the input text and the positions
    private HashMap<String,ArrayList<Float>> images = new HashMap<>();

    // Default constructor
    public Parser() { this(400,400); }

    public Parser(int width, int height) {
        this.width = width;
        this.height = height;
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
     *         "circle": [x, y, weight, height]
     */
    public HashMap<String,ArrayList<Float>> parse() {
        // TODO:
        // Make coordinates depend on text input as well
        // Actually use tags

        // Tokenize words of sentences
        MaxentTagger tagger = new MaxentTagger("src/parser/resources/english-left3words-distsim.tagger");
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(text));

        // Go through each sentence, tagging the words and putting them into ["word","tag"] pairs
        // Puts keywords found into images HashMap with coordinates
        for (List<HasWord> sentence : sentences) {
            List<TaggedWord> tSentence = tagger.tagSentence(sentence);
            for (TaggedWord word : tSentence) {
                String[] word_pair = word.toString().split("/");
                String key = word_pair[0].toLowerCase();
                String tag = word_pair[1];
                if (keywords.contains(key)) {
                    images.put(key,getCoordinates(key));
                }
            }
        }
        return images;
    }

    // Determines coordinates of input
    private ArrayList<Float> getCoordinates(String word) {
        // TODO:
        // Have coordinates depend on input

        ArrayList<Float> coordinates;
        switch (word) {
            case "triangle": coordinates = new ArrayList<>(Arrays.asList(new Float(width/2),
                    new Float(height/4), new Float(width/4), new Float((3*height)/4),
                    new Float((3*width)/4), new Float((3*height)/4)));
                            break;
            case "circle": coordinates = new ArrayList<Float>(Arrays.asList(new Float(width/2),new Float((2*height)/4),
                    new Float(width/2),new Float(height/2)));
                            break;
            default: coordinates = new ArrayList<>();
                            break;
        }
        return coordinates;
    }

}
