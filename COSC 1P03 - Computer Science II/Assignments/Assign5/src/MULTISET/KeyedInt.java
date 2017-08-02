package MULTISET;
public class KeyedInt implements Keyed { // Keyed integer implementation of Keyed
    private int theInt;
    public KeyedInt (int i) {
        theInt = i;
    }
    public String getKey( ) {
        return String.valueOf(theInt);
    }
}