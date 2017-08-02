package Example_7_2;


import Media.*;
import BasicIO.*;


/** This class is a program to play a sound at an increased volume.
  * 
  * @author D. Hughes
  * 
  * @version 1.0 (Oct. 2012)
  *
  * new concepts: Sound, SoundPlayer, Sample                                     */

public class MakeLouder {
    
    
    private static final int  FACTOR = 10;  // volume factor
    
    private SoundPlayer  player;  // a player for the sound
    
    
    /** The constructor sets up a player for the sound.                          */
    
    public MakeLouder ( ) {
        
        player = new SoundPlayer();
        
    };  // constructor
    
    
    /** This method loads the sound, doubles the amplitude and allows the sound
      * to be played.                                                            */
    
    private void play ( ) {
        
        Sound  theSound;  // the sound to manipulate
        
        theSound = new Sound();
        player.placeSound(theSound);
        player.waitForUser();
        makeLouder(theSound,FACTOR);
        player.close();
        theSound.save();
        
    };  // play
    
    
    /** This method increases the amplitude of a sound by a factor.
      * @param by     the factor of the increase                                 */
    
    private void makeLouder ( Sound aSound, int by ) {
        
        Sample  s;       // a sample of the sound
        int     amp;     // amplitude of the sample
        int     newAmp;  // new amplitute of sample
        
        while ( aSound.hasNext() ) {
            s = aSound.next();
            amp = s.getAmp();
            newAmp = amp * by;
            s.setAmp(newAmp);
        };
    };  // makeLouder
    
    
    public static void main ( String[] args )
                            { MakeLouder m = new MakeLouder(); m.play(); };
    
    
}  // MakeLouder