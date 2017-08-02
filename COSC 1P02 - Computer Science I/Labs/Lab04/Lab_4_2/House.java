package Lab_4_2;


import Media.*;
import static java.lang.Math.*;
import static java.awt.Color.*;


/** This program draws a house
  *
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                  */

public class House {
    
    
    private TurtleDisplayer  display;  // display to draw on
    private Turtle           pen;   // turtle for drawing
        
    
    public House ( ) {
        
      display = new TurtleDisplayer();
      pen = new Turtle();
      display.placeTurtle(pen);
      
      int triLength, recWidth, recHeight;
      triLength = 120;
      recWidth = 100;
      recHeight = 80;
      
      pen.moveTo (0, -50);
      drawRectangle (recWidth, recHeight);
      pen.left (PI/2);
      pen.forward (recHeight);
      pen.right (PI/2);
      pen.backward ((triLength/2)-(recWidth/2));
      drawTriangle (triLength);
      display.close ();
      
        
    };  // constructor
        
    
      private void drawRectangle (int recLength, int recHeight) {
      
      pen.penDown ();
      for (int i = 1 ; i <= 2 ; i++) {
        pen.forward (recLength);
        pen.left (PI/2);
        pen.forward (recHeight);
        pen.left (PI/2);
      }
      pen.penUp ();
      
      }; //drawRectangle
      
      private void drawTriangle (int triLength) {
      
      pen.penDown ();
      for (int i = 1 ; i <= 3 ; i++) {
        pen.forward (triLength);
        pen.left (2*PI/3);
      }

      pen.penUp ();
      
      
    };  // drawTriangle  

      
    private void drawStarburst ( ) {
        
        for ( int i=1 ; i<=10 ; i++ ) {
            pen.forward(20);
            pen.penDown();
            pen.forward(10);
            pen.penUp();
            pen.backward(30);
            pen.right(PI/5);
        };
        
    }
      
    
    public static void main ( String[] args ) { House h = new House();};
    
    
} // Scene