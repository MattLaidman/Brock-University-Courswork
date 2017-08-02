package Lab_5_3;

import Media.*;
import static java.lang.Math.*;
import static java.awt.Color.*;
import java.awt.*;


public class Effects {
    
    
  Picture p,q;
  PictureDisplayer display;
    
    public Effects ( ) {
        
        display = new PictureDisplayer ();
        p = new Picture ();
        q = new Picture ();
        display.placePicture (p);
        applyEffect();
        display.close();
        
    };
    
    
    private void applyEffect ( ) {
      
      Pixel x,y;
      
      while (p.hasNext ()) {
        x = p.next ();
        y = q.next ();
        if (x.getDistance(GREEN) < 160 ) {
          x.setColor (y.getColor ());
        } else if (x.getDistance(GREEN) < 200) {
          x.setRed ((x.getRed ()+ y.getRed())/2);
          x.setGreen ((x.getGreen ()+ y.getGreen ())/2);
          x.setBlue ((x.getBlue ()+ y.getBlue ())/2);
        }
      }
      
    };  // applyEffect
    
    
    public static void main ( String[] args ) { Effects e = new Effects(); };
    
    
}  // Effects
