package Testing;
import Collections.*;

/**
 * This program...
 *
 * @author Matt Laidman
 * @version 1.0 (March 2014)
 */
public class KeyedInt implements Keyed {

    int theInt;

    public KeyedInt(int i) {
        theInt = i;
    }


    public String getKey() {
        return Integer.toString(theInt);
    }
}