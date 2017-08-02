package LabTestPractice_A_3;

import Media.*;                  // for displayer and turtle
import java.awt.*;               // for Color class
import static java.lang.Math.*;  // for math constants and functions & random
import static Media.Turtle.*;    // for turtle speed constants
import static java.awt.Color.*;  // for Color constants


public class Pentagram {
  
  private Turtle pen;
  private TurtleDisplayer display;
  
  
  public Pentagram ( ) {  // constructor
    
    pen = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
    
    drawPentagram (50);
    display.close ();
    
  };  // constructor
  
  
  private void drawPentagram (double radius) {

    pen.forward (radius);
    pen.right (9*PI/10);
    pen.penDown ();
    
    for (int i = 1 ; i <= 5 ; i++) {
      pen.forward (2*radius);
      pen.right (4*PI/5);
    }
    pen.penUp ();
    
  };
  
  
  public static void main ( String[] args ) {Pentagram m = new Pentagram();}
};