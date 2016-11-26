package imageGenerator;

import processing.core.PApplet;

import java.util.ArrayList;


/**
 * Created by Jordae4 on 11/25/2016.
 */
public class Rectangle {
    private PApplet pApplet;
    private ArrayList<Tuple<Float,Float>> points;

    private ArrayList<Tuple<Float,Float>> defCoord = new ArrayList<>();

    public Rectangle(PApplet pApplet, ArrayList<Float> Points) {
        this.pApplet = pApplet;
        if(Points.size()!=8){
            throw new IllegalArgumentException();
        }
        else{
            // right upper corner
            this.points.add(new Tuple(Points.get(0),Points.get(1)));
            // left uppler corner
            this.points.add(new Tuple(Points.get(2),Points.get(3)));
            // right lower corner
            this.points.add(new Tuple(Points.get(4),Points.get(5)));
            // left lower corner
            this.points.add(new Tuple(Points.get(6),Points.get(7)));

        }
    }

    private void initDefault() {
        // right corner x
        // right upper corner
        defCoord.add(new Tuple(20.0,80.0));
        // left uppler corner
        defCoord.add(new Tuple(120.0,80.0));
        // right lower corner
        defCoord.add(new Tuple(20.0,130));
        // left lower corner
        defCoord.add(new Tuple(120,130));
    }

    /**
     * Draws a triangle.
     */
    public void drawTriangle() {
        pApplet.stroke(0);
        pApplet.fill(255,255,0);


    }
}

