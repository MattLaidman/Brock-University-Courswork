package Lab_5_2;

import Media.*;
import static java.lang.Math.*;
import java.awt.*;


public class Brighten {
    
    
  Picture p;
  PictureDisplayer display;
    
    public Brighten ( ) {
        
        display = new PictureDisplayer ();
        p = new Picture ();
        display.placePicture (p);
        brightenPicture();
        display.close();
        
    };
    
    
    private void brightenPicture ( ) {
      
      Pixel x;
      
      while (p.hasNext ()) {
        x = p.next ();
        x.setRed (min ((int)(x.getRed ()*2), 255));
        x.setGreen (min ((int)(x.getGreen ()*2), 255));
        x.setBlue (min ((int)(x.getBlue ()*2), 255));
      }
      
    };  // BrightenPicture
    
    
    public static void main ( String[] args ) { Brighten s = new Brighten(); };
    
    
}  // Brighten
