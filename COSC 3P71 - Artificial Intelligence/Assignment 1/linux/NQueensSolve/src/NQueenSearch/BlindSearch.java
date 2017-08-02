package NQueenSearch;

import QueenBoard.QueenBoard;

/**
 * BlindSearch class performs a search  on an empty QueenBoard object by
 * placing all n queens in their own column along the top row. It then
 * recursively searches through all possible arrangements of the n queens
 * by moving the queens along their respective columns.
 *
 * Depending on the boolean input, showAll, all solutions or only the
 * first will be displayed.
 */

public class BlindSearch {

    private int numSolutions;
    private int configsTested, firstConfigsTested;
    private boolean printFlag, firstPrint;

    public BlindSearch ( ) {

        numSolutions = 0;
        configsTested = 0;
        firstConfigsTested = 0;
        firstPrint = true;
    }

    public void doBlindSearch (QueenBoard board, boolean showAll) {

        printFlag = showAll;
        firstPrint = true;

        int size = board.getBoardSize();

        board.resetBoard(); // for each placement of a queen in a column, get solutions.
        for (int i = 0 ; i < size ; i++) {
            if (firstPrint || printFlag) {
                board.placeQueen(i, 0);
                recBlindSearch(board, i, 0);
                board.removeQueen(i, 0);
            }
        }
        if (printFlag) {
            System.out.println("\nTotal number of solutions: " + numSolutions);
            System.out.println("Total number of configurations tested: " + configsTested);
            System.out.println("Total number of configurations tested for first solution: " + firstConfigsTested);
            System.out.println("Seed used for random number generator: N/A (Blind Search)");
        } else {
            System.out.println("Total number of configurations tested: " + firstConfigsTested);
            System.out.println("Seed used for random number generator: N/A (Blind Search)");
        }
        numSolutions = 0;
        configsTested = 0;
        firstConfigsTested = 0;
    }

    private void recBlindSearch (QueenBoard board, int x, int y) {

        int size = board.getBoardSize();
        configsTested++;

        if (!printFlag && !firstPrint) {
            return;
        }

        if (board.getNumQueens() == size) { // If 8 queens are on board check if solution
            if (isSolution(board)) {
                if (firstPrint) {
                    board.printBoard();
                    firstConfigsTested = configsTested;
                    firstPrint = false;
                } else if (printFlag) {
                    board.printBoard();
                }
                numSolutions++;
            }
            return;
        } else { // otherwise add a queen in next column
            y++;
            board.placeQueen(x, y);
            recBlindSearch(board, x, y);
            board.removeQueen(x, y);
        }

        // recursively search by moving queens along their columns and checking
        // every possible configuration.

        for (int i = x+1 ; i != x ; i = ((i + 1) % size)) {
            if (!board.hasQueen(i%size, y)) {
                board.placeQueen(i%size, y);
                recBlindSearch(board, i%size, y);
                board.removeQueen(i%size, y);
            }
        }

    }

    private boolean isSolution (QueenBoard board) {

        // Checks if current board is a correct solutions to N Queens puzzle

        int size = board.getBoardSize();

        if (board.getNumQueens() != size) {
            return false;
        }
        for (int i = 0 ; i < size; i++) { // for each queen check if can attack  any others
            for (int j = 0; j < size; j++) {
                if (board.hasQueen(i, j)) {
                    if (board.canAttack(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
