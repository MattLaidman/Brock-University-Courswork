package Assign_1_A;


import Media.*;
import static Media.Turtle.*;
import static java.lang.Math.*;


/** Assignment 1 Part A, draws a diamond
  **
  ** @author Matt Laidman
  **
  ** @version 1 (October 2012)            */

public class Diamond {
  
  
  private TurtleDisplayer display;
  private Turtle pen;
  
  
  private Diamond ( ) { 
    
    
    pen = new Turtle ();
    display = new TurtleDisplayer();
    display.placeTurtle (pen);
    pen.penDown ();
    drawDiamond ();   
    
  };
  
  
  private void drawDiamond ( ) { // drawDiamond method draws a diamond
    
    
    pen.left (PI/3);
    
    for (int i = 0 ; i <= 2 ; i++) {
      pen.forward (40);
      pen.right (2*PI/3);
      pen.forward (40);
      pen.right (PI/3);
    }
    
    pen.left ((PI/2)+(PI/6));   
    
    
  }
  
  
  
  public static void main ( String[] args ) {Diamond d = new Diamond(); };
  
  
}