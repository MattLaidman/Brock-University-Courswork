package Lab_7_1;


import BasicIO.*;
import static java.lang.Math.*;


/** This class is a program for a High/Low guessing game.
  * 
  * @author <your name>
  * 
  * @version 1.0 (Nov. 2012)                                                     */

public class Guess {
    
    
    private ASCIIPrompter   prompt;   // prompter for user input
    private ASCIIDisplayer  display;  // displayer user feedback
    
    
    /** The constructor sets up the prompter for input and displayer for the
      * report.                                                                  */
    
    public Guess ( ) {
        
        display = new ASCIIDisplayer();
        prompt = new ASCIIPrompter();
        
    };  // constructor
    
    
    /** This method plays a guessing game with the user.                         */
    
    private void play ( ) {
        
        int  guess;     // user's guess
        
        prompt.setLabel("Guess");
        guess = prompt.readInt();
        display.writeInt(guess);
        display.newLine();
        prompt.close();
        display.close();
        
    };  // play
    
    
    public static void main ( String[] args ) { new Guess().play(); };
    
    
}  // Guess