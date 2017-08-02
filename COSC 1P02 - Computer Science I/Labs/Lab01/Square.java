package Lab01;


import Media.*;
import static java.lang.Math.*;
import static java.awt.Color.*;
import java.awt.Color;


public class Square {
  
  
  private TurtleDisplayer  display;
  private Turtle           pen;
  
  
  public Square ( ) {
    
    display = new TurtleDisplayer();
    pen = new Turtle();
    display.placeTurtle(pen);
    int sides = 5;
    
    pen.setPenColor (red);
    pen.setPenWidth (5);
    pen.setSpeed (Turtle.FAST);
    pen.penDown();
    
    for (int j = 1 ; j <= sides ; j++) {
      for (int i = 1 ; i <= sides ; i++) {
        switch (i) {
          case 1: pen.setPenColor (new Color ((int)(random()*127)+127,(int)(random()*127)+127,(int)(random()*127)+127));
          break;
          case 2: pen.setPenColor (new Color ((int)(random()*127)+127,(int)(random()*127)+127,(int)(random()*127)+127));
          break;
          case 3: pen.setPenColor (new Color ((int)(random()*127)+127,(int)(random()*127)+127,(int)(random()*127)+127));
          break;
          case 4: pen.setPenColor (new Color ((int)(random()*127)+127,(int)(random()*127)+127,(int)(random()*127)+127));
          break;
          case 5: pen.setPenColor (new Color ((int)(random()*127)+127,(int)(random()*127)+127,(int)(random()*127)+127));
          break;
        }
        
        pen.forward(50);
        pen.right(2*PI/sides);
        
      }
      
      pen.left (2*PI/sides);
      
    }
    
    display.close();
    
  }
  
  
  public static void main ( String[] args ) { Square s = new Square(); }
  
  
}