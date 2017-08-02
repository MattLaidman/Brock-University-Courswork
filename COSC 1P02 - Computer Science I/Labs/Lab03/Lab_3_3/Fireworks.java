package Lab_3_3;


import Media.*;
import static java.lang.Math.*;
import java.awt.Color;


/** This program draws fireworks
  *
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                  */

public class Fireworks {
    
    
    private TurtleDisplayer  display;  // display to draw on
    private Turtle           pen;   // turtle for drawing
    
    
    /** The constructor sets up the drawing environment.                        */
    
    public Fireworks ( ) {
        
        display = new TurtleDisplayer();
        pen = new Turtle();
        display.placeTurtle(pen);
        drawFireworks();
        display.close();
        
    };  // constructor
    
    
    private void drawFireworks ( ) {
      
      pen.setSpeed (Turtle.FAST);
      
      for ( int i = 1 ; i <= 20 ; i++) {
        pen.moveTo (((int) (301*random ()) - 150),((int) (301*random ()) - 150));
        drawStarburst ();
        pen.penUp ();
      };
      
    };  // drawFireworks
    
      
      private void drawStarburst () {
        
        int c;
        
        c = ((int) (16777215*random ()));
        
        pen.setPenColor(new Color (c));
        
        for ( int i=1 ; i<=10 ; i++ ) {
            pen.forward(20);
            pen.penDown();
            pen.forward(10);
            pen.penUp();
            pen.backward(30);      // back to center
            pen.right(PI/5);
        };
      };
    
    public static void main ( String[] args ) { Fireworks h = new Fireworks();};
    
    
} // Fireworks