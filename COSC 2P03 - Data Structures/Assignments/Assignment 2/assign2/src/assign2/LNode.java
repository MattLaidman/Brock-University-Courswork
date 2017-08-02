package assign2;

/**
 * LNode class is the List Node used in the building and traversal of the
 * words list containing the words from the data file.
 *
 * ***************** Global Variables *****************
 *
 * key          - The contents of the node.
 * next         - The next node in the list.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 1, 2014)
 */

public class LNode {

    String key;
    LNode next;

    public LNode (String key) {
        this(key, null);
    }

    public LNode (String key, LNode next) {
        this.key = key;
        this.next = next;
    }
}