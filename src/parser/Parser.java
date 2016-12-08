package parser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.*;
import java.util.List;
import java.util.Collections;
import java.util.Map;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.ArrayCoreMap;
import javafx.util.Pair;

import static processing.javafx.PSurfaceFX.PApplicationFX.surface;

/**
 * Parser can ask the user for a text/String input
 * and then parses the input using Natural Language Processing
 * to fetch the semantic meaning
 */
public class Parser {

    private String text;
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> sizewords = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<String> descriptive_verbs = new ArrayList<>();
    private ArrayList<String> position_words=new ArrayList<>();

    private int width, height;

    // this is the map to hold the identified images to draw
    // from the input text and the positions
    private ArrayList<Pair<String,HashMap<String,ArrayList<Float>>>> images = new ArrayList<>();

    // Default constructor
    public Parser() { this(400,400); }

    public Parser(int width, int height) {
        this.width = width;
        this.height = height;
        loadLexicon();
    }

    /**
     * Reads in the resource files into
     * the respective lists in order
     * for the program to recognize
     * certain words in the user input
     */
    private void loadLexicon() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/parser/resources/keywords.txt"));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                keywords.add(line);
            }

            bufferedReader = new BufferedReader(new FileReader("src/parser/resources/sizewords.txt"));

            while ((line = bufferedReader.readLine()) != null) {
                sizewords.add(line);
            }

            bufferedReader = new BufferedReader(new FileReader("src/parser/resources/colors.txt"));

            while ((line = bufferedReader.readLine()) != null) {
                colors.add(line);
            }

            bufferedReader = new BufferedReader(new FileReader("src/parser/resources/descriptive_verbs.txt"));

            while ((line = bufferedReader.readLine()) != null) {
                descriptive_verbs.add(line);
            }

            bufferedReader=new BufferedReader(new FileReader( "src/parser/resources/position_words.txt"));

            while ((line=bufferedReader.readLine()) !=null) {
                position_words.add(line);
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
    public ArrayList<Pair<String,HashMap<String,ArrayList<Float>>>> parse() {
        // TODO:
        // Make coordinates depend on text input as well
        // Actually use tags

        // Tokenize words of sentences
        MaxentTagger tagger = new MaxentTagger("src/parser/resources/english-left3words-distsim.tagger");
        List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(text));

        String prev_noun = "";
        String prev_tag = "";
        ArrayList<String> adj_chain = new ArrayList<>();
        ArrayList<String> position=new ArrayList<>();

        boolean isNoun = true;
        boolean prev_color = true;

        // Activated when descriptive verb found
        boolean listening = false;

        int consecutive_adjs = 0;

        String pos_word="";
        int order_num=0;



        // Go through each sentence, tagging the words and putting them into ["word","tag"] pairs
        // Puts keywords found into images HashMap with coordinates
        for (List<HasWord> sentence : sentences) {
            List<TaggedWord> tSentence = tagger.tagSentence(sentence);
            for (TaggedWord taggedWord: tSentence){
                String[] word_pair = taggedWord.toString().split("/");
                String word = word_pair[0].toLowerCase();
                String tag = word_pair[1];
                if(position_words.contains(word)) {
                    pos_word=word;
                    //break;
                }
            }

            for (TaggedWord taggedWord : tSentence) {
                String[] word_pair = taggedWord.toString().split("/");
                String word = word_pair[0].toLowerCase();
                String tag = word_pair[1];


                if (descriptive_verbs.contains(word)) {
                    listening = true;
                }

                // Keeps track of the consecutive adjectives
                if (tag.equals("JJ") || colors.contains(word)) {
                    if (prev_tag.equals("JJ")) {
                        consecutive_adjs += 1;

                    } else {
                        consecutive_adjs = 1;
                    }
                    adj_chain.add(word);
                } else if (getIsNoun(tag)) {
                    isNoun = true;
                    if (prev_noun.equals("")) {
                        prev_noun = word;
                    }
                }

                // If a keyword is found
                if (keywords.contains(word) || colors.contains(word) || tag.equals("JJ")) {
                    HashMap<String,ArrayList<Float>> optionsMap = new HashMap<>();
                    //optionsMap.put("coordinates",getCoordinates(word));
                    if (keywords.contains(word)) {
                        if (pos_word == "") {
                            optionsMap.put("coordinates", getCoordinates(word));
                        } else if (order_num == 0) {
                            optionsMap.put("coordinates", getCoordinatesfirst(word, pos_word));
                            order_num = 1;
                        } else if (order_num == 1) {
                            optionsMap.put("coordinates", getCoordinatessecond(word, pos_word));
                            order_num = 1;
                        }
                    }
                    else{
                        optionsMap.put("coordinates",getCoordinates(word));
                    }



                    // Aka "If it is a noun, it has found a descriptive verb and is looking for
                    // for more adjectives, and the last word was an adjective
                    if (getIsNoun(tag) && listening && (prev_tag.equals("JJ") || prev_color)) { // Handles "The dog was black and orange cat..."

                        // Find the adjectives that belong to the previous noun and the ones
                        // that belong to the current noun
                        // The ones belong to the current noun will be the ones that are right in
                        // front of this noun (otherwise, they are assumed to belong to the other one)
                        List<String> prevNounAdjs = adj_chain.subList(0,adj_chain.size()-consecutive_adjs);
                        List<String> currNounAdjs = adj_chain.subList(adj_chain.size()-consecutive_adjs,adj_chain.size());

                        // Extract the last pair from images and add the adjectives for the previous noun
                        // to its hashmap
                        Pair<String,HashMap<String,ArrayList<Float>>> prev_pair = images.get(images.size()-1);
                        HashMap<String,ArrayList<Float>> prevMap = prev_pair.getValue();

                        // Classify adjectives by type (size,color,etc.)
                        ArrayList<String> adj_groups = classifyAdjectives(prevNounAdjs);
                        int index = 0;
                        for (String adj_group : adj_groups) {
                            if (adj_group.equals("")) {
                                index += 1;
                                continue;
                            }
                            prevMap.put(adj_group,getAdjOptions(adj_group,prevNounAdjs.get(index)));
                            index += 1;
                        }

                        // Avoiding possible error? Something weird happened with non-nouns
                        if (getIsNoun(tag)) {

                            // Add the modified pair back to images
                            Pair<String,HashMap<String,ArrayList<Float>>> new_prev_pair = new Pair<>(prev_pair.getKey(),prevMap);
                            images.remove(images.size()-1);
                            images.add(new_prev_pair);
                        }

                        // Add the rest of the adjectives to the current noun
                        adj_groups = classifyAdjectives(currNounAdjs);
                        index = 0;
                        for (String adj_group : adj_groups) {
                            if (adj_group.equals("")) {
                                index += 1;
                                continue;
                            }

                            if (getIsNoun(tag)) {
                                optionsMap.put(adj_group, getAdjOptions(adj_group, currNounAdjs.get(index)));
                                index += 1;
                            }
                        }

                        // Clear the chain for the next adjectives
                        adj_chain.clear();

                    } else if (((prev_tag.equals("JJ") || prev_color) && getIsNoun(tag))) { // "The black dog"
                        // For when the program did not encounter a descriptive verb (not "listening" for others)

                        ArrayList<String> adj_groups = classifyAdjectives(adj_chain);
                        int index = 0;
                        for (String adj_group : adj_groups) {

                            // If no type was found, skip it
                            if (adj_group.equals("")) {
                                index += 1;
                                continue;
                            }
                            if (getIsNoun(tag)) {
                                optionsMap.put(adj_group, getAdjOptions(adj_group, adj_chain.get(index)));
                                index += 1;
                            }

                        }
                        adj_chain.clear();
                    } else if ((tag.equals("JJ") || colors.contains(word)) && listening && !prev_noun.equals("")) {
                        // If the current word is an adjective, and a descriptive verb was found. This
                        // case is for when the adjective describing the subject is at the end of the sentence
                        // e.g., "The circle is black"

                        Pair<String,HashMap<String,ArrayList<Float>>> prev_pair = images.get(images.size()-1);
                        HashMap<String,ArrayList<Float>> prevMap = prev_pair.getValue();

                        ArrayList<String> adj_groups = classifyAdjectives(adj_chain);

                        int index = 0;
                        for (String adj_group : adj_groups) {
                            if (adj_group.equals("")) {
                                index += 1;
                                continue;
                            }
                            if (getIsNoun(tag)) {
                                prevMap.put(adj_group,getAdjOptions(adj_group,adj_chain.get(index)));
                                index += 1;
                            }

                        }
                        if (getIsNoun(tag)) {
                            Pair<String,HashMap<String,ArrayList<Float>>> new_prev_pair = new Pair<>(prev_pair.getKey(),prevMap);
                            images.remove(images.size()-1);
                            images.add(new_prev_pair);
                        }

                    }
                    if (getIsNoun(tag)) {
                        Pair<String,HashMap<String,ArrayList<Float>>> object = new Pair<>(word,optionsMap);
                        images.add(object);
                    }

                } else if (isNoun) { //If word isn't a keyword but still has adjectives describing it
                    adj_chain.clear();
                }

                // Code for whether to stop "listening" for adjectives
                if (listening) {
                    if (!tag.equals("JJ") && !colors.contains(word) && !tag.equals("CC") && !descriptive_verbs.contains(word)
                            && !tag.equals("DT")) {
                        listening = false;
                    }
                }

                // Handles flags for looking back in the sentence
                prev_tag = tag;
                if (getIsNoun(tag)) {
                    isNoun = false;
                    prev_noun = word;
                }

                if (!tag.equals("JJ")) {
                    consecutive_adjs = 0;
                }

                prev_color = colors.contains(word);
            }
        }
        if (pos_word.equals("in") || pos_word.equals("on")) {
            Collections.reverse(images);
        }
        return images;
    }

    /****** FUNCTIONS FOR PROVIDING OPTIONS ******/

    // Determines coordinates of input
    private ArrayList<Float> getCoordinates(String word) {
        // TODO:
        // Have coordinates depend on input

        ArrayList<Float> coordinates;
        switch (word) {
            case "triangle": coordinates = new ArrayList<>(Arrays.asList((float) width/2,
                    (float) height/4, (float) width/4,(float) (3*height)/4,
                    (float) (3*width)/4, (float) (3*height)/4));
                break;
            case "circle": coordinates = new ArrayList<>(Arrays.asList((float) width/2,(float) (2*height)/4,
                    (float) width/2, (float) height/2));
                break;
            case "arc": coordinates = new ArrayList<>(Arrays.asList((float) width/2,(float) (2*height)/4,
                    (float) width/2,(float) height/2));
                break;
            case "rectangle": coordinates = new ArrayList<>(Arrays.asList((float) width/4, (float) height/4,
                    (float) width/2, (float) height/3));
                break;
            case "square": coordinates = new ArrayList<>(Arrays.asList((float) width/4, (float) height/4,
                    (float) height/2, (float) height/2));
                break;
            case "house":
                ArrayList<Float> triangle = getCoordinatesfirst("triangle","top");
                ArrayList<Float> square = getCoordinatessecond("square","below");
                coordinates = new ArrayList<>();
                coordinates.addAll(triangle);
                coordinates.addAll(square);
                break;
            default: coordinates = new ArrayList<>();
                break;
        }
        return coordinates;
    }
    private ArrayList<Float> getCoordinatesfirst(String word, String position){

        ArrayList<Float> coordinates=new ArrayList<>();
        if (position.equals("above") || position.equals("top")){
            if (word.equals("triangle")) {
                coordinates = new ArrayList<>(Arrays.asList(new Float(width / 2),
                        new Float(0), new Float(width / 4), new Float(height / 2), new Float((width / 4) * 3), new Float(height / 2)));
            }
            else if (word.equals("circle")) {
                coordinates= new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("arc")) {
                coordinates= new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("square")) {
                coordinates = new ArrayList<>(Arrays.asList((float) width/4, (float) 0, (float) width/2, (float) width/2));
            }
        }
        else if (position.equals("below") || position.equals("underneath")){
            if (word.equals("circle")) {
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4*3), new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("triangle")) {
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/2),new Float(width/4),new Float(height),new Float(3*(width/4)),new Float(height)));
            }
            else if (word.equals("arc")) {
                coordinates= new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4*3), new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("square")) {
                coordinates = new ArrayList<>(Arrays.asList((float) width/4, (float) height/2, (float) width/2, (float) width/2));
            }
        }
        else if (position.equals("right")){
            if (word.equals("triangle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height),new Float((width/4)*3),new Float(0), new Float(width),new Float(height)));
            }
            else if (word.equals("circle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)*3),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("arc")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)*3),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
        }
        else if (position.equals("left")) {
            if (word.equals("triangle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float(0),new Float(height),new Float(width/4),new Float(0),new Float(width/2),new Float(height)));
            }
            else if (word.equals("circle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("arc")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
        }

        else{
            coordinates=new ArrayList<>();
        }

        if (coordinates.isEmpty()) {
            return getCoordinates(word);
        }
        return coordinates;
    }
    private ArrayList<Float> getCoordinatessecond(String word, String position){
        ArrayList<Float> coordinates=new ArrayList<>();
        if (position.equals("above") || position.equals("top")){
            if (word.equals("circle")) {
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4*3), new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("triangle")) {
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/2),new Float(width/4),new Float(height),new Float(3*(width/4)),new Float(height)));
            }
            else if (word.equals("arc")) {
                coordinates = new ArrayList<>(Arrays.asList(new Float(width / 2), new Float(height / 4 * 3), new Float(width / 2), new Float(height / 2)));
            }
        }
        else if (position.equals("below") || position.equals("underneath")){
            if (word.equals("triangle")) {
                coordinates = new ArrayList<>(Arrays.asList(new Float(width / 2),
                        new Float(0), new Float(width / 4), new Float(height / 2), new Float((width / 4) * 3), new Float(height / 2)));
            }
            else if (word.equals("circle")) {
                coordinates= new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("arc")) {
                coordinates= new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/4),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("square")) {
                coordinates = new ArrayList<>(Arrays.asList((float) width/4, (float) height/2, (float) width/2, (float) width/2));
            }
        }
        else if (position.equals("right")){
            if (word.equals("triangle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float(0),new Float(height),new Float(width/4),new Float(0),new Float(width/2),new Float(height)));
            }
            else if (word.equals("circle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("arc")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
        }
        else if (position.equals("left")) {
            if (word.equals("triangle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height),new Float((width/4)*3),new Float(0), new Float(width),new Float(height)));
            }
            else if (word.equals("circle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)*3),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
            else if (word.equals("arc")){
                coordinates=new ArrayList<>(Arrays.asList(new Float((width/4)*3),new Float(height/2),new Float(width/2),new Float(height/2)));
            }
        }
        else if (position.equals("in") || position.equals("on")) {
            if (word.equals("circle")){
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/2),new Float(width),new Float(height)));
            }
            if (word.equals("triangle")) {
                coordinates=new ArrayList<>(Arrays.asList(new Float(0),new Float(height),new Float(width/2),new Float(0),new Float(width),new Float(height)));
            }
            if (word.equals("arc")){
                coordinates=new ArrayList<>(Arrays.asList(new Float(width/2),new Float(height/2),new Float(width),new Float(height)));
            }
        }

        if (coordinates.isEmpty()) {
            return getCoordinates(word);
        }
        return coordinates;
    }

    // Determines the color inputted and returns its float rgb values
    // (they get converted back to ints in the classes)
    private ArrayList<Float> getColor(String word) {

        Color color;
        ArrayList<Float> colorComponents = new ArrayList<>();

        // Determine the color specified (Default is yellow)
        switch(word) {
            case "black": color = Color.BLACK; break;
            case "blue": color = Color.BLUE; break;
            case "cyan": color = Color.CYAN; break;
            case "gray": color = Color.GRAY; break;
            case "green": color = Color.GREEN; break;
            case "magenta": color = Color.MAGENTA; break;
            case "orange": color = Color.ORANGE; break;
            case "pink": color = Color.PINK; break;
            case "red": color = Color.RED; break;
            case "yellow": color = Color.YELLOW; break;
            default: color = Color.WHITE; break;
        }

        // Put the rgb values to array
        colorComponents.add(new Float(color.getRed()));
        colorComponents.add(new Float(color.getGreen()));
        colorComponents.add(new Float(color.getBlue()));

        return colorComponents;
    }

    // TODO: Code this part
    // Will return float values to represent the size
    // of the shapes
    private ArrayList<Float> getSize(String word) {
        return new ArrayList<>();
    }

    /****** MANAGER FUNCTIONS ******/

    // Manages what functions are called depending on the given group
    private ArrayList<Float> getAdjOptions(String group, String word) {
        ArrayList<Float> options = new ArrayList<>();
        switch(group) {
            case "color": options = getColor(word); break;
            case "size": options = getSize(word); break;
            default: break;
        }

        return options;
    }

    // Determines which groups the given adjectives are in
    private ArrayList<String> classifyAdjectives(List<String> adjectives) {
        ArrayList<String> groups = new ArrayList<>();
        for (String adjective : adjectives) {
            if (sizewords.contains(adjective)) {
                groups.add("size");
            } else if (colors.contains(adjective)) {
                groups.add("color");
            } else {
                groups.add("");
            }
        }
        return groups;
    }

    /****** HELPER FUNCTIONS ******/

    // Determines whether the given tag is any kind of noun
    private boolean getIsNoun(String tag) {
        return (tag.equals("NN") || tag.equals("NNP") || tag.equals("NNS") || tag.equals("NNPS"));
    }

    private double getScale(String word){
        // TODO: have the size output depending on the contextual word used
        // basically umm...
        //let's see

        ArrayList<String> words = new ArrayList<>();
        words.add("tiny");
        words.add("small");
        words.add("");
        words.add("large");
        words.add("gigantic");
        int neutral = -1;
        double index = -1;
        for(int i = 0; i < words.size(); ++i){
            if(words.get(i).equalsIgnoreCase("")){
                neutral = i;
            }
            if(words.get(i).equalsIgnoreCase(word)){
                index = i;
            }
        }

        if(index == -1){
            return -1;
        }
        if(index == 0){
            index = 0.5;
        }

        return index / neutral;
        //i stores the location of the neutral/aka not size descriptor string
        //so basically the scale should be returned as whatever index/neutral goes

    }

}