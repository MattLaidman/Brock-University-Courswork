package Assign_1_B;


import Media.*;
import static Media.Turtle.*;
import static java.lang.Math.*;


/** Assignment 1 Part B, draws a row of 7 diamonds
  **
  ** @author Matt Laidman
  **
  ** @version 1 (October 2012)            */

public class DiamondRow {
  
  
  private TurtleDisplayer display;
  private Turtle pen;
  
  
  public DiamondRow () { // DiamondRow method draws a row of diamonds
    
    
    pen = new Turtle ();
    display = new TurtleDisplayer();
    display.placeTurtle (pen);
    
    pen.moveTo (-140,0);
    pen.penDown ();
    
    for (int j = 1 ;j <= 7 ; j++) {
      drawDiamond ();
    }
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
  };
  
  
  public static void main ( String[] args ) {DiamondRow d = new DiamondRow(); };
  
  
}