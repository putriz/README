package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;


public class Rectangle {
    private PApplet pApplet;

    private ArrayList<Float> coordinates;
    private ArrayList<Float> color;

    private ArrayList<Float> defaultCoordinates = new ArrayList<>();

    public Rectangle(PApplet pApplet, HashMap<String,ArrayList<Float>> options) {
        this.pApplet = pApplet;
        this.coordinates = options.get("coordinates");
        this.color = options.get("color");

        initDefaultCoordinates();

    }

    /**
     * Using rect(a,b,c,d)
     * where a: float: x-coord of the rectangle by default
     *       b float: y-coord of the rectangle by default
     *       c: float: width of the rectangle by default
     *       d: float: height of the rectangle by default
     */
    private void initDefaultCoordinates() {
        defaultCoordinates.add((float) pApplet.width/3); // a
        defaultCoordinates.add((float) pApplet.height/3); // b
        defaultCoordinates.add((float) 100.0); // c
        defaultCoordinates.add((float) 50.0); // d
    }

    /**
     * Draws a rectangle.
     */
    public void drawRectangle() {
        pApplet.stroke(0);
        if (color != null) {
            pApplet.fill(color.get(0),color.get(1),color.get(2));
        } else {
            pApplet.fill(255,255,0);
        }

        if(coordinates.size() != 4){
//            System.out.println("WARNING: Wrong number of coordinates to" +
//                    "specify a rectangle. Using default values to draw coordinates." +
//                    " Number of coordinates: " + coordinates.size());
            pApplet.rect(defaultCoordinates.get(0),defaultCoordinates.get(1),
                    defaultCoordinates.get(2),defaultCoordinates.get(3));
        } else {
            pApplet.rect(coordinates.get(0),coordinates.get(1),coordinates.get(2),coordinates.get(3));
        }

    }
}

