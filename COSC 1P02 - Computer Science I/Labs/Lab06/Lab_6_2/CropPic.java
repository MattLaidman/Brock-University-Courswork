package Lab_6_2;

import Media.*;
import static java.lang.Math.*;
import static java.awt.Color.*;
import java.awt.*;


public class CropPic {

  static Picture aPic;
  PictureDisplayer display;
    
  
    public CropPic ( ) {
        
        aPic = new Picture ();
    };
    
    
    private void crop (Picture aPic, int x, int y, int width, int height) {
      
      Picture cropped = new Picture (width, height);
      Pixel p;

      for (int i = 0 ; i < height ; i++) {
        for (int j = 0 ; j < width ; j++) {
          cropped.getPixel(j, i).setColor(aPic.getPixel(j + x, i + y).getColor ());                                           
        }
      }
      display = new PictureDisplayer (width, height);
      display.placePicture (cropped);
      
    };
    
    
    public static void main ( String[] args ) {
      CropPic c = new CropPic();
      c.crop (aPic, 275, 275, 100, 100);
    };
    
    
}