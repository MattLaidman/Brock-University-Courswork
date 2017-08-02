package List;

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
 * @version 1.0 (October 21, 2014)
 */

public class LNode {

    public String key;
    public LNode next;

    /**
     * Public constructor to create an LNode from a key with a null next pointer.
     *
     * @param key       The data in the LNode.
     */

    public LNode (String key) {

        this(key, null);
    }

    /**
     * Private constructor to set values of package private variables.
     *
     * @param key       The data in the LNode.
     * @param next      The next node in the list.
     */

    private LNode (String key, LNode next) {

        this.key = key;
        this.next = next;
    }
}