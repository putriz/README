package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;

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
     *
     */
    public void sketch() {



    }


}
