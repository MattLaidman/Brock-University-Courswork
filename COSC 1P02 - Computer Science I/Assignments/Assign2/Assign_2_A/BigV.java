package Assign_2_A;

import Media.*;
import java.awt.*;
import static java.lang.Math.*;
import static Media.Turtle.*;
import static java.awt.Color.*;

/** This class is a program to draw curved V
  * 
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                */

public class BigV {
  
  private Turtle pen;
  private TurtleDisplayer display;
  
  
  public BigV ( ) {
    
    int ox, oy;
    pen = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
        
    ox = pen.getScreenX ();
    oy = pen.getScreenY ();
    
    curvedV (0, 0, 8);
    pen.moveTo (ox, oy);
    display.close ();   
    
  };
  
  
  public void curvedV (int x, int y, int segment) {
   
    pen.moveTo (x, y);
    pen.penDown ();
    pen.left (PI/2);
    for (int j = 1 ; j <= 2 ; j++) {
      for (int i = 1 ; i <= 5 ; i++) {
        if (j == 1) {
        pen.left (PI/6);
        } else {
          pen.right (PI/6);
        }
        pen.forward (i*segment);
      }
      pen.penUp ();
      pen.moveTo (x, y);
      if (j == 1) {
        pen.right (5*PI/6);
      } else {
        pen.left (5*PI/6);
      }
      pen.penDown ();
    }
    pen.right (PI/2);
    pen.penUp ();
  };
  
  
  public static void main ( String[] args ) {BigV b = new BigV();}
};