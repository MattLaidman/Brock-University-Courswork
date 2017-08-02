package Assign_2_B;

import Media.*;
import java.awt.*;
import static java.lang.Math.*;
import static Media.Turtle.*;
import static java.awt.Color.*;

/** This class is a program to draw a palm tree
  * 
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                 */

public class Palms {
  
  private Turtle pen;
  private TurtleDisplayer display;
  
  
  public Palms ( ) {
    
    int ox, oy;
    pen = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
    
    ox = pen.getScreenX ();
    oy = pen.getScreenY ();
    
    palmTree (0, 0);
    pen.moveTo (ox, oy);
    display.close ();    
  };
  
  
  public void palmTree (int x, int y) {
    
    pen.moveTo (x, y);
    pen.left (PI/2);
    pen.setPenColor (new Color (16752690));
    pen.setPenWidth (8);
    pen.penDown ();
    pen.forward (100);
    pen.setPenColor (green);
    pen.setPenWidth (4);
    
    for (int i = 1 ; i <= 3 ; i++) {
      curvedV (pen.getScreenX (), pen.getScreenY (), 2*i);
    }
    pen.right (PI/2);
    pen.penUp ();
    
  };
  
  
  public void curvedV (int x, int y, int segment) {
    
    pen.moveTo (x, y);
    pen.penDown ();
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
    pen.penUp ();
  };
  
  
  public static void main ( String[] args ) {Palms b = new Palms();}
};