package Lab_3_3;

import Media.*;                  // for Turtle and TurtleDisplayer
import static java.lang.Math.*;  // for math valuse & functions (e.g. PI, sin)
import static java.awt.Color.*;  // for color names (e.g. red)
import static Media.Turtle.*;    // for turtle speed (e.g. FAST)
import java.awt.*;               // for Color class


/** This class draws a fireworks display using starbursts.
  *
  * @version 1.0 (Sept 2007)
  * @author Dave Bockus
  *
  * @version 2.0 (Oct 2010)
  * @author Dave Hughes
  * 
  * @version 3.0 (Sept 2011)
  * @author Dave Hughes                                                          */

public class Fireworks {
    
    
    private TurtleDisplayer  display;  // displayer to draw on
    private Turtle           yertle;   // turtle to draw with
    
    
    /** The constructor draws the fireworks display.                             */
    
    public Fireworks ( ) {
        
        display = new TurtleDisplayer();
        yertle = new Turtle(FAST);
        display.placeTurtle(yertle);
        drawFireworks();
        display.close();
        
    }; // constructor
    
    
    /** This methods draws randomly placed starbursts in random colors.          */
    
    private void drawFireworks ( ) {
        
        double  x;  // x-coordinate for position
        double  y;  // y-coordinate for position
        
        for ( int i=1 ; i<=5 ; i++ ) {
            x = (int)(301*random())-150;
            y = (int)(301*random())-150;
            yertle.moveTo(x,y);
            yertle.setPenColor(new Color((int)(16777216*random())));
            drawStarburst();
        };
        
    };  // drawFireworks
    
    
    /** This method draws a starburst.                                           */
    
    private void drawStarburst ( ) {
        
        for ( int i=1 ; i<=10 ; i++ ) {
            yertle.forward(20);
            yertle.penDown();
            yertle.forward(10);
            yertle.penUp();
            yertle.backward(30);
            yertle.right(PI/5);
        };
        
    };  // drawStarburst
    
    
    public static void main ( String[] args ) { Fireworks f = new Fireworks(); };
    
    
}  // Fireworks
