package Assign_3_B;

import Media.*;
import java.awt.*;
import static java.lang.Math.*;

public class SoundEcho {
  
  private static Sound aSound;
  private SoundPlayer player;
  
  
  public SoundEcho ( ) {
    
    aSound = new Sound ();
    player = new SoundPlayer ();
  };

  
  private void addEcho ( ) {
    
    Sound eSound = echo (aSound, 0.5, 0.25); 
    Sound nSound = new Sound (aSound.getNumSamples() + eSound.getNumSamples());
    Sample s = aSound.next();
    Sample t = eSound.next();
    Sample u = nSound.next();
    
    while (nSound.hasNext() & (aSound.hasNext() | eSound.hasNext())) {
      if (aSound.hasNext()) {
      u.setValue(s.getValue());
      u = nSound.next();
      s = aSound.next();
      } else {
        u.setValue(t.getValue());
        u = nSound.next();
        t = eSound.next();
      }
    }
    System.out.println("placed sound");
    player.placeSound (nSound); 
  }
  
  private Sound echo (Sound aSound, double delay, double factor) {
    
    Sound eSound = new Sound ((int)(delay*aSound.getSampleRate())+aSound.getNumSamples());
    Sample s = eSound.next();
    
    for (int i = 1 ; i <= (int)(delay*aSound.getSampleRate()) ; i++) {
      eSound.next();
    }
    
    while (eSound.hasNext ()) {
      s.setValue((int)(aSound.next().getValue()*factor));
      s = eSound.next();
    }
    System.out.println("returned sound");
    return(eSound);
  };
  
  
  public static void main ( String[] args ) {
    SoundEcho s = new SoundEcho();
    s.addEcho ();               
  };
};