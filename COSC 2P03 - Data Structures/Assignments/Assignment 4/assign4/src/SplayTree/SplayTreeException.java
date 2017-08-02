package SplayTree;

/**
 * SplayTreeException is thrown by SplayTree class if an empty list is supplied as input to tree.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 28, 2014)
 */

public class SplayTreeException extends RuntimeException {

    public SplayTreeException ( ) {

        this("Unable to build tree from null list.");
    }

    public SplayTreeException (String arg) {

        super(arg);
    }
}
