package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;

class Circle {

    private PApplet pApplet;
    private ArrayList<Float> coordinates = new ArrayList<>();

    private ArrayList<Float> defaultCoordinates = new ArrayList<>();

    public Circle(PApplet pApplet, ArrayList<Float> coordinates) {
        this.pApplet = pApplet;
        this.coordinates = coordinates;

        initDefaultCoordinates();
    }

    private void initDefaultCoordinates() {
        defaultCoordinates.add((float) pApplet.width/3);
        defaultCoordinates.add((float) pApplet.height/3);
        defaultCoordinates.add((float) 50);
        defaultCoordinates.add((float) 50);
    }

    /**
     * Draws a circle
     */
    public void drawCircle() {
        pApplet.stroke(0);
        pApplet.fill(255,255,0);

        if (coordinates.size() != 4) {
            System.err.println("WARNING: Incorrect number of coordinates " +
                    "to specify a circle (Expected input is 4: x, y, width, height). " +
                    "Using default coordinates to draw");
            pApplet.ellipse(defaultCoordinates.get(0),defaultCoordinates.get(1),
                    defaultCoordinates.get(2),defaultCoordinates.get(3));
        }
        else {
            pApplet.ellipse(coordinates.get(0), coordinates.get(1),
                    coordinates.get(2), coordinates.get(3));
        }

    }

}
