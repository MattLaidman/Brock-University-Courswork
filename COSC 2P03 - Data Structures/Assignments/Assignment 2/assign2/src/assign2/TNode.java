package assign2;

/**
 * TNode class is the Tree Node used in the building and traversal of the
 * un-threaded Binary Search Tree.
 *
 * ***************** Global Variables *****************
 *
 * key          - The contents of the node.
 * left         - The left child of the node.
 * right        - The right child of the node.
 * C            - The number of occurrences of key in data file
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 1, 2014)
 */

public class TNode {

    String key;
    TNode left, right;
    int C;

    public TNode ( ) { this(null, null, null); }

    public TNode (String key) {
        this(key, null, null);
    }

    public TNode (String key, TNode left, TNode right) {
        this.key = key;
        this.right = right;
        this.left = left;
        this.C = 1;
    }
}
