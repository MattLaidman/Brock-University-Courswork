package Lab_6_1;


import Media.*;
import BasicIO.*;
import java.lang.Math.*;

public class Noise {
  
  SoundPlayer  player;  
  static Sound aSound;
  
  
  public Noise ( ) {
    
    aSound = new Sound();
    player = new SoundPlayer();
    player.placeSound (aSound);
    
  };
  
  
  private void makeNoisy (Sound aSound, int nAmp) {
    
    Sample s;
    
    while (aSound.hasNext()) {
      s = aSound.next();
      s.setAmp(s.getAmp()*((int)(2*nAmp*Math.random()-nAmp)));
    };   
  };
  
  
  public static void main ( String[] args ) {
    
    Noise n = new Noise();
    n.makeNoisy(aSound, 1000);
  };
  
  
}