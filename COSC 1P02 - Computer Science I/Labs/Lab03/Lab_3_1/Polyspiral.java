package Lab_3_1;


import Media.*;
import static java.lang.Math.*;


/** This program draws a hexagon.
  *
  * @author D. Hughes
  * 
  * @version 1.0 (May 2012)
  *
  * new concepts: variables, double, value assignment, expressions              */

public class Hexagon2 {
    
    
    private TurtleDisplayer  display;  // display to draw on
    private Turtle           yertle;   // turtle for drawing
    
    
    /** The constructor sets up the drawing environment.                        */
    
    public Hexagon2 ( ) {
        
        display = new TurtleDisplayer();
        yertle = new Turtle();
        display.placeTurtle(yertle);
        drawHexagon();
        display.close();
        
    };  // constructor
    
    
/** This method draws a hexagon with radius 80 units centered on the
  * turtle's starting point. It leaves the the turtle in its original position. */
    
    private void drawHexagon ( ) {
        
        double radius;  // radius of hexagon
        double angle;   // exterior angle between sides of hexagon
        double side;    // length of side of hexagon

        radius = 80;
        angle = PI / 3;
        side = 2 * radius * sin(PI/6);
        yertle.forward(radius);
        yertle.right(PI/2+PI/6);
        yertle.penDown();
        for ( int i=1 ; i<=6 ; i++) {
            yertle.forward(side);
            yertle.right(angle);
        };
        yertle.penUp();
        yertle.left(PI/2+PI/6);
        yertle.backward(radius);
        
    };  // drawHexagon
    
    
    public static void main ( String[] args ) { Hexagon2 h = new Hexagon2(); };
    
    
} // Hexagon2