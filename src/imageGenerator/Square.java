package imageGenerator;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Jordae4 on 11/27/2016.
 */
public class Square extends Rectangle {

    private PApplet pApplet;
    private ArrayList<Tuple<Float,Float>> points;

    private ArrayList<Tuple<Float,Float>> defCoord = new ArrayList<>();

    public Square(){
        defCoord.add(new Tuple(40,50));
        defCoord.add(new Tuple(50,50));
    }

    public Square(PApplet pApplet, ArrayList<Tuple<Float,Float>> coords){
        this.pApplet = pApplet;
        if(coords.size()!=2){
            throw new IllegalArgumentException();
        }
        else{
            points.add(coords.get(0));
            points.add(coords.get(1));
        }
    }

    public void drawRectangle() {
        pApplet.stroke(0);
        pApplet.fill(255,255,0);
        if(points.size()!= 0){
            pApplet.rect(points.get(0).x,points.get(0).y,60,100);
        }else{
            pApplet.rect(defCoord.get(0).x,defCoord.get(0).y,defCoord.get(1).x,defCoord.get(1).y);
        }

    }
}
