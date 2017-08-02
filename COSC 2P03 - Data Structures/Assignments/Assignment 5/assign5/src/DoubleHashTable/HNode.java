package DoubleHashTable;

/**
 * HNode class is the Double Hashing Table Node used in the hash table HNode array.
 *
 * ***************** Global Variables *****************
 *
 * key          The word in the HNode.
 * c            The number of occurrences in the table.
 * del          If the word has been deleted or not.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (November 24, 2014)
 */
class HNode {

    String key;                                                 // String word key
    int c;                                                      // Number of occurrences
    boolean del;                                                // If deleted (c == 0)

    /**
     * Public HNode constructor creates an initial HNode from a given string.
     *
     * @param key       The String to use as key.
     */

    public HNode(String key) {

        this.key = key;                                         // Set key variable
        this.c = 1;                                             // Count is 1 upon creation
        this.del = false;                                       // Not deleted upon creation
    }
}
