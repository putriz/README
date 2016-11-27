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

    public Rectangle(){
        // right corner x
        // right upper corner
        this.defCoord.add(new Tuple(20.0,80.0));
        // left uppler corner
        this.defCoord.add(new Tuple(120.0,80.0));
        // right lower corner
        this.defCoord.add(new Tuple(20.0,130));
        // left lower corner
        this.defCoord.add(new Tuple(120,130));
    }

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

    /**
     * Draws a triangle.
     */
    public void drawRectangle() {
        pApplet.stroke(0);
        pApplet.fill(255,255,0);
        if(points.size()!= 0){
            pApplet.rect(points.get(0).x,points.get(0).y,60,100);
        }else{
            pApplet.rect(defCoord.get(0).x,defCoord.get(0).y,defCoord.get(1).x,defCoord.get(2).y);
        }

    }
}

