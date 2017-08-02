package LabTestPractice_A_2;

import Media.*;                  // for displayer and turtle
import java.awt.*;               // for Color class
import static java.lang.Math.*;  // for math constants and functions & random
import static Media.Turtle.*;    // for turtle speed constants
import static java.awt.Color.*;  // for Color constants


public class Bubbles {
  
  private Turtle pen;
  private TurtleDisplayer display;
  
  
  public Bubbles ( ) {  // constructor
    
    pen = new Turtle (FAST);
    display = new TurtleDisplayer ();
    display.placeTurtle (pen);
    
    for (int i = 1 ; i <= 10 ; i ++) {
    drawBubble (10+(int)(Math.random()*20));
    }
    display.close ();
    
  };  // constructor
  
  
  private void drawBubble (int diameter) {

    pen.setPenWidth (diameter);
    pen.setPenColor (new Color (1+(int)(Math.random ()*16777216)));
    pen.moveTo ((int)((Math.random () *300)-150), (int)((Math.random () *300)-150));
    
    pen.penDown ();
    pen.forward (0);
    pen.setPenColor (white);
    pen.setPenWidth ((int)(diameter*0.8));
    pen.forward (0);
    pen.penUp ();
    
  };
  
  
  public static void main ( String[] args ) {Bubbles b = new Bubbles();}
};