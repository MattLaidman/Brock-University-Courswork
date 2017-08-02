package Assign_3_C;

import Media.*;
import java.awt.*;
import static java.lang.Math.*;
import static java.awt.Color.*;


public class EdgeDetection {
  
  private Picture pic;
  private PictureDisplayer display;
  private double TOLERANCE = 10.0;
  
  
  public EdgeDetection ( ) {
    
    pic = new Picture ();
    display = new PictureDisplayer ();
    display.placePicture (pic);
  };
  
  
  private void edge () {
    
    display.waitForUser ();
    
    for (int j = 0 ; j < pic.getHeight() ; j++){
      for (int k = 0 ; k < pic.getWidth() ; k++){
        if (j == (pic.getHeight()-1)) {
          pic.getPixel(k,j).setColor(white);
        } else{
          if (abs(intensity(pic.getPixel(k,j).getColor()) - intensity(pic.getPixel(k,j+1).getColor())) < TOLERANCE){
            pic.getPixel(k,j).setColor(white);
          } else {
            pic.getPixel(k,j).setColor(black);
          }
        }
      }
    }
  };
  
  
  private double intensity (Color c) {
    
    double intensity;
    intensity = ((c.getRed () + c.getGreen () + c.getBlue ()) / 3);
    return intensity;
  };
  
  
  public static void main ( String[] args ) {
    EdgeDetection e = new EdgeDetection();
    e.edge ();                 
  };
};