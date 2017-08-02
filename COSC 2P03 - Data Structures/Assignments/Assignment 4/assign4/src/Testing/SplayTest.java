package Testing;

import FileParser.FileToList;
import List.LNode;
import SplayTree.SplayTree;

/**
 * COSC 2P03 Assignment 3
 *
 * Created by Matt Laidman on October 11, 2014.
 * Student Number 5199807
 *
 * SplayTest main class is a test driver for the SplayTree data structure.
 *
 * ************* Operations Performed ****************
 *
 * inOrder()    Initial In-Order traversal of the tree.
 * preOrder()   Initial Pre-Order traversal of the tree.
 * delete()     Delete keys starting with R - T and r - t, inclusive.
 * inOrder()    Perform another In-Order traversal of the tree.
 * preOrder()   Perform another In-Order traversal of the tree.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (November 2, 2014)
 */

public class SplayTest {

    public SplayTest() {

        LNode words = new FileToList("dat/input.txt").data;     // Get list of words from input file
        SplayTree tree = new SplayTree(words);                  // Build tree from list of words
        System.out.println("\nInOrder Traversal:\n");
        tree.inOrder();                                         // Perform In-Order traversal
        System.out.println("\nPreOrder Traversal:\n");
        tree.preOrder();                                        // Perform Pre-Order traversal
        System.out.println("\nDeleting Words");
        while (words != null) {                                 // Delete each word starting with rR - tT
            if ((words.key.charAt(0) >= 'R' && words.key.charAt(0) <= 'T') ||
                    (words.key.charAt(0) >= 'r' && words.key.charAt(0) <= 't')) {
                tree.delete(words.key);
            }
            words = words.next;
        }
        System.out.println("\nInOrder Traversal:\n");
        tree.inOrder();                                         // Perform In-Order traversal
        System.out.println("\nPreOrder Traversal:\n");
        tree.preOrder();                                        // Perform In-Order traversal
    }

    public static void main(String[] args) {
        new SplayTest();
    }
}
