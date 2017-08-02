package AVLTree;

/**
 * TNode class is the Binary Search Tree Node used in the building and traversal of the
 * AVL Tree.
 *
 * ***************** Global Variables *****************
 *
 * key          The contents of the node.
 * left         The left child of the node.
 * right        The right child of the node.
 * c            The number of occurrences in the tree.
 * height       The height of the node.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 21, 2014)
 */

public class TNode {
    String key;                                                 // String of node data (key)
    TNode left, right;                                          // Left and right children
    int c, height;                                              // Number of occurences, height in tree

    /**
     * Public constructor to create a TNode from a key with two null children.
     *
     * @param key       The data in the TNode.
     */

    public TNode (String key) {

        this(key, null, null);                                  // Call private constructur with key/null
    }

    /**
     * Private constructor to set values of package private variables.
     *
     * @param key       The String data of the TNode.
     * @param left      The left child TNode to point to.
     * @param right     The right child TNode to point to.
     */

    private TNode (String key, TNode left, TNode right) {

        this.key = key;                                         // Set values on variables
        this.right = right;
        this.left = left;
        this.c = 1;                                             // Initial count is 1 upon creation
        height = 0;                                             // Initial height is 1 upon creation
    }
}
