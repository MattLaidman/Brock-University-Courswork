package MULTISET;
public class KeyedChar implements Keyed { // Keyed character implementation of Keyed
    private char theChar;
    public KeyedChar (char c) {
        theChar = c;
    }
    public String getKey( ) {
        return String.valueOf(theChar);
    }
}