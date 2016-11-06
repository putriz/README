package main;

import imageGenerator.Circle;
import parser.Parser;
import processing.core.PApplet;

public class Main extends PApplet{

    private Parser parser;
    private Circle circle;

    public void settings() {
        size(200,200);
    }

    public void setup() {
        parser = new Parser();
        parser.askInput();
        circle = new Circle(this);
    }

    public void draw() {
        background(255);
        circle.drawCircle();
    }

    /**
     * Main method that starts up the processing sketch
     * @param args
     */
    static public void main(String args[]) {
        PApplet.main("main.Main");
    }

}
