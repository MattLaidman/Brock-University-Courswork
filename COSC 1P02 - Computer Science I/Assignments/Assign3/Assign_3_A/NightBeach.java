package Assign_3_A;

import Media.*;
import java.awt.*;
import static java.lang.Math.*;
import static java.awt.Color.*;


public class NightBeach {
  
  private Picture pic;
  private PictureDisplayer display;
  
  
  public NightBeach ( ) {
    
    pic = new Picture ();
    display = new PictureDisplayer ();
    display.placePicture (pic); 
    display.waitForUser ();
  };
  
  
  private void darken () {
    
    Pixel x = pic.next ();
    double r;
    
    while (pic.hasNext ()) {
      r = x.getDistance (new Color (58, 117, 197)) / 311;
      x.setRed ((int) (x.getRed () * r));
      x.setGreen ((int) (x.getGreen () * r));
      x.setBlue ((int) (x.getBlue () * r)); 
      x = pic.next ();
    }
    display.close ();
  };
  
  
  public static void main ( String[] args ) {
    NightBeach n = new NightBeach();
    n.darken ();                 
  };
};