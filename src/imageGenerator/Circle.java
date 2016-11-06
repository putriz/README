package imageGenerator;

import processing.core.PApplet;

/**
 * One of the shapes
 */
public class Circle {

    private PApplet pApplet;

    public Circle(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    /**
     * Currently doesn't actually draw a circle
     */
    public void drawCircle() {
        pApplet.stroke(0);
        pApplet.fill(255,255,0);
        pApplet.rect(pApplet.width/3, pApplet.height/3,100,5);
    }

}
