package Lab_2_4;


import Media.*;
import static java.lang.Math.*;
import static java.awt.Color.*;


/** This class draws a starburst pattern.
  *
  * @version 1.0 (Sept 2007)
  * @author Dave Bockus
  *
  * @version 2.0 (Sept 2008)
  * @author Dave Hughes
  *
  * @version 3.0 (Sept 2010)
  * @author Dave Hughes                                                          */

public class Starburst {
    
    
    private TurtleDisplayer  display;  // displayer to draw ion
    private Turtle           yertle;   // turtle to draw with
    
    public Starburst ( ) {
        
        display = new TurtleDisplayer();
        yertle = new Turtle();
        display.placeTurtle(yertle);
        
        yertle.setPenColor(orange);
        for ( int i=1 ; i<=10 ; i++ ) {
            yertle.forward(60);
            yertle.penDown();
            yertle.forward(30);
            yertle.penUp();
            yertle.backward(90);      // back to center
            yertle.right(PI/5);
        };
        
        display.close();
        
    }; // constructor
    
    
    public static void main ( String[] args ) { Starburst s = new Starburst(); };
    
    
} // Starburst
