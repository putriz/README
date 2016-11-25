package main;

import imageGenerator.Sketcher;
import parser.Parser;
import processing.core.PApplet;

public class Main extends PApplet {

    private Parser parser;
    private Sketcher sketcher;

    public void settings() {
        size(400,400);
    }

    public void setup() {
        parser = new Parser();
        parser.askInput();
        sketcher = new Sketcher(this,parser.parse());
    }

    public void draw() {
        background(255);
        sketcher.sketch();
    }

    /**
     * Main method that starts up the processing sketch
     * @param args
     */
    static public void main(String args[]) {
        PApplet.main("main.Main");
    }

}
