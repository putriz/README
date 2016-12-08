package imageGenerator;

/**
 * Created by patels13 on 12/7/2016.
 */

import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.HashMap;

class Arc{
    private PApplet pApplet;
    private ArrayList<Float> coordinates = new ArrayList<>();
    private ArrayList<Float> color = new ArrayList<>();

    private ArrayList<Float> defaultCoordinates = new ArrayList<>();

    public Arc(PApplet pApplet, HashMap<String,ArrayList<Float>> options) {
        this.pApplet = pApplet;
        this.coordinates = options.get("coordinates");
        this.color = options.get("color");

        initDefaultCoordinates();
    }
    private void initDefaultCoordinates() {
        defaultCoordinates.add((float) pApplet.width/3);
        defaultCoordinates.add((float) pApplet.height/3);
        defaultCoordinates.add((float) 50);
        defaultCoordinates.add((float) 50);
        }
    public void drawArc(){
        pApplet.stroke(0);
        if (color != null) {
            pApplet.fill(color.get(0),color.get(1),color.get(2));
        } else {
            pApplet.fill(255,255,0);
        }

        if (coordinates.size() != 4) {
            System.err.println("WARNING: Incorrect number of coordinates " +
                    "to specify an arc (Expected input is 4: x, y, width, height). " +
                    "Using default coordinates to draw");
            pApplet.arc(defaultCoordinates.get(0),defaultCoordinates.get(1),
                    defaultCoordinates.get(2),defaultCoordinates.get(3),pApplet.PI,pApplet.TWO_PI);
        }
        else {
            pApplet.arc(coordinates.get(0), coordinates.get(1),
                    coordinates.get(2), coordinates.get(3),pApplet.PI,pApplet.TWO_PI);
        }
        //pApplet.arc(479,300,280,280, pApplet.PI,pApplet.TWO_PI);
    }

}
