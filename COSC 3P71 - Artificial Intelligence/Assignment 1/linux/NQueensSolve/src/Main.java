import NQueenSearch.InformedSearch;
import QueenBoard.QueenBoard;
import NQueenSearch.BlindSearch;

/**
 * COSC 3P71 - Introduction to Artificial Intelligence
 * Professor: Dr. Beatrice Ombuki-Berman
 * TA / Tutorial Leader: Justin Maltese
 *
 * Assignment 1
 *
 * Matt Laidman
 * 5199807, ml12ef
 * October 19, 2015
 *
 * NQueensSolve package solves the NQueens puzzle in two different ways.
 *
 * 1. Blind Search (See BlindSearch.java for details)
 * 2. Informed Search (See InformedSearch.java for details)
 *
 * Either method is accessible from the command line. Execute for the
 * .jar with no arguments for usage instructions and examples.
 */

public class Main {

    public static void main(String[] args) {

        //parse command line input
        if (args.length > 0) {
            try{
                QueenBoard board = new QueenBoard(Integer.parseInt(args[0]));
                if (args.length > 1) {
                    if (args[1].equals("0")) {
                        BlindSearch search1 = new BlindSearch();
                        if (args.length > 2) {
                            if (args[2].equals("1")) {
                                search1.doBlindSearch(board, true);
                                return;
                            }
                        }
                        search1.doBlindSearch(board, false);
                        return;
                    } else if (args[1].equals("1")) {
                        InformedSearch search2 = new InformedSearch();
                        if (args.length > 2) {
                            try{
                                search2.doInformedSearch(board, Long.parseLong(args[2]));
                                return;
                            } catch (Exception e) {
                                // do nothing :(
                            }
                        }
                        search2.doInformedSearch(board);
                        return;
                    }
                }
            } catch (Exception e) {
                // do nothing :(
            }
        }
        System.out.println("Usage:\n");
        System.out.println("- java -jar NQueensSolve M [Options]\n");
        System.out.println("  - M (Mode): 0 or 1");
        System.out.println("    - 0: Blind Search");
        System.out.println("    - 1: Informed Search");
        System.out.println("  - Options:");
        System.out.println("    - M = 0:");
        System.out.println("      - Optional: 0 or 1");
        System.out.println("        - 0: false - print only first solution (default)");
        System.out.println("        - 1: true - print all solutions");
        System.out.println("    - M = 1:");
        System.out.println("      - Optional: long integer seed");
        System.out.println("        - If no seed given, 34567 is used\n");
        System.out.println("Example:\n");
        System.out.println("- java -jar NQueensSolve 0 0");
        System.out.println("- java -jar NQueensSolve 1 76543");
    }
}