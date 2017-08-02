package Testing;
import Collections.*;

class KeyedChar implements Keyed {

    char  theChar;  // the key (and data) of the item for the test


    KeyedChar ( char c ) {

        theChar = c;

    }; // constructor


    public String getKey( ) {  // for Keyed

        return String.valueOf(theChar);

    }; // getKey
} // KeyedChar