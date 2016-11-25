package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;

class Triangle {

    private PApplet pApplet;
    private ArrayList<Float> coordinates;

    private ArrayList<Float> defaultCoordinates = new ArrayList<>();

    public Triangle(PApplet pApplet, ArrayList<Float> coordinates) {
        this.pApplet = pApplet;
        this.coordinates = coordinates;

        initDefaultCoordinates();

    }

    private void initDefaultCoordinates() {
        defaultCoordinates.add((float) 30.0);
        defaultCoordinates.add((float) 75.0);
        defaultCoordinates.add((float) 58.0);
        defaultCoordinates.add((float) 20.0);
        defaultCoordinates.add((float) 86.0);
        defaultCoordinates.add((float) 75.0);
    }

    /**
     * Draws a triangle.
     */
    public void drawTriangle() {
        pApplet.stroke(0);
        pApplet.fill(255,255,0);

        if (coordinates.size() != 6) {
            System.out.println("WARNING: not enough coordinates to" +
                    "specify a triangle. Using default values to draw coordinates.");
            pApplet.triangle(defaultCoordinates.get(0),defaultCoordinates.get(1),
                    defaultCoordinates.get(2),defaultCoordinates.get(3),defaultCoordinates.get(4),
                    defaultCoordinates.get(5));
        } else {
            pApplet.triangle(coordinates.get(0),coordinates.get(1),coordinates.get(2),
                    coordinates.get(3),coordinates.get(4),coordinates.get(5));
        }
    }
}
