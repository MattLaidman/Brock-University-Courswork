package LabTestPractice_A_1;

import Media.*;                  // for displayer and turtle
import java.awt.*;               // for Color class
import static java.lang.Math.*;  // for math constants and functions & random
import static Media.Turtle.*;    // for turtle speed constants
import static java.awt.Color.*;  // for Color constants


public class MirroredWalk {
  
  private Turtle pen, pen2;
  private TurtleDisplayer display;
  
  
  public MirroredWalk ( ) {  // constructor
    
    pen = new Turtle (FAST);
    pen2 = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
    display.placeTurtle (pen2);
    walk (10);
    display.close ();
    
  };  // constructor
  
  
  private void walk (int steps) {
    
    int distance;
    double angle;
    
    pen.setPenColor (red);
    pen2.setPenColor (blue);
    pen.penDown ();
    pen2.penDown ();
    
    for (int i = 1 ; i <= steps ; i++) {
      distance = (int)(Math.random ()*100);
      angle = (int)(Math.random ()*(2*PI));
      pen.right (angle);
      pen2.left (angle);
      pen.forward (distance);
      pen2.forward (distance);
    };
    
  };
  
  
  public static void main ( String[] args ) {MirroredWalk m = new MirroredWalk();};
};