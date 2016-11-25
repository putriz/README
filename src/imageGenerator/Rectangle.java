package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;


/**
 * Created by Jordae4 on 11/25/2016.
 */
public class Rectangle {
    private PApplet pApplet;
    private ArrayList<Coordinates<Double,Double>> points;

    private ArrayList<Coordinates<Double,Double>> defCoordinates = new ArrayList<>();

    public Rectangle(PApplet pApplet, ArrayList<Double> points) {
        this.pApplet = pApplet;

    }

    private void initDefaultCoordinates() {

    }

    /**
     * Draws a triangle.
     */
    public void drawTriangle() {

    }
}

