package assign2;

/**
 * TTNode class is the Threaded Tree Node used in the building and traversal of the
 * Threaded Binary Search Tree.
 *
 * ***************** Global Variables *****************
 *
 * key          - The contents of the node.
 * left         - The left child of the node.
 * right        - The right child of the node.
 * C            - The number of occurrences of key in data file
 * thread       - If the right child is a thread to successor.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 1, 2014)
 */

public class TTNode {

    String key;
    TTNode left, right;
    int C;
    boolean thread;

    public TTNode ( ) { this(null); }

    public TTNode (String key) {
        this(key, null, null);
    }

    public TTNode (String key, TTNode left, TTNode right) {
        this(key, left, right, false);
    }

    public TTNode (String key, TTNode left, TTNode right, boolean thread) {
        this.key = key;
        this.right = right;
        this.left = left;
        this.thread = thread;
        this.C = 1;
    }
}
