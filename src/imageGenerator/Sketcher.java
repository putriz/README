package imageGenerator;

import javafx.util.Pair;
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
    private ArrayList<Pair<String,HashMap<String,ArrayList<Float>>>> images;

    public Sketcher(PApplet pApplet, ArrayList<Pair<String,HashMap<String,ArrayList<Float>>>> images) {
        this.pApplet = pApplet;
        this.images = images;
    }

    /**
     * Examines the items in the HashMap images to decide which images
     * to draw
     */
    public void sketch() {

        // iterate through the HashMap images
        for (Pair<String,HashMap<String,ArrayList<Float>>> entry : images) {

            if (entry.getKey().equals("triangle")) {
                HashMap<String,ArrayList<Float>>  imageOptions = entry.getValue();
                Triangle triangle = new Triangle(pApplet,imageOptions);
                triangle.drawTriangle();
            }

            else if (entry.getKey().equals("circle")) {
                HashMap<String,ArrayList<Float>>  imageOptions = entry.getValue();
                Circle circle = new Circle(pApplet,imageOptions);
                circle.drawCircle();
            }
            else if (entry.getKey().equals("arc")) {
                HashMap<String,ArrayList<Float>> imageOptions=entry.getValue();
                Arc arc=new Arc(pApplet,imageOptions);
                arc.drawArc();
            }
            /*else if (entry.getKey().equals("rectangle")){
                HashMap<String, ArrayList<Float>> imageOptions = entry.getValue();
                Rectangle rect = new Rectangle(pApplet, imageOptions);
                rect.drawRectangle();
            }*/
        }


    }

}
