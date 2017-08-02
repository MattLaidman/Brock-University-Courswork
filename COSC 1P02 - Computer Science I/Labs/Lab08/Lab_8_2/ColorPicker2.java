package Lab_8_2;


import Media.*;                  // for Picture and PictureDisplayer
import java.awt.*;               // for Color class


/** This class is a program to show color represented by RGB value.
  * 
  * @author  D. Hughes
  * 
  * @version  1.0 (June 2012)
  * 
  * new concepts: Picture constructor, function method                           */

public class ColorPicker2 {
    
    
    private static final int R = 64;   // red component
    private static final int G = 128;  // green component
    private static final int B = 192;  // blue component
    
    private PictureDisplayer  display;  // display for picture
    
    
    /** The constructor sets up the display for the picture.                     */
    
    public ColorPicker2 ( ) {
        
        display = new PictureDisplayer();
        
    };  // constructor
    
    
    /** This method creates and displays the color swatch                        */
    
    private void display ( ) {
        
        Picture        pic;     // picture to be displayed
        Color          c;       // color for swatch
        
        c = new Color(R,G,B);
        pic = makeSwatch(200,200,c);
        display.placePicture(pic);
        display.close();
        pic.save();
        
    };  // display
    
    
    /** This method creates a color swatch of specified size and color.
      * 
      * @param  width   width of color swatch
      * @param  height  height of color swatch
      * @param  aColor  color for swatch
      * 
      * @return  Picture  the color swatch as a Picture                          */
    
    private Picture makeSwatch ( int width, int height, Color aColor ) {
        
        Picture  result;  // picture for swatch
        Pixel    p;       // a pixel of the picture
        
        result = new Picture(width,height);
        while ( result.hasNext() ) {
            p = result.next();
            p.setColor(aColor);
        };
        return result;
        
    };  // makeSwatch
    
    
    public static void main ( String[] args ) { new ColorPicker2().display(); } ;
    
    
}  // ColorPicker2