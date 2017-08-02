package Lab_4_1;


import Media.*;
import static java.lang.Math.*;
import java.awt.Color;


/** This program draws triangles and rectangles
  *
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                  */

public class Shapes {
    
    
    private TurtleDisplayer  display;  // display to draw on
    private Turtle           pen;   // turtle for drawing
        
    
    public Shapes ( ) {
        
      display = new TurtleDisplayer();
      pen = new Turtle();
      display.placeTurtle(pen);
      
      int triLength, recWidth, recHeight;
      triLength = 50;
      recWidth = 60;
      recHeight = 20;
      
      drawTriangle (triLength);
      pen.forward (80);
      drawRectangle (recWidth, recHeight);
      display.close ();
      
        
    };  // constructor
    
    
    private void drawTriangle (int length) {
      
      pen.penDown ();
      for (int i = 1 ; i <= 3 ; i++) {
        pen.forward (length);
        pen.left (2*PI/3);
      }
      
      pen.penUp ();
      
      
    };  // drawTriangle    
    
    
     private void drawRectangle (int length, int height) {
      
      pen.penDown ();
      for (int i = 1 ; i <= 2 ; i++) {
        pen.forward (length);
        pen.left (PI/2);
        pen.forward (height);
        pen.left (PI/2);
      }
      
      pen.penUp ();
      
      
    };  // drawTectangle   
    
    public static void main ( String[] args ) { Shapes h = new Shapes();};
    
    
} // Shapes