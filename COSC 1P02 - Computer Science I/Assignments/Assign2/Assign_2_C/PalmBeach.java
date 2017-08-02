package Assign_2_C;

import Media.*;
import java.awt.*;
import static java.lang.Math.*;
import static Media.Turtle.*;
import static java.awt.Color.*;

/** This class is a program to draw beach with palm trees and birds
  * 
  * @author Matt Laidman
  * 
  * @version 1.0 (October 2012)                                      */

public class PalmBeach {
  
  private Turtle pen;
  private TurtleDisplayer display;
  
  
  public PalmBeach ( ) {
    
    int ox, oy;
    pen = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
    
    ox = pen.getScreenX ();
    oy = pen.getScreenY ();
    
    drawBeach ();
    pen.moveTo (ox, oy);
    display.close ();
  };
  
  
  public void drawBeach () {
    
    pen.moveTo (-150, 0);
    pen.setPenColor (new Color (8440063));
    pen.setPenWidth (300);
    pen.penDown ();
    pen.forward (300);
    pen.penUp ();
    pen.moveTo (-150, -100);
    pen.setPenColor (yellow);
    pen.setPenWidth (100);
    pen.penDown ();
    pen.forward (300);
    pen.penUp ();
    
    pen.left (PI/2);
    palmTree (-50, -70);
    palmTree (50, -120);
    bird (100, 100);
    bird (125, 125);
    pen.right (PI/2);
  };
  
  
  public void bird (int x, int y) {
    
    pen.moveTo (x, y);
    pen.setPenColor (black);
    pen.setPenWidth (1);
    pen.penDown ();
    curvedV (pen.getScreenX (), pen.getScreenY (), 1);
  };
  
  
  public void palmTree (int x, int y) {
    
    pen.moveTo (x, y);
    pen.setPenColor (new Color (16752690));
    pen.setPenWidth (8);
    pen.penDown ();
    pen.forward (100);
    pen.setPenColor (green);
    pen.setPenWidth (4);
    
    for (int i = 1 ; i <= 3 ; i++) {
      curvedV (pen.getScreenX (), pen.getScreenY (), 2*i);
    }
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
  
  
  public static void main ( String[] args ) {PalmBeach b = new PalmBeach();}
};