package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *    #
 *   # #
 *  # _ #
 *  |   |
 *  | _ |
 *
 *  This simple house will be made out of a triangle and a square
 */
public class House {

    private PApplet pApplet;

    private ArrayList<Float> coordinates;
    private ArrayList<Float> color;

    private ArrayList<Float> defaultCoordinates = new ArrayList<>();

    public House(PApplet pApplet, HashMap<String,ArrayList<Float>> options) {
        this.pApplet = pApplet;
        this.coordinates = options.get("coordinates");
        this.color = options.get("color");

        initDefaultCoordinates();
    }

    /**
     * The coordinates for house will contain coordinates for
     * triangle and square. There should be 10 numbers total.
     */
    private void initDefaultCoordinates() {
        // triangle
        defaultCoordinates.add((float) 30.0);
        defaultCoordinates.add((float) 75.0);
        defaultCoordinates.add((float) 58.0);
        defaultCoordinates.add((float) 20.0);
        defaultCoordinates.add((float) 86.0);
        defaultCoordinates.add((float) 75.0);

        // square
        defaultCoordinates.add((float) pApplet.width/3); // a
        defaultCoordinates.add((float) pApplet.height/3); // b
        defaultCoordinates.add((float) 100.0); // c
        defaultCoordinates.add((float) 100.0); // d
    }

    public void drawHouse() {
        pApplet.stroke(0);
        if (color != null) {
            pApplet.fill(color.get(0),color.get(1),color.get(2));
        } else {
            pApplet.fill(255,255,0);
        }

        if (coordinates.size() != 10) {
            pApplet.triangle(defaultCoordinates.get(0),defaultCoordinates.get(1),defaultCoordinates.get(2),
                    defaultCoordinates.get(3),defaultCoordinates.get(4),defaultCoordinates.get(5));
            pApplet.rect(defaultCoordinates.get(6),defaultCoordinates.get(7),defaultCoordinates.get(8),defaultCoordinates.get(9));

        } else {
            pApplet.triangle(coordinates.get(0),coordinates.get(1),coordinates.get(2),coordinates.get(3),coordinates.get(4),coordinates.get(5));
            pApplet.rect(coordinates.get(6),coordinates.get(7),coordinates.get(8),coordinates.get(9));
        }

    }



}
