package AVLTree;

/**
 * AVLTreeException is thrown by AVLTree class if an empty list is supplied as input to tree.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 21, 2014)
 */

public class AVLTreeException extends RuntimeException {

    public AVLTreeException ( ) {

        this("Unable to build tree from null list.");
    }

    public AVLTreeException (String arg) {

        super(arg);
    }
}
