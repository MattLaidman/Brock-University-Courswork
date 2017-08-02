package Lab_6_3;


import Media.*;


public class RepeatSound {
  
  SoundPlayer  player;
  static Sound aSound;
  
  
  public RepeatSound ( ) {
    
    aSound = new Sound();
    player = new SoundPlayer();
    
  };
  
  
  private void repeat (Sound aSound, int nTimes) {
    
    Sample s;
    Sound nSound = new Sound(aSound.getNumSamples ()*nTimes, aSound.getSampleSize (), aSound.getSampleRate ());
    
    for (int i = 0 ; i < aSound.getNumSamples ()*nTimes ; i++) {
      nSound.getSample (i).setAmp (aSound.getSample (i % aSound.getNumSamples ()).getAmp());
    }
    player.placeSound (nSound);
  };
  
  
  public static void main ( String[] args ) {
    RepeatSound r = new RepeatSound();
    r.repeat(aSound, 3);
  };
  
  
}