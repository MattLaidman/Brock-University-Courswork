package Lab_4_3;


import Media.*;
import static java.lang.Math.*;
import static java.awt.Color.*;


/** This program draws a scene
  *
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                  */

public class Scene {
    
    
    private TurtleDisplayer  display;  // display to draw on
    private Turtle           pen;   // turtle for drawing
        
    
    public Scene ( ) {
        
      display = new TurtleDisplayer();
      pen = new Turtle();
      display.placeTurtle(pen);
      
      int triLength, recWidth, recHeight;
      triLength = 120;
      recWidth = 100;
      recHeight = 80;
      
      pen.moveTo (-100,-100);
      pen.setPenColor (green);
      drawHouse (recWidth, recHeight, triLength);
      pen.moveTo (50, -100);
      pen.setPenColor (red);
      drawHouse ((int)(recWidth*0.2), (int) (recHeight*0.2), (int) (triLength*0.2));
      pen.moveTo (80, 100);
      pen.setPenColor (orange);
      drawStarburst ();
      display.close ();
      
        
    };  // constructor
        
    
    private void drawHouse (int recLength, int recHeight, int triLength) {
         
      drawRectangle (recLength, recHeight);
      pen.left (PI/2);
      pen.forward (recHeight);
      pen.right (PI/2);
      pen.backward ((triLength/2)-(recLength/2));
      drawTriangle (triLength);

      
    };  // drawHouse  
    
    
    private void drawRectangle (int recLength, int recHeight) {
      
      pen.penDown ();
      for (int i = 1 ; i <= 2 ; i++) {
        pen.forward (recLength);
        pen.left (PI/2);
        pen.forward (recHeight);
        pen.left (PI/2);
      }
      pen.penUp ();
      
      
    };  // drawHouse
    
    
    private void drawTriangle (int triLength) {
       
      pen.penDown ();
      for (int i = 1 ; i <= 3 ; i++) {
        pen.forward (triLength);
        pen.left (2*PI/3);
      }

      pen.penUp ();
      
      
    };  // drawHouse

      
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
      
    
    public static void main ( String[] args ) { Scene h = new Scene();};
    
    
} // Scene