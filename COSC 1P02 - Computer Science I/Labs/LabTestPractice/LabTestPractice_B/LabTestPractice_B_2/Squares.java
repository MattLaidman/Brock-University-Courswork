package LabTestPractice_B_2;

import Media.*;                  // for displayer and turtle
import java.awt.*;               // for Color class
import static java.lang.Math.*;  // for math constants and functions & random
import static Media.Turtle.*;    // for turtle speed constants
import static java.awt.Color.*;  // for Color constants


public class Squares {
  
  private Turtle pen;
  private TurtleDisplayer display;
  
  
  public Squares ( ) {  // constructor
    
    pen = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
    
    drawNSquares (5, 15);
    display.close ();
    
  };  // constructor
  
  
  private void drawSquare (int length) {
    
    pen.penDown ();
    for (int i = 1 ; i <= 4 ; i++) {
      pen.forward (length);
      pen.right (PI/2);
    }
    pen.penUp ();
    pen.forward (length);
  };
  
  
  private void drawNSquares (int n, int n2) {
    
    pen.moveTo (-150,50); 
    for (int i = 1 ; i <= n ; i++) {
      drawSquare (300/n);
    }
    
    pen.moveTo (-150,-50); 
    for (int i = 1 ; i <= n2 ; i++) {
      drawSquare (300/n2);
    }
  };
  
  
  public static void main ( String[] args ) {Squares m = new Squares();}
};