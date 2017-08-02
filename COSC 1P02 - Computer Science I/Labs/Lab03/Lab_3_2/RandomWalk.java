package Lab_3_2;


import Media.*;
import static java.lang.Math.*;


/** This program draws a Random Walk
  *
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                  */

public class RandomWalk {
    
    
    private TurtleDisplayer  display;  // display to draw on
    private Turtle           pen;   // turtle for drawing
    
    
    /** The constructor sets up the drawing environment.                        */
    
    public RandomWalk ( ) {
        
        display = new TurtleDisplayer();
        pen = new Turtle();
        display.placeTurtle(pen);
        drawRandomWalk();
        display.close();
        
    };  // constructor
    
    
    private void drawRandomWalk ( ) {
      
      pen.penDown();
      for ( int i = 1 ; i <= 20 ; i++) {
        pen.moveTo (((int) (301*random ()) - 150),((int) (301*random ()) - 150));
      };
      pen.penUp();
      
    };  // drawRandomWalk
    
    
    public static void main ( String[] args ) { RandomWalk h = new RandomWalk(); };
    
    
} // RandomWalk