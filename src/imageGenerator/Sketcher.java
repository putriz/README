package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class that decides what's going
 * to appear on the canvas
 */
public class Sketcher {

    private PApplet pApplet;
    private HashMap<String,ArrayList<Float>> images;

    public Sketcher(PApplet pApplet, HashMap<String,ArrayList<Float>> images) {
        this.pApplet = pApplet;
        this.images = images;
    }

    /**
     * Examines the items in the HashMap images to decide which images
     * to draw
     */
    public void sketch() {

        // iterate through the HashMap images
        for (Map.Entry<String,ArrayList<Float>> entry : images.entrySet()) {

            if (entry.getKey().equals("triangle")) {
                Triangle triangle = new Triangle(pApplet,entry.getValue());
                triangle.drawTriangle();
            }

            else if (entry.getKey().equals("circle")) {
                Circle circle = new Circle(pApplet,entry.getValue());
                circle.drawCircle();
            }
        }


    }

}
