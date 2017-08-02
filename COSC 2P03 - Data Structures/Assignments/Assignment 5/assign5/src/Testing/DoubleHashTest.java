package Testing;

import DoubleHashTable.DoubleHashTable;
import FileParser.FileToList;
import List.LNode;

/**
 * COSC 2P03 Assignment 5
 *
 * Created by Matt Laidman on November 24, 2014.
 * Student Number 5199807
 *
 * DoubleHashTest main class is a test driver for the DoubleHashTable data structure.
 *
 * The creation of the table from the words list uses the insert() method of the data structure, and
 * the deletion of words uses the find method. The operations were not shown as it can be concluded that
 * if the table is successfully created and the words are successfully deleted, the insert and find
 * functions will be correctly implemented.
 *
 * ************* Operations Performed ****************
 *
 * print()      Print a visual representation of the table to the console.
 * delete()     Delete keys starting with D - M and d - m, inclusive.
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (November 24, 2014)
 */

public class DoubleHashTest {

    public DoubleHashTest() {

        LNode words = new FileToList("dat/input.txt").data;     // Get list of words from input file
        DoubleHashTable myTable = new DoubleHashTable(words);   // Create hash table from words
        myTable.print();
        System.out.println("\nDeleting words dD - mM");
        while (words != null) {                                 // Delete each word starting with dD - mM
            if ((words.key.charAt(0) >= 'D' && words.key.charAt(0) <= 'M') ||
                    (words.key.charAt(0) >= 'd' && words.key.charAt(0) <= 'm')) {
                myTable.delete(words.key);
            }
            words = words.next;
        }
        myTable.print();                                        // Re-print table
    }

    public static void main(String[] args) {
        new DoubleHashTest();
    }
}
