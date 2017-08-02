package Lab_5_1;

import Media.*;
import static java.lang.Math.*;
import java.awt.*;


public class Sunset {
    
    
  Picture p;
  PictureDisplayer display;
    
    public Sunset ( ) {
        
        display = new PictureDisplayer ();
        p = new Picture ();
        display.placePicture (p);
        dimPicture();
        display.close();
        
    };
    
    
    private void dimPicture ( ) {
      
      Pixel x;
      
      while (p.hasNext ()) {
        x = p.next ();
        x.setBlue ((int)(x.getBlue ()*0.7));
        x.setGreen ((int)(x.getGreen ()*0.7));
      }
      
    };  // dimPicture
    
    
    public static void main ( String[] args ) { Sunset s = new Sunset(); };
    
    
}  // Sunset
