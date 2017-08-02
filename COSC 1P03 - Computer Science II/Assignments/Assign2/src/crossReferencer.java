import java.util.*;
import BasicIO.*;

/**
 * This program produces a cross reference of a given source file, sampleSource.dat, against a given
 * key file, reservedWords.dat.
 *
 * @author Matt Laidman
 * @version 1.0 (February 2014)
 */


public class crossReferencer {

    private ASCIIDataFile kFile = new ASCIIDataFile("reservedWords.dat");
    private ASCIIDataFile sFile = new ASCIIDataFile("sampleSource.dat");

    Node wordList;
    Node sourceList;

    public crossReferencer() {
        wordList = getWords(kFile, wordList);
        sourceList = getWords(sFile, sourceList);
        compare(wordList, sourceList);
    }


    private void compare (Node key, Node source) {

        ASCIIDisplayer display = new ASCIIDisplayer(35, 35);
        Node p, q;
        String last = "";

        display.show(); // print header
        display.writeLine("Cross Reference of " + kFile.getFile() + " against " + sFile.getFile() + "\n\nKey Word\t\tLine Number");
        display.writeLine("----------------------------------------------------------------------");

        p = key;
        while (p != null) { // loop through key list
            if (!p.item.equals(last)) { // check to see if doubles in key
                display.writeString(p.item + "\t\t");
                q = source;
                while (q != null) { // loop through source list
                    if (q.item.equals(p.item)) { // if items are equal print line number
                        display.writeString(q.line + " ");
                    }
                    q = q.next;
                }
                display.newLine();
            }
            last = p.item;
            p = p.next;
        }
    }


    private Node getWords(ASCIIDataFile words, Node list){

        String line;
        String word;
        int lNum = 0;
        Node p, q;

        while (!words.isEOF()) {
            lNum++;
            line = words.readLine(); // read in whole line
            if (!words.successful()) break;
            StringTokenizer st = new StringTokenizer(line, " \t", false); // tokenize line, space delims
            while (st.hasMoreTokens()) {
                word = st.nextToken(); // get token
                q = null;
                p = list;
                while (p != null && word.compareToIgnoreCase(p.item) >= 0) { // find alphabetical place in list
                    q = p;
                    p = p.next;
                }
                if (q == null) { // add to place/end of list
                    list = new Node(word, lNum, p);
                } else {
                    q.next = new Node(word, lNum, p);
                }
            }
        }
        return list;
    }


    public static void main(String[] args) {new crossReferencer();}
}