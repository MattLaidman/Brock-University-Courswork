import BasicIO.*;

/** This program will  solve any word search puzzle given the input is in
 * the form of included wordSearch.dat. The program will find words forwards,
 * backwards, up, down, and on all four diagonals. The private integer
 * variables 'numWords' and 'boardSize' must be changed if the number of
 * words or board size changes.
 *
 * @author Matt Laidman
 *
 * @version 1.0 (January 2013)									             */

public class wordSearch {

    private int numWords = 21;
    private int boardSize = 25;

    private char[][] words = new char[21][];
    private char[][] board = new char[boardSize][boardSize];
    private char[][] fBoard = new char[boardSize][boardSize];

    private int wordsFound = 0;

    public wordSearch() {
        getData();
        search();
        printSolution();
    }


    private void getData() {
        ASCIIDataFile in = new ASCIIDataFile ("wordSearch.dat");

        for (int i = 0 ; i < numWords ; i++) { // read in words
            words[i] = in.readLine().toCharArray();
        }

        for (int i = 0 ; i < boardSize ; i++) { // read in board
            board[i] = in.readLine().toCharArray();
        }
    }


    private void search() {

        char c;

        for (int wc = 0 ; wc < numWords ; wc++) { // loop through words to find
            c = words[wc][0];
            for (int i = 0 ; i < boardSize ; i++) { // loop through board looking for first letter
                for (int j = 0 ; j < boardSize ; j++) {
                    if (board[i][j] == Character.toUpperCase(c)) {
                        if (check(wc, i, j)) {
                            i = boardSize; // exit loop
                            j = boardSize;
                        } // check letter found in board for all directions
                    }
                }
            }
        }
    }


    private boolean check (int wc, int i, int j) {
        boolean match;

        // check forward
        if (j + words[wc].length <= boardSize) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i][j+c] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (1, wc, i, j);
                return true;
            }
        }

        // check backward
        if (j - words[wc].length >= 0) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i][j-c] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (2, wc, i, j);
                return true;
            }
        }

        // check up
        if (i - words[wc].length >= 0) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i-c][j] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (3, wc, i, j);
                return true;
            }
        }

        // check down
        if (i + words[wc].length <= boardSize) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i+c][j] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (4, wc, i, j);
                return true;
            }
        }

        //check diagonal up-forward
        if ((i - words[wc].length >= 0) && (j + words[wc].length <= boardSize)) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i-c][j+c] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (5, wc, i, j);
                return true;
            }
        }

        //check diagonal up-backward
        if ((i - words[wc].length >= 0) && (j - words[wc].length >= 0)) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i-c][j-c] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (6, wc, i, j);
                return true;
            }
        }

        //check diagonal down-forward
        if ((i + words[wc].length <= boardSize) && (j + words[wc].length <= boardSize)) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i+c][j+c] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (7, wc, i, j);
                return true;
            }
        }

        //check diagonal down-backward
        if ((i + words[wc].length <= boardSize) && (j - words[wc].length >= 0)) {
            match = true;
            for (int c = 0 ; c < words[wc].length ; c++) {
                if (board[i+c][j-c] != Character.toUpperCase(words[wc][c])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                writeToSol (8, wc, i, j);
                return true;
            }
        }

        return false;
    }

    private void writeToSol (int c, int wc, int i, int j) {
        wordsFound++;

        switch (c){ // write words to solution matrix
            case 1:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i][j+k] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 2:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i][j-k] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 3:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i-k][j] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 4:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i+k][j] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 5:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i-k][j+k] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 6:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i-k][j-k] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 7:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i+k][j+k] = Character.toUpperCase(words[wc][k]);
                }
                break;
            case 8:
                for (int k = 0 ; k < words[wc].length ; k++) {
                    fBoard[i+k][j-k] = Character.toUpperCase(words[wc][k]);
                }
                break;
            default:
                break;
        }
    }

    private void printSolution () {
        ASCIIDisplayer display = new ASCIIDisplayer(boardSize+5, boardSize);

        display.show();
        display.writeLine("Words to find: " + numWords);
        display.writeLine("Words found: " + wordsFound);
        display.writeLine("");
        for (int i = 0 ; i < boardSize ; i++) {
            for (int j = 0 ; j < boardSize ; j++) {
                display.writeChar(fBoard[i][j]);
            }
            display.writeLine("");
        }
    }

    public static void main(String[] args) {new wordSearch();}
}