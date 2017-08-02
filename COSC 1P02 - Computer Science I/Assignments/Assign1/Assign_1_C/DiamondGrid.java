package Assign_1_C;


import Media.*;
import static Media.Turtle.*;
import static java.lang.Math.*;


/** Assignment 1 Part C, draws a grid of diamonds (4x7)
  **
  ** @author Matt Laidman
  **
  ** @version 1 (October 2012)            */

public class DiamondGrid {
  
  
  private TurtleDisplayer display;
  private Turtle pen;
  
  
  private DiamondGrid () { // DiamondRGrid method draws a grid of diamonds
    
    
    pen = new Turtle ();
    display = new TurtleDisplayer();
    display.placeTurtle (pen);
    
    pen.moveTo (-140,(150-(20*Math.sqrt(3))-12));
    pen.penDown ();
    
    
    drawDiamondGrid ();
    
  }
  
  
  
  public void drawDiamondGrid () { //drawDiamondGrid method draws a grid of diamonds
    
    
    for (int k = 1 ; k <= 4 ; k++) {
      drawDiamondRow ();
      pen.penUp ();
      pen.moveTo (-140,pen.getScreenY ());
      pen.right (PI/2);
      pen.forward (40*Math.sqrt(3));
      pen.left (PI/2);
      pen.penDown ();
    }
  };
  
  private void drawDiamondRow () { // DiamondRow method draws a row of diamonds
    
    
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
  
  public static void main ( String[] args ) {DiamondGrid d = new DiamondGrid(); };
  
  
}